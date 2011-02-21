<#include "/template/head.ftl"/>
<#include "scope.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/Validator.js"></script>
 <body>
 <#assign labInfo><@text name="security.resource.info"/></#assign>  
<#include "/template/back.ftl"/> 
   <form name="moduleForm" action="resource.action?method=save" method="post">
   <input type="hidden" name="resource.id" value="${(resource.id)!}" style="width:200px;" />
   <input type="hidden" name="patternIds" value=""/>
   <tr>
    <td>
     <table width="80%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2"><@text name="security.resource.info"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name"><@text name="common.name"/><font color="red">*</font>:</td>
	     <td >
          <input type="text" name="resource.name" value="${resource.name!}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_title">标题<font color="red">*</font>:</td>
	     <td >
          <input type="text" name="resource.title" value="${resource.title!}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_description"><@text name="common.description"/>:</td>
	     <td >
	        <input type="text" name="resource.description" value="${resource.description!}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" >可见范围:</td>
	     <td >
	        <@resourceScopeRadio resource.scope/>
         </td>
	   </tr>
		<tr>
	   	  <td class="title"><@text "common.status"/>:</td>
	   	  <td><select  name="resource.enabled" style="width:100px;" >
		   		<option value="true" <#if (resource.enabled??)&&(resource.enabled==true)>selected</#if>><@text "action.activate"/></option>
		   		<option value="false" <#if (resource.enabled??)&&(resource.enabled==false)>selected</#if>><@text "action.freeze"/></option>
		  </select>
		</td>
		</tr>
		 <tr>
	     <td class="title">适用用户:</td>
	     <td>
	      <#list categories as category>
	      <input name="categoryIds" value="${category.id}" type="checkbox" id="categoryIds${category.id}" <#if resource.categories?seq_contains(category)>checked</#if>/>
	       <label for="categoryIds${category.id}" >${category.name}</label>
	      </#list>
	   </tr>
		<tr>
	    <td class="title" >数据限制模式:</td>
	    <td >
	     <table>
	      <tr>
	       <td>
	        <select name="Patterns" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Patterns'], this.form['SelectedPattern'])" >
	         <#list patterns as pattern>
	          <option value="${pattern.id}">${pattern.description}</option>
	         </#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['Patterns'], this.form['SelectedPattern'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedPattern'], this.form['Patterns'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	        <select name="SelectedPattern" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedPattern'], this.form['Patterns'])">
	         <#list resource.patterns! as pattern>
	          <option value="${pattern.id}">${pattern.description}</option>
	         </#list>
	        </select>
	       </td>
	      </tr>
	     </table>
	    </td>
	   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="6" >
	       <input type="button" value="<@text name="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />
	       <input type="reset"  name="reset1" value="<@text name="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript" >
   function save(form){
     form['patternIds'].value = getAllOptionValue(form.SelectedPattern);  
     var a_fields = {
         'resource.title':{'l':'标题', 'r':true, 't':'f_title'},
         'resource.name':{'l':'<@text name="common.name"/>', 'r':true,'t':'f_name'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/template/foot.ftl"/>