<#include "/template/head.ftl"/>
 <body>
 <table id="groupBar"></table>
   <table  class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
  <script>
    var form=document.groupSearchForm;
    var action="group.action";
    function getIds(){
       return(getCheckBoxValue(contentFrame.document.getElementsByName("groupId")));
    }    
    function add(){
       form.action=action+"?method=edit";
       addInput(form,"groupId","");
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
    function searchGroup(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    searchGroup();
    function singleGroupAction(method){
       var groupIds = getIds();
       if(groupIds ==""||isMultiId(groupIds)) {
         window.alert('<@text name="common.singleSelectPlease"/>!');
         return;
       }
       addParamsInput(form,contentFrame.queryStr);
       addInput(form,"groupId",groupIds);
       form.action=action+"?method="+method; 
       form.submit(); 
   }
    function multiGroupAction(method){
       var groupIds = getIds();
       if(groupIds =="") {
         window.alert('<@text name="common.select"/>!');
         return;
       }
       if(window.confirm("<@text "common.confirmAction"/>")){
           addParamsInput(form,contentFrame.queryStr);
           addInput(form,"groupIds",groupIds);
           form.action = action+"?method="+method;
           form.submit();
       }
   }

   function exportData(){
      addInput(form,"keys","name,description,creator.name,createdAt,updatedAt,users");
      addInput(form,"titles","<@text "common.name"/>,<@text "common.description"/>,<@text "common.creator"/>,<@text "common.createdAt"/>,<@text "common.updatedAt"/>,<@text "group.users"/>");
      form.action=action+"?method=export";
      form.submit();
   }
   var bar = new ToolBar('groupBar','<@msg.text name="ui.indexPanel" arg0="Group"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@text "action.new"/>",add,'new.gif');
   bar.addItem("<@text "action.edit"/>","singleGroupAction('edit');",'update.gif');
   bar.addItem("<@text "action.delete"/>","multiGroupAction('remove')",'delete.gif');
   bar.addItem("<@text "action.export"/>","exportData()");
   bar.addHelp("<@text name="action.help"/>");
  </script>
</html>