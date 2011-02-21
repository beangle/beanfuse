<#include "/template/head.ftl"/>
<body>
 <table id="restrictionPatternBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="restrictionPatternId"/>   
      <@table.sortTd  width="10%" id="restrictionPattern.description" text="描述" />
      <@table.sortTd  width="70%" id="restrictionPattern.content" text="内容" />
    </@>
    <@table.tbody datas=patterns;pattern>
     <@table.selectTd id="patternId" value=pattern.id/>
         <input type="hidden" name="${pattern.id}" id="${pattern.id}" />
     </td>
     <td><a href="restriction-pattern!info.action?pattern.id=${pattern.id}">${pattern.description!("")}</a></td>
     <td>${(pattern.content)!}</td>
    </@>
  </@>
  </body>
 <@htm.actionForm name="restrictionPatternForm" entity="pattern" action="restriction-pattern.action"/>
  <script>
   var bar = new ToolBar('restrictionPatternBar','<@text name="info.module.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@text "action.add"/>","add()");
   bar.addItem("<@text "action.edit"/>","edit()");
   bar.addItem("<@text "action.export"/>","exportData()");
  </script>
  </body>
 <#include "/template/foot.ftl"/>