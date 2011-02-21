<#include "/template/head.ftl"/>
<div align="center">
<form name="form1" method="post" action="${base}/database/sql-execute!executeSql.action">
  <table class="emptyTable" cellpadding="0" cellspacing="0">
    <tr>
      <td><div align="left">SQL语句：</div>
            <textarea name="sql" cols="100" rows="5" id="sql">${Parameters['sql']!}</textarea>
        </td>
    </tr>
    <tr>
      <td><div align="right"><input type="submit"  name="Submit" value="-提交-"></div>
        </td>
    </tr>
  </table>
</form>
</div>

<#if meta??>
<@table.table id="listTable" width="90%" sortable="false" align="center">
  <@table.thead>
      <@table.td width="5%" text="序号"/>
      <#list meta.columnNames as columnName>  
      <@table.td text=columnName />
      </#list>
    </@>
    ${rowSet.last()?string("","")}
    <#assign count=rowSet.row/>
    ${rowSet.beforeFirst()}
    <#list 1..count as i>
    <tr align="center">
     <#if rowSet.next()>
       <td >${i}</td>
       <#list meta.columnNames as columnName>
	   <td>${(rowSet.getObject(columnName)?string)!}</td>
	   </#list>
	  </#if>
    </tr>
    </#list>
  </@>
<#else>
更改成功
</#if>
  <br/><br/>
<#include "/template/foot.ftl"/>