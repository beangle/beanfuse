<#include "/template/head.ftl"/>
<body>
 <table id="paramBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="paramId"/>   
      <@table.sortTd  width="10%" id="param.name" text="名称" />
      <@table.sortTd  width="10%" id="param.description" text="描述" />
      <@table.sortTd  width="10%" id="param.multiValue" text="是否多值" />
      <@table.sortTd  width="10%" id="param.type" text="类型" />
      <@table.sortTd  width="10%" id="param.editor.idProperty" text="值属性" />
      <@table.sortTd  width="10%" id="param.editor.properties" text="显示属性" />
    </@>
    <@table.tbody datas=params;param>
     <@table.selectTd id="paramId" value=param.id/>
         <input type="hidden" name="${param.id}" id="${param.id}" />
     </td>
     <td title="${(param.editor.source)!}">${(param.name)!}</td>
     <td>${param.description!("")}</td>
     <td>${(param.multiValue)?string("是","否")}</td>
     <td>${(param.type)!}</td>
     <td>${(param.editor.idProperty)!}</td>
     <td>${(param.editor.properties)!}</td>
    </@>
  </@>
  </body>
 <@htm.actionForm name="paramForm" entity="param" action="param.action">
   <input type="hidden" name="paramGroup.id" value="${Parameters['paramGroup.id']!}">
 </@>
  <script>
   var bar = new ToolBar('paramBar','参数列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@text "action.new"/>","add()");
   bar.addItem("<@text "action.edit"/>","edit()");
   bar.addItem("<@text "action.delete"/>","remove()");
  </script>
  </body>
 <#include "/template/foot.ftl"/>