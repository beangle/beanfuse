<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/Validator.js"></script>
 <body>
 <#assign labInfo><@text name="info.moduleUpdate"/></#assign>  
 <#include "/template/back.ftl"/> 
   <form name="moduleForm" action="menu!save.action" method="post">
   <@searchParams/>
   <input type="hidden" name="menu.id" value="${menu.id!}" style="width:200px;" />
   <input type="hidden" name="resourceIds" />
   <tr>
    <td>
     <table width="90%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <#assign userMsg><@text name="menu"/></#assign>
	     <td  colspan="2"> <@msg.text name="ui.editForm" arg0=userMsg/></td>
	   </tr>
	   <tr>
	    <td class="title">菜单配置<font color="red">*</font></td>
	    <td><select  name="menu.profile.id" style="width:150px;" >
	        <#list profiles as profile>
	        <option value="${profile.id}" <#if ((menu.profile.id)!0) ==profile.id>selected</#if>>${profile.name}</optino>
	        </#list>
	        </select>
	    </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_id">&nbsp;<@text name="common.id"/><font color="red">*</font>:</td>
	     <td >
	          <input id="menu_code" type="text" name="menu.code" value="${menu.code!}" style="width:200px;" onKeyUp="value=validCode(value);"/> 数字    
         </td>
	   </tr>	   
	   <tr>
	     <td class="title" width="25%" id="f_title">&nbsp;标题<font color="red">*</font>:</td>
	     <td >
          <input type="text" name="menu.title" value="${menu.title!}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_engTitle">英文标题<font color="red">*</font>:</td>
	     <td >
          <input type="text" name="menu.engTitle" value="${menu.engTitle!}" style="width:200px;" />     
         </td>
	   </tr>
		<tr>
	   	  <td class="title" width="25%" id="f_name">&nbsp;<@text "common.status"/>:</td>
	   	  <td><select  name="menu.enabled" style="width:100px;" >
		   		<option value="true" <#if menu.enabled>selected</#if>><@text "action.activate"/></option>
		   		<option value="false" <#if !menu.enabled>selected</#if>><@text "action.freeze"/></option>
		  </select>
		</td>
		</tr>
	   <tr>
	     <td class="title" width="25%" id="f_entry">&nbsp;<@text "menu.entry"/>:</td>
	     <td><input type="text" name="menu.entry" value="${menu.entry!}"/></td>
	   </tr>
	   
	   <tr>
	    <td class="title" width="25%" id="f_resources">使用资源:</td>
	    <td >
	     <table>
	      <tr>
	       <td>
	        <select name="Resources" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Resources'], this.form['SelectedResource'])" >
	        	<#list resources?sort_by("name") as resource>
	       			<option value="${resource.id}" title="${resource.title!}">
	       				${resource.name!}(${resource.title!})
	       			</option>
	       		</#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['Resources'], this.form['SelectedResource'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedResource'], this.form['Resources'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	        <select name="SelectedResource" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedResource'], this.form['Resources'])">
	         	<#list menu.resources?sort_by("name") as resource>
	       			<option value="${resource.id}" title="${resource.title!}" <#if (menu.resources?seq_contains(resource))>selected</#if>>
	       				${resource.name!}(${resource.title!})
	       			</option>
	       		</#list>
	        </select>
	       </td>             
	      </tr>
	     </table>
	    </td>
	   </tr>
   
   
	   <tr>
	     <td class="title" width="25%" id="f_description">&nbsp;<@text name="common.description"/>:</td>
	     <td >
	        <textarea cols="35" rows="2" name="menu.description">${menu.description!}</textarea>
         </td>
	   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="6"  >
	       <input type="button" value="<@text name="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="reset1" value="<@text name="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript" >
   	function validCode(codeValue){
		return codeValue.replace(/[^\d]/g,'')
	}
   function save(form){
    form.resourceIds.value = getAllOptionValue(form.SelectedResource);  
     var a_fields = {
     	 'menu.code':{'l':'<@text name="common.id"/>', 'r':true,'t':'f_id'},
         'menu.title':{'l':'标题', 'r':true, 't':'f_title'},
         'menu.engTitle':{'l':'英文标题', 'r':true, 't':'f_engTitle'}
     };
     
     var codeValue=document.getElementById("menu_code").value;
      var le = codeValue.length;
     if(le%2!=0){
          alert("代码必须为双数位!");
          return;
     }
     
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/template/foot.ftl"/>