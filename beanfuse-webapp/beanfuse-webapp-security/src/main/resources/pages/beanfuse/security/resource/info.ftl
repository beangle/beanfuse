<#include "/template/head.ftl"/>
<#include "scope.ftl"/>
<body>
<table id="resourceInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;标题:</td>
	     <td class="content">${resource.title}</td>
	     <td class="title" >&nbsp;<@text name="common.name"/>:</td>
         <td class="content">${resource.name}</td>
	   </tr>
       <tr>
        <td class="title" >&nbsp;<@text name="common.description"/>:</td>
        <td  class="content">${resource.description!}</td>
	    <td class="title">&nbsp;<@text name="common.status"/>:</td>
        <td class="content">
            <#if resource.enabled><@text name="action.activate" /><#else><@text name="action.unactivate"/></#if>
        </td>
       </tr>
       <tr>
	     <td class="title" >&nbsp;引用菜单:</td>
         <td class="content"><#list menus as menu>(${menu.code})${menu.title}<br></#list></td>
         <td class="title">&nbsp;可见范围:</td>
         <td class="content"><@resourceScope resource.scope/></td>
       </tr>
       <tr>
	     <td class="title">适用用户:</td>
	     <td>
	      <#list categories as category>
	       <#if resource.categories?seq_contains(category)>[${category.name}]</#if>
	      </#list>
	   </tr>
      </table>
      <#list resource.patterns as pattern>
      <fieldSet  align=center> 
 	   <legend>数据权限</legend>
	   <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;描述:</td>
	     <td class="content">${pattern.description}</td>
	     <td class="title" >&nbsp;参数组:</td>
         <td class="content">${pattern.paramGroup.name}</td>
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;限制模式:</td>
         <td class="content" colspan="3">${pattern.content}</td>
	   </tr>
       </table>
	  </fieldSet>
	  </#list>
  <script>
   var bar = new ToolBar('resourceInfoBar','<@text name="security.resource.info"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@text name="action.back"/>");  
  </script>
 </body>
<#include "/template/foot.ftl"/>