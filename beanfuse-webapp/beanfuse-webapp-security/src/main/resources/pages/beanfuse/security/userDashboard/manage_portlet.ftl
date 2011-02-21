<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
    <div class="ui-widget-header ui-corner-all"><span class="title">分级管理</span><span class="ui-icon ui-icon-plusthick"></span></div>
    <div class="portlet-content">
      <table class="infoTable">
	   <tr>
        <td class="title" >&nbsp;<@text name="group" />:</td>
        <td  class="content" colspan="3">
             <#list user.groups! as group>
                  ${group.name}&nbsp;
             </#list>
         </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;<@text name="user.mngGroups" />:</td>
        <td  class="content" colspan="3">
             <#list user.mngGroups! as group>
                  ${group.name}&nbsp;
             </#list>
         </td>
       </tr>
       </table>
	</div>
</div>