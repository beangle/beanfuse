<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/Validator.js"></script>
 <body>
 <#assign labInfo><#if user.name??><@text name="action.modify"/><#else><@text name="action.new"/></#if> <@text name="user"/></#assign>
 <#include "/template/back.ftl">
  
 <table width="100%"  class="formTable">
   <form name="userForm" action="user.action?method=save" method="post">
   <@searchParams/>
   <tr class="darkColumn">
     <td  colspan="2"><@text name="ui.userInfo"/></td>
   </tr>
   <tr>
     <td class="title" width="15%" id="f_name"><font color="red">*</font><@text name="user.name"/>:</td>
     <td >
     <#if !(user.id??)>
      <input type="text" name="user.name" value="${user.name!}" style="width:300px;" maxLength="30"/>         
     <#else>
     ${user.name}
     </#if>
   </tr>
   <tr>
     <td class="title"><@text "common.status"/>:</td>
     <td >
     <input value="1" id="user_status_1" type="radio" name="user.status" <#if (user.status!1)==1>checked</#if>>
     <label for="user_status_1"><@text name="action.activate"/></label>
     <input value="0" id="user_status_0" type="radio" name="user.status" <#if (user.status!1)==0>checked</#if>>
     <label for="user_status_0"><@text name="action.freeze"/></label>
     <br>
     <input id="user_admin_1" value="1" type="radio" name="user.admin" <#if user.admin!false>checked</#if>>
     <label for="user_admin_1">超级管理员</label>
     <input id="user_admin_0" value="0" type="radio" name="user.admin" <#if !(user.admin!false)>checked</#if>>
     <label for="user_admin_0">一般用户</label>
     <#if user.id??><a target="_blank" href="restriction.action?method=info&forEdit=1&restrictionType=user&restriction.holder.id=${user.id}" >(数据级权限)</a></#if>
     </td>
   </tr>
   <tr>
     <td class="title" id="f_fullname"><font color="red">*</font><@text "user.fullname"/>:</td>
     <td ><input type="text" name="user.fullname" value="${user.fullname!}" style="width:200px;" maxLength="60" /></td>
   </tr>
   <tr>
     <td class="title" id="f_email"><font color="red">*</font><@text name="common.email"/>:</td>
     <td ><input type="text" name="user.mail" value="${user.mail!}" style="width:300px;" maxLength="70" /></td>
   </tr>
   <tr>
    <td class="title" id="f_group"><font color="red">*</font><@text name="group"/>:</td>
    <td >
     <table>
      <tr>
       <td>
        <select name="Groups" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Groups'], this.form['SelectedGroup'])" >
         <#list userGroups?sort_by('name') as group>
          <option value="${group.id}">${group.name}</option>
         </#list>
        </select>
       </td>
       <td  valign="middle">
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['Groups'], this.form['SelectedGroup'])" type="button" value="&gt;"> 
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedGroup'], this.form['Groups'])" type="button" value="&lt;"> 
        <br>
       </td> 
       <td  class="normalTextStyle">
        <select name="SelectedGroup" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedGroup'], this.form['Groups'])">
         <#list user.groups! as group>
          <option value="${group.id}">${group.name}</option>
         </#list>
        </select>
       </td>             
      </tr>
     </table>
    </td>
   </tr>
   <tr>
    <td class="title" id="f_mngGroup">管理用户组:</td>
    <td >
     <table>
      <tr>
       <td>
        <select name="MngGroups" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['MngGroups'], this.form['SelectedMngGroup'])" >
         <#list mngGroups?sort_by('name') as group>
          <option value="${group.id}">${group.name}</option>
         </#list>
        </select>
       </td>
       <td  valign="middle">
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['MngGroups'], this.form['SelectedMngGroup'])" type="button" value="&gt;"> 
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedMngGroup'], this.form['MngGroups'])" type="button" value="&lt;"> 
        <br>
       </td> 
       <td  class="normalTextStyle">
        <select name="SelectedMngGroup" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedMngGroup'], this.form['MngGroups'])">
         <#list user.mngGroups! as group>
          <option value="${group.id}">${group.name}</option>
         </#list>
        </select>
       </td>
      </tr>
     </table>
    </td>
   </tr>
   <tr>
     <td class="title">&nbsp;<font color="red">*</font><@text name="userCategory" />:</td>
     <td>
      <#list categories as category>
      <input name="categoryIds" id="categoryIds${category.id}" value="${category.id}" type="checkbox" <#if user.categories?seq_contains(category)>checked</#if>/>
      <label for="categoryIds${category.id}">${category.name}</label>
      </#list>
      <br>默认
      <select name="user.defaultCategory.id">
      <#list categories as category>
         <option value="${category.id}" <#if (user.defaultCategory??)&&(user.defaultCategory.id==category.id)>selected</#if> >${category.name}</option>
      </#list>
      </select>
   </tr>
   <tr>
     <td id="f_remark" class="title">&nbsp;<@text name="common.remark"/>:</td>
     <td><textarea cols="50" rows="1" name="user.remark">${user.remark!}</textarea></td>
   </tr>
   <tr class="darkColumn" align="center">
     <td colspan="6"  >
       <input type="hidden" name="user.id" value="${user.id!}" />
       <input type="hidden" name="groupIds" value=""/>
       <input type="hidden" name="mngGroupIds" value=""/>
       <input type="button" value="<@text name="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />&nbsp;
       <input type="reset"  name="reset1" value="<@text name="action.reset"/>" class="buttonStyle" />
     </td>
   </tr>  
  </form>
 </table>
  <script language="javascript" >
  
   function editRestriction(holderId){
	  window.open('restriction.action?method=info&forEdit=1&restrictionType=user&restriction.holder.id='+holderId, 'new', 'toolbar=no,top=250,left=250,location=no,directories=no,statue=no,menubar=no,width=400,height=400');
   }
   
   function save(form){
     form['groupIds'].value = getAllOptionValue(form.SelectedGroup);
     form['mngGroupIds'].value = getAllOptionValue(form.SelectedMngGroup);  
     var a_fields = {
         'user.mail':{'l':'<@text name="common.email" />', 'r':true, 'f':'email', 't':'f_email'},
          <#if !(user.id??)>
         'user.name':{'l':'<@text name="user.name"/>', 'r':true, 't':'f_name'},
         </#if>
         'user.fullname':{'l':'真实姓名', 'r':true, 't':'f_fullname'},
         'user.remark':{'l':'<@text "common.remark"/>','r':false,'t':'f_remark','mx':80}
     };

     var v = new validator(form, a_fields, null);
     if (v.exec()) {
     	var cIds = getSelectIds("categoryIds");
        if(""==getSelectIds("categoryIds")){
           alert("请选择身份");return;
        }
        var arr = cIds.split(",");
        var defaultValue = form["user.defaultCategory.id"].value;
        var isIn = false;
        for(var i=0;i<arr.length;i++){
  			if(defaultValue==arr[i]){
  				isIn=true;
  				break;
  			}
  		}
  		if(!isIn){
  			alert("默认身份必须在所选身份中！");
  			return ;
  		}
  		
        form.submit();
     }
   }
  </script>
 </body>
<#include "/template/foot.ftl"/>
