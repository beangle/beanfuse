<#include "/template/head.ftl"/>

<BODY>
 <table id="onlineUserBar"></table>
 <table width="100%" class="frameTable">
 <tr>
  <td width="20%" valign="top" class="frameTable_view">
   <@s.form name="onlineUserForm" cssClass="searchTable" theme="xhtml">
     <tr>
         <td align="center">
         <#assign online=0><#assign max=0>
         <#list onlineProfiles as profile>
         <#assign online=online+profile.online/><#assign max=max+profile.categoryProfile.capacity/>
         </#list>
         在线${online}人 / 上限${max}人
         </td>
     </tr>
     <#list onlineProfiles?sort_by(["categoryProfile","id"]) as profile>
     <tr>
      <td colspan="2">
      <br>
      <fieldSet  align=center> 
	  <legend>${profile.categoryProfile.category.name} (在线${profile.online}人)</legend>
	   上限人数:<input name="max_${profile.categoryProfile.id}" value="${profile.categoryProfile.capacity}" style="width:50px" maxlength="5"/></br>
	   过期时间:<input name="inactiveInterval_${profile.categoryProfile.id}" value="${profile.categoryProfile.inactiveInterval}" style="width:50px" maxlength="5"/>分</br>
	   最大会话数:<input name="maxSessions_${profile.categoryProfile.id}" value="${profile.categoryProfile.userMaxSessions}" style="width:35px" maxlength="2"/>
      </fieldSet>
      </td>
     </tr>
     </#list>
     <tr>
         <td  align="center" >注:过期时间(以分计)，最大会话数指单个用户同时在线数量</br></br><button onclick="save(this.form);">  提交  </button></br></br></br></td>
     </tr>
  </@>
  </td>  
  <td valign="top">
	 <@table.table width="100%" id="onLineUserTable" sortable="true">
	    <@table.thead>
	      <@table.selectAllTd id="sessionId"/>
	      <@table.sortTd width="10%" text="用户名" id="principal"/>
	      <@table.sortTd width="10%" text="姓名" id="fullname"/>
	      <@table.sortTd width="15%" text="登录时间" id="loginAt"/>
	      <@table.sortTd width="15%" text="最近访问时间" id="lastAccessAt"/>
	      <@table.sortTd width="10%" text="在线时间" id="onlineTime"/>
	      <@table.sortTd width="15%" text="地址" id="host"/>
	      <@table.sortTd width="10%" text="用户身份" id="category.name"/>
	      <@table.sortTd width="10%" text="状态" id="expired"/>
	   </@>
	   <@table.tbody datas=onlineActivities;activity>
	      <@table.selectTd  id="sessionId" value=activity.sessionid/>
	      <td>${activity.principal}</td>
	      <td>${activity.fullname!('')}</td>
	      <td>${activity.loginAt?string("yy-MM-dd HH:mm")}</td>
	      <td>${activity.lastAccessAt?string("yy-MM-dd HH:mm")}</td>
	      <td>${(activity.onlineTime)/1000/60}min</td>
	      <td>${activity.host!('')}</td>
	      <td>${activity.category.name}</td>
	      <td>${activity.expired?string("过期","在线")}</td>
	   </@>
	  </@>
  </td>
 </tr>
</table>
<script>
  var form=  document.onlineUserForm;
   function save(form){
      <#list onlineProfiles as profile>
      if(!(/^\d+$/.test(form['max_${profile.categoryProfile.id}'].value))){alert("${profile.categoryProfile.category.name}最大用户数限制应为整数");return;}
      if(!(/^\d+$/.test(form['maxSessions_${profile.categoryProfile.id}'].value))){alert("${profile.categoryProfile.category.name}最大会话数应为整数");return;}
      if(!(/^\d+$/.test(form['inactiveInterval_${profile.categoryProfile.id}'].value))){alert("${profile.categoryProfile.category.name}过期时间应为整数");return;}
      </#list>
      form.action="online-user.action?method=save";
      form.submit();
   }
   function query(){
       alert("暂无实现");
   }

   function invalidateSession(){      
      var names =getCheckBoxValue(document.getElementsByName("sessionId"));
      if(names =="") {
         window.alert('<@text name="common.selectPlease"/>!');
         return;
      }
      if(!confirm("是否确定要结束选定用户的会话?")) return;
      form.action="online-user.action?method=invalidate&sessionIds=" +names;
      form.submit();      
   }
   function refresh(){
      form.action="online-user.action?method=index";
      form.submit();
   }
   function sendMessage(){
       var names =getCheckBoxValue(document.getElementsByName("name"));
       if(""==names){
           alert("请选择用户");return;
       }
       window.open("systemMessage.action?method=quickSend&receiptorIds="+names);
   }
   function accessLog(){
       window.open("online-user.action?method=accessLog");
   }
   var bar = new ToolBar('onlineUserBar','在线用户',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("发送消息","sendMessage()","inbox.gif");
   bar.addItem("刷新","javascript:refresh()",'refresh.gif');
   bar.addItem("结束会话","javascript:invalidateSession()",'delete.gif');
   bar.addItem("系统资源访问记录","accessLog()");
   
   var interval=setInterval('refresh()',10*1000);
</script>
</body>
<#include "/template/foot.ftl"/>