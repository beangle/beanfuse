<#include "/template/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="static/scripts/common/Validator.js"></script>
<link href="${base}/static/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/TableTree.js"></script>
<script> defaultColumn=1;</script>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("menuId")));
    }
    function save(){
        document.actionForm.action="menu-authority.action?method=save";
        if(confirm("<@msg.text name="alert.authoritySave" arg0="${ao.name}"/>")){
           document.actionForm.submit();
        }
    }
    function changeProfile(){
        document.actionForm.action="menu-authority.action?method=editAuthority";
        document.actionForm.submit();
    }
</script>
<body>
<div>
  <table width="100%">
  <tr>
  <td valign="top">
  <table id="authorityBar"></table>
  <script>
   var bar = new ToolBar('authorityBar','<@text name="ui.authorityInfo" />',null,true,true);
   bar.setMessage('<@getMessage/>');

	bar.addItem("<@text name="action.spread"/>","displayAllRowsFor(2);f_frameStyleResize(self)",'contract.gif');
	bar.addItem("<@text name="action.collapse"/>","collapseAllRowsFor(2);f_frameStyleResize(self)",'expand.gif');
	
   bar.addBack("<@text  "action.back"/>");
   bar.addItem("<@text  "action.save"/>",save,'save.gif');
   
   
   function selectListen(targetId){
   	var tempTarget ;
   	tempTarget = document.getElementById(targetId);
   	if(tempTarget!=null||tempTarget!='undefined'){
	   	var stats = tempTarget.checked;
	   	var num=0;
	   	var tempId = targetId+'_'+num;
	   	while(tempTarget!=null){//||tempTarget!='undefined'
	   		num++;
	   		tempTarget.checked = stats;
	   		tempTarget = document.getElementById(tempId);
	   		tempId = targetId+'_'+num;
	   	}
   	}
   }
  </script>
  
  <table width="100%" class="searchTable" id="meunAuthorityTable">
    <form name="actionForm" method="post" action="menu-authority.action?method=editAuthority" onsubmit="return false;">
       <input type="hidden" name="group.id" value="${ao.id}"/>
	   <tr>
	    <td>${ao.name}</td>
	    <td class="title">菜单配置</td>
	    <td><select name="menuProfileId" style="width:200px;" onchange="changeProfile();">
	        <#list menuProfiles as profile>
	        <option value="${profile.id}" <#if profile.id?string=Parameters['menuProfileId']!('')>selected</#if>>${profile.name}</optino>
	        </#list>
	        </select>
	    </td>
	   </tr>
  </table>
  
  <table width="100%" class="listTable">

  <tbody>
  <tr class="darkColumn">
    <th width="5%"><input type="checkbox" onClick="treeToggleAll(this)"></th>
    <th width="40%"><@text name="common.name"/></th>
    <th width="10%"><@text name="common.id"/></th>
    <th width="20%">可用资源</th> 
    <th width="8%"><@text name="common.status"/></th>
  </tr>
    <#macro i18nTitle(entity)><#if language?index_of("en")!=-1><#if entity.engTitle!?trim=="">${entity.title!}<#else>${entity.engTitle!}</#if><#else><#if entity.title!?trim!="">${entity.title!}<#else>${entity.engTitle!}</#if></#if></#macro>       	
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
    
    
    <tr class="grayStyle" id="${tdid}">
	   <td   class="select">
    	   <input type="checkBox" id="checkBox_${menu_index}" onclick="treeToggle(this);selectListen('checkBox_${menu_index}');"  name="menuId" <#if (aoMenus??)&&(aoMenus?seq_contains(menu))>checked</#if> value="${menu.id}">
       </td>
	   <td>
	    <div class="tier${menu.code?length/2}">
           <#if menu.leaf>
           <a href="#" class="doc"></a><#rt>
           <#else>
           <a href="#" class="folder" onclick="toggleRows(this);f_frameStyleResize(self)"></a><#rt>
           </#if>
	       <#if menu.leaf>
             <@i18nTitle menu/>
           <#else>
            <@i18nTitle menu/>
           </#if>
	    </div>
	   </td>
       <td >&nbsp;${menu.code}</td>
       <td>
       	<#list menu.resources as resource>
       	   <#if resources?seq_contains(resource)>
       	   <input type="checkBox" name="resourceId" id="checkBox_${menu_index}_${resource_index}" <#if aoResources?seq_contains(resource)>checked</#if> value="${resource.id}"><#rt>
	       <#if ((resource.patterns?size)>0)&&aoResources?seq_contains(resource)>
	       <a href="restriction.action?method=info&forEdit=1&restrictionType=authority&restriction.holder.id=${aoResourceAuthorityMap[resource.id?string]}" target="restictionFrame" ><font color="red">${resource.title}</font></a><#rt>
	       <#else><#lt>${resource.title}</#if><#if resource_has_next><br></#if>
       	   </#if>
       	</#list>
       </td>
       <td> <#if menu.enabled><@text name="action.activate" /><#else><@text name="action.unactivate" /></#if></td>
	  </tr>
	 </#list>
  </tbody>
  </form>
 </table>
</div>
   </td>
   <td id="dataRealmTD" style="width:300px" valign="top" >
     <iframe  src="restriction.action?method=tip" id="restictionFrame" name="restictionFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" 	 frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
<#include "/template/foot.ftl"/>