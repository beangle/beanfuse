<#include "/template/head.ftl"/>
<link href="${base}/static/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/TableTree.js"></script>
<script> defaultColumn=1;</script>
<body>
 <table id="menuBar"></table>
 <@table.table width="100%" sortable="true" id="listTable">
   <tr>
   	  <td>
  <table width="100%" class="listTable">
  <tbody>
  <tr class="darkColumn">
    <th width="5%"><input type="checkbox" onClick="treeToggleAll(this)"></th>
    <th width="30%">标题</th>
    <th width="30%">英文标题</th>
    <th><@text name="common.code"/></th>
    <th><@text name="common.status"/></th>
  </tr>
  <#assign firstLevel=0>
    <#if Parameters['menu.code']?? >
       <#assign firstLevel=Parameters['menu.code']?length>
    </#if>
    <#if (firstLevel<2)> <#assign firstLevel=2></#if>
    
  <#list menus?sort_by("code") as menu>
	<#assign tdid="1-">
    <#if menu.code?length!=1>
	    <#list 1..menu.code?length as menuIdChar>
	        <#if menuIdChar%2==1>
	        <#assign tdid = tdid + menu.code[menuIdChar-1..menuIdChar] +"-">
	        </#if>
	    </#list>
    </#if>
    <#assign tdid = tdid[0..tdid?length-2]>
    
    <tr <#if (menu.code?length >firstLevel)>style="display: none;"</#if> id="${tdid}"  onmouseover="swapOverTR(this,this.className)" 
         onmouseout="swapOutTR(this)" onclick="onRowChange(event);"
         title="${menu.entry!}">
       <td   class="select">
    	   <input type="checkBox" name="menuId" value="${menu.id}" onclick="treeToggle(this,false)">
       </td>
	   <td>
	    <div class="tier${menu.code?length/2}">
	    <#if (menu.leaf)>
    	    <a href="#" class="doc">
	    <#else>
	        <a href="#" class="folder_open"  onclick="toggleRows(this)" >   </a>
        </#if>
	       <a href="menu.action?method=info&menu.id=${menu.id}" >${menu.title}</a>
	    </div>
	   </td>
	   <td>&nbsp;${menu.engTitle!}</td>
	   <td>&nbsp;${menu.code}</td>
       <td><#if menu.enabled><@text name="action.activate" /><#else><font color="red"><@text name="action.freeze"/></font></#if></td>
	  </tr>
	 </#list>
  </tbody>
 </table>
 </td>
</tr>
  <@table.tbody datas=[1];temp></@>
 </@>
 
 <@htm.actionForm name="menuForm" entity="menu" action="menu.action">
 <input name="menu.profile.id" type="hidden" value="${Parameters['menu.profile.id']}"/>
 <input name="params" type="hidden" value="&menu.profile.id=${Parameters['menu.profile.id']}"/>
 </@>
  <script>
   function activate(isActivate){
       addInput( document.menuForm,"isActivate",isActivate);
       multiAction("activate","确定操作?");
   }
   function exportData(){
       addInput(form,"titles","代码,标题,入口,描述,是否可用");
       addInput(form,"keys","code,title,entry,description,enabled");
       exportList();
   }
   function preview(){
      window.open(action+"?method=preview<#list Parameters?keys as key>&${key}=${Parameters[key]}</#list>");
   }
   //展开所有菜单
   displayAllRowsFor(1);
   
   var bar = new ToolBar('menuBar','<@text name="info.module.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   
   bar.addItem("<@text "action.new"/>",'add()','new.gif');
   bar.addItem("<@text "action.edit"/>","singleAction('edit');",'update.gif');
   
   bar.addItem("<@text "action.freeze"/>","activate(0)");
   bar.addItem("<@text "action.activate"/>","activate(1)");
   bar.addItem("<@text "action.export"/>","exportData()");
   bar.addItem("<@text "action.delete"/>","multiAction('remove')",'delete.gif');
   bar.addItem("打印预览","preview()","print.gif");
  </script>
  </body>
 <#include "/template/foot.ftl"/>