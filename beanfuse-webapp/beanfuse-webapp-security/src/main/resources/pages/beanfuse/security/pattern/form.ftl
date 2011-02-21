<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/Validator.js"></script>
 <body>
 <#assign labInfo><@text name="security.restrictionPattern.info"/></#assign>  
<#include "/template/back.ftl"/> 
   <form name="moduleForm" action="restriction-pattern.action?method=save" method="post">
   <input type="hidden" name="pattern.id" value="${(pattern.id)!}" style="width:200px;" />
   <tr>
    <td>
     <table width="70%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2">数据限制模式</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_description">描述<font color="red">*</font>:</td>
	     <td >
	        <input name="pattern.description" value="${pattern.description!}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_pattern">模式<font color="red">*</font>:</td>
	     <td >
          <textarea  style="width:500px;" rows="4" name="pattern.content" >${pattern.content!}</textarea>     
         </td>
	   </tr>
   <tr>
    <td class="title" id="f_params">参数组:</td>
    <td >
        <select name="pattern.paramGroup.id"  style="width:200px">
         <#list paramGroups as group>
          <option value="${group.id}" <#if group.id=(pattern.paramGroup.id)!(0)>selected</#if>>${group.name}</option>
         </#list>
        </select>
    </td>
   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="6"  >
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
     var a_fields = {
         'pattern.description':{'l':'标题', 'r':true, 't':'f_description'},
         'pattern.content':{'l':'模式', 'r':true, 't':'f_pattern'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/template/foot.ftl"/>