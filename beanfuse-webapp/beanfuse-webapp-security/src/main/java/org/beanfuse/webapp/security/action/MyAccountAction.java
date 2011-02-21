package org.beanfuse.webapp.security.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.security.User;
import org.beanfuse.security.codec.EncryptUtil;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * 维护个人账户信息
 * 
 * @author chaostone
 * 
 */
public class MyAccountAction extends SecurityAction {

	private MailSender mailSender;

	private SimpleMailMessage message;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMessage(SimpleMailMessage message) {
		this.message = message;
	}

	public String resetPassword() {
		return forward();
	}

	/**
	 * 用户修改自己的密码
	 * 
	 * @return
	 */
	public String edit() {
		put("user", getUser());
		return forward();
	}

	/**
	 * 用户修改自己的密码
	 * 
	 * @return
	 */
	public String saveChange() {
		return updateAccount(getUserId());
	}

	/**
	 * 更新指定帐户的密码和邮箱
	 * 
	 * @param mapping
	 * @param request
	 * @param userId
	 * @return
	 */
	private String updateAccount(Long userId) {
		String email = get("mail");
		String pwd = get("password");
		Map valueMap = new HashMap(2);
		valueMap.put("password", pwd);
		valueMap.put("mail", email);
		entityService.update(User.class, "id", new Object[] { userId }, valueMap);
		addMessage("ok.passwordChanged");
		return "actionResult";
	}

	/**
	 * 发送密码
	 */
	public String sendPassword() {
		String name = get("name");
		String email = get("mail");
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email)) {
			addActionError("error.parameters.needed");
			return (ERROR);
		}
		List userList = entityService.load(User.class, "name", name);
		User user = null;
		if (userList.isEmpty()) {
			return goErrorWithMessage("error.user.notExist");
		} else {
			user = (User) userList.get(0);
		}
		if (!StringUtils.equals(email, user.getMail())) {
			return goErrorWithMessage("error.email.notEqualToOrign");
		} else {
			String longinName = user.getName();
			String password = RandomStringUtils.randomNumeric(6);
			user.setRemark(password);
			user.setPassword(EncryptUtil.encode(password));
			String title = getText("user.password.sendmail.title");

			List values = new ArrayList();
			values.add(longinName);
			values.add(password);
			String body = getText("user.password.sendmail.body", (String[]) values.toArray());
			try {
				SimpleMailMessage msg = new SimpleMailMessage(message);
				msg.setTo(user.getMail());
				msg.setSubject(title);
				msg.setText(body.toString());
				mailSender.send(msg);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("reset password error for user:" + user.getName() + " with email :"
						+ user.getMail());
				return goErrorWithMessage("error.email.sendError");
			}
		}
		entityService.saveOrUpdate(user);
		return forward("sendResult");
	}

	private String goErrorWithMessage(String key) {
		addError(key);
		return forward("resetPassword");
	}
}
