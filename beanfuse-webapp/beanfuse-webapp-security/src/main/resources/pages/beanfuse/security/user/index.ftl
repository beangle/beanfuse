<#include "/template/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/Menu.js"></script> 
 <body>
   <table id="userBar"></table>
   <table  class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
    </iframe>
    </td>
   </tr>
  </table>
 </body>
  <script>
    var form=document.userSearchForm;
    var action="user.action";
    function getIds(){
       return(getCheckBoxValue(contentFrame.document.getElementsByName("userId")));
    }
    function storeParams(){
       addParamsInput(form,contentFrame.queryStr);
    }
    function searchUser(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    searchUser();
    function removeUser(){
      storeParams();
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@text name="common.selectPlease"/>!');
         return;
       }
       if(window.confirm("<@text name="alert.userRemove"/>")){
           form.action = action+"?method=remove&userIds="+userIds;
           form.submit();
       }
       else return;
    }
    function activateUser(isActivate){
       storeParams();
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@text name="common.selectPlease"/>!');
         return;
       }
       form.action =action+ "?method=activate&userIds="+userIds+"&isActivate="+isActivate; 
       form.submit();
    }
    function addUser(){
       storeParams();
       form.action=action+"?method=edit&user.id=";
   	   form.submit();
    }
    function modifyUser(){
       storeParams();
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@text name="common.select"/>!');
         return;
       }
       if(isMultiId(userIds)){alert("<@text name="common.singleSelectPlease"/>");return;}
       form.action=action+"?method=edit&user.id="+userIds;
   	   form.submit();
    }
    function exportUserList(){
       addInput(form,"keys","name,password,mail,creator.name,createdAt,updatedAt,groups,managers,mngGroups")
       addInput(form,"titles","登录名,密码,电子邮件,创建者,创建时间,修改时间,用户组,管理者,管理用户组");
       form.action=action+"?method=export";
       form.submit();
    }
    function updateUserAccount(){
       var userId = getIds();
       if(userId =="" ||isMultiId(userId)) {
         window.alert("<@text name="common.singleSelectPlease"/>");
         return;
       }
        window.open("password.action?method=editUser&user.id="+userId);
    }
    //二级管理
    var managementAction = "management.action";
    function removeManagement(kind){
       var userIds =	 getIds();
       if(userIds =="") {
         window.alert('<@text name="common.select"/>!');
         return;
       }       
       if((kind !="mngUsers")&&(kind !="mngGroups")&&(kind !="both")) alert("what do you want?");
       if(window.confirm("<@text name="alert.manageRemove"/>")){
          storeParams();
          form.action = managementAction+"?method=removeManagement&userIds="+userIds+"&kind=" + kind;
          form.submit();
       }
       else return;
    } 
    function editManagement(){
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@text name="common.select"/>!');
         return;
       }
       if(isMultiId(userIds)){alert("<@text name="common.singleSelectPlease"/>");return;}
       storeParams();
       form.action=managementAction+"?method=editManagement&user.id="+userIds;
       form.submit();
   }
    
  
   var bar = new ToolBar('userBar','<@text "ui.userIndex"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@text "action.new"/>",addUser,'new.gif');
   bar.addItem("<@text "action.modify"/>",modifyUser,'update.gif');
   bar.addItem("<@text "action.changePassword"/>",updateUserAccount);
   bar.addItem("<@text "action.freeze"/>","activateUser('false')",'prohibit.gif');
   bar.addItem("<@text "action.activate"/>","activateUser('true')",'newUser.gif');
   bar.addItem("<@text "action.delete"/>",removeUser,'delete.gif');
   bar.addItem("<@text "action.export"/>",exportUserList,'excel.png');   
  </script>
</html>