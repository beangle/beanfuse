<#include "/template/head.ftl"/>
<#include "scope.ftl"/>
<body>
 <table id="resourceBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="resourceId"/>   
      <@table.sortTd  width="10%" id="resource.title" text="标题" />
      <@table.sortTd  width="10%" id="resource.name" text="名称" />
      <@table.sortTd  width="10%" id="resource.description" text="描述" />
      <@table.sortTd  width="10%" id="resource.scope" text="可见范围" />
      <@table.sortTd  width="10%" id="resource.enabled" text="状态" />
    </@>
    <@table.tbody datas=resources;resource>
     <@table.selectTd id="resourceId" value=resource.id/>
         <input type="hidden" name="${resource.id}" id="${resource.id}" />
     </td>
     <td><a href="resource.action?method=info&resource.id=${resource.id}">${(resource.title)!}</a></td>
     <td>${(resource.name)!}</td>
     <td>&nbsp;${resource.description!("")}</td>
     <td><@resourceScope resource.scope/></td>
     <td><#if resource.enabled><@text name="action.activate" /><#else><font color="red"><@text name="action.freeze"/></font></#if></td>
    </@>
  </@>
  </body>
 <@htm.actionForm name="resourceForm" entity="resource" action="resource.action"/>
  <script>
   function activate(enabled){
       addInput( document.resourceForm,"enabled",enabled);
       multiAction("activate","确定操作?");
   }
   function exportData(){
       addInput(form,"titles","标题,名称,描述,状态");
       addInput(form,"keys","title,name,description,enabled");
       exportList();
   }
   function preview(){
      window.open(action+"?method=preview");
   }
   var bar = new ToolBar('resourceBar','<@text name="info.module.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@text "action.freeze"/>","activate(0)");
   bar.addItem("<@text "action.activate"/>","activate(1)");
   bar.addItem("<@text "action.add"/>","add()");
   bar.addItem("<@text "action.edit"/>","edit()");
   bar.addItem("<@text "action.delete"/>","multiAction('remove')",'delete.gif');
   bar.addItem("<@text "action.export"/>","exportData()");
  </script>
  </body>
 <#include "/template/foot.ftl"/>