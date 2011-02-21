<#include "/template/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<@getMessage/>
 <@table.table width="100%" id="listTable" sortable="true">
   <@table.thead>
     <@table.selectAllTd id="userId"/>
     <@table.sortTd width="10%" id="user.name" name="user.name"/>
     <@table.sortTd width="10%" id="user.fullname" name="user.fullname"/>
     <@table.sortTd width="30%" id="user.mail" name="common.email" />
   	 <@table.sortTd width="10%" id="user.creator.fullname" name="common.creator" />
  	 <@table.sortTd width="15%" id="user.updatedAt" name="common.updatedAt" />
   	 <@table.sortTd width="10%" id="user.status" name="common.status" />
   </@>
   <@table.tbody datas=users;user>
     <@table.selectTd id="userId" value="${user.id}"/>
     <td><A href="user-dashboard.action?method=dashboard&user.id=${user.id}" target="_blank">&nbsp;${user.name} </a></td>
     <td>${user.fullname!("")}</td>
     <td>${user.mail}</td>
     <td>${(user.creator.fullname)!}</td>
     <td>${user.updatedAt?string("yyyy-MM-dd")}</td>
     <td>
      <#if user.enabled ==true><@text name="action.activate" /></#if>
      <#if user.enabled ==false><@text name="action.freeze" /></#if>
     </td>
   </@>
 </@>
 </body>
<#include "/template/foot.ftl"/>