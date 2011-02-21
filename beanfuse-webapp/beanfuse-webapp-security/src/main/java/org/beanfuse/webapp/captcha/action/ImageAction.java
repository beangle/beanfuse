package org.beanfuse.webapp.captcha.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.beanfuse.struts2.action.BaseAction;

import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 图片验证码
 * 
 * @author chaostone
 * 
 */
public class ImageAction extends BaseAction implements ServletRequestAware {

	private HttpServletRequest request;

	private ByteArrayInputStream inputStream;

	private ImageCaptchaService captchaService;

	public String index() throws IOException {
		String captchaId = request.getSession().getId();
		BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, getLocale());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(challenge, "JPEG", os);
		setInputStream(new ByteArrayInputStream(os.toByteArray()));
		return "success";
	}

	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
