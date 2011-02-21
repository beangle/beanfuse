<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="static/scripts/common/Validator.js"></script>
 <script>
 function listAuthorityInfo(srcSelect){
	for (var i=0; i<srcSelect.length; i++){	   
		if (srcSelect.options[i].selected){ 		 
			var op = srcSelect.options[i];
			document.authorityForm.action = "menu-authority.action?method=editAuthority&group.id="+op.value;
			document.authorityForm.target="authorityInfoFrame";
			document.authorityForm.submit();
		 }
	 }
 }
 </script> 
 <body>
  <table id="authorityBar"></table>
  <script>
    var bar = new ToolBar("authorityBar",'<@text name="ui.authorityIndex"/>',null,true,true);
    bar.addHelp("帮助");
  </script>
  <table width="100%" border="0"  >
   <tr>
    <td style="width:160px;" valign="top">
      <form name="mngGroupForm">
         <table  height="100%" style="font-size:12px">
          <tr>
          <td><@text name="tip.authority.selectGroup"/>:</td>
          </tr>         
          <tr>
           <td >
            <select name="Groups" MULTIPLE size="10" onDblClick="JavaScript:listAuthorityInfo(this.form['Groups'])" style="height:280px;width:150px">
             <#assign mngGroups=manager.mngGroups/>
             <#if allGroups??><#assign mngGroups=allGroups/></#if>
             <#list mngGroups?sort_by("name")! as group>
              <option value="${group.id}">${group.name}</option>
             </#list>
            </select>
           </td>
          </tr>
        </table>   
       </form>
     </td>

     <td valign="top">
     <iframe  src="menu-authority.action?method=prompt" id="authorityInfoFrame" name="authorityInfoFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
     </tr>
     <table>

    <form name="authorityForm" method="post" action="menu-authority.action?method=info">
    <input type="hidden" name="groupId" value="" />
    <input type="hidden" name="userId" value="" />
    </form>
    
 </body>
<#include "/template/foot.ftl"/> 