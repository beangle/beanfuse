<#include "/template/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <@getMessage/>
  <@table.table width="100%" align="left" id="groupListTable" sortable="true">
	   <@table.thead>
	     <@table.selectAllTd id="groupId"/>
	     <@table.sortTd id="userGroup.name" width="15%" name="common.name"/>
   	     <@table.sortTd width="15%" id="userGroup.creator.name" name="common.creator"/>
  	     <@table.sortTd width="15%" id="userGroup.updatedAt" name="common.updatedAt"/>
  	     <@table.sortTd width="15%" id="userGroup.remark" text="适用身份"/>
  	     <@table.sortTd width="15%" id="userGroup.enabled" name="common.status"/>
	   </@>
	   <@table.tbody datas=groups;group>
        <@table.selectTd type="checkbox" id="groupId" value="${group.id}"/>
	    <td ><A href="group.action?method=info&groupId=${group.id}" >${group.name} </a></td>
        <td >${group.creator.name}</td>        
        <td >${group.updatedAt?string("yyyy-MM-dd")}</td>
        <td ><#if group.category??>${group.category.name}</#if></td>
        <td ><#if group.enabled><@text name="action.activate" /><#else><font color="red"><@text name="action.freeze"/></font></#if></td>
	   </@>
    </@>
 </body>
<#include "/template/foot.ftl"/>