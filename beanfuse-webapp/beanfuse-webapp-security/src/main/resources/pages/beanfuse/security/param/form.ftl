<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/Validator.js"></script>
 <body>
 <#assign labInfo><@text name="info.moduleUpdate"/></#assign>  
<#include "/template/back.ftl"/> 
   <form name="moduleForm" action="param.action?method=save" method="post">
   <input type="hidden" name="param.id" value="${(param.id)!}" style="width:200px;" />
   <tr>
    <td>
     <table width="80%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2">数据限制参数</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_name"><@text name="common.name"/><font color="red">*</font>:</td>
	     <td>
          <input type="text" name="param.name" value="${param.name!}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_type">类型<font color="red">*</font>:</td>
	     <td >
          <input type="text" name="param.type" value="${param.type!}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_description">标题<font color="red">*</font>:</td>
	     <td >
	        <input name="param.description" value="${param.description!}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_multiValue">是否多值<font color="red">*</font>:</td>
	     <td ><input type="radio" <#if (param.multiValue)!(true)>checked</#if> value="1" name="param.multiValue">是
	          <input type="radio" <#if !(param.multiValue)!(true)>checked</#if> value="0" name="param.multiValue">否
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_referenceType">引用类型:</td>
	     <td >
          <input type="text" name="param.editor.source" value="${(param.editor.source)!}" style="width:400px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_valueProperty">值属性:</td>
	     <td >
	        <input  name="param.editor.idProperty" value="${(param.editor.idProperty)!}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_titleProperty">显示属性:</td>
	     <td >
	        <input name="param.editor.properties" value="${(param.editor.properties)!}"/>
         </td>
	   </tr>
    <td class="title" id="f_group"><font color="red">*</font>参数组:</td>
    <td >
     <table>
      <tr>
       <td>
        <select name="Groups" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Groups'], this.form['SelectedGroup'])" >
         <#list paramGroups?sort_by('name') as group>
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
         <#list param.paramGroups! as group>
          <option value="${group.id}">${group.name}</option>
         </#list>
        </select>
       </td>
      </tr>
     </table>
    </td>
   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="6"  >
	       <input type="hidden" name="paramGroupIds" value=""/>
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
     form['paramGroupIds'].value = getAllOptionValue(form.SelectedGroup);
     var a_fields = {
         'param.name':{'l':'<@text name="common.name"/>', 'r':true,'t':'f_name'},
         'param.description':{'l':'标题', 'r':true, 't':'f_description'},
         'param.type':{'l':'类型', 'r':true, 't':'f_type'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/template/foot.ftl"/>