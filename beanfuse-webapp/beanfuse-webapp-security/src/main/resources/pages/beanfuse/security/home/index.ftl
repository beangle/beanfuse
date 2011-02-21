<#include "/template/simpleHead.ftl"/>
<link href="${base}/static/css/menu.css" rel="stylesheet" type="text/css">
<style>
#BakcGroup_image_td {
  background-repeat: repeat;
  background-position: center center;
  background-image: url(images/home/push_top.jpg);
}
</style>
<body style="overflow-y:hidden;">

<table width="100%"  style="width:100%;background-color:#e0ecff" border="0" cellpadding="0" cellspacing="0" id="logoTable" style="" >
  <tr>
   <td width="995" ALIGN="right" VALIGN="bottom">
    <table  border="0" height="100%" >
     <tr>
       <td colspan="4" align="right" valign="top" style="font:14px;color:green" id="changeCategoryTd"><B>身份切换</B></td>
       <td align="right" valign="top" height="100%">
        
        <select name="user.category" onchange="changeUserCategory(this.value);" id="userCategorySelect" style="display:none;width:100px" ></select>
        </td>
        <td align="right" valign="top" >
         <img src="${base}/static/images/home/modifyPassword.jpg" style="cursor:pointer;" onClick="changePassword()" alt="<@text 'action.changePassword'/>"/>&nbsp;
         <img src="${base}/static/images/home/goHome.jpg" style="cursor:pointer;" onClick="home()" alt="<@text 'action.backHome'/>"/>&nbsp;
         <img src="${base}/static/images/home/logout.jpg" style="cursor:pointer;" onClick="logout()" alt="<@text 'action.logout'/>" />
       </td>
     </tr>
     <tr>
      <#macro i18nNameTitle(entity)><#if language?index_of("en")!=-1><#if (entity.engTitle!"")?trim=="">${entity.title!}<#else>${entity.engTitle!}</#if><#else><#if entity.title!?trim!="">${entity.title!}<#else>${entity.engTitle!}</#if></#if></#macro>      
       <#list menus! as module>
       <td align="center" valign="bottom" width="100" height="20%">
        <A style="cursor:hand" HREF="home.action?method=moduleList&parentCode=${module.code}" target="leftFrame" >
          <span CLASS="menu_blue_13px2" onclick="changeColor(this);"><@i18nNameTitle module/></span>
        </A>
       </td>
       </#list>
       <#if !menus??>
       <td align="center" valign="bottom" width="100" height="23" ></td>
       </#if>
     </tr>     
    </table>
   </td>
  </tr>
</table>
 
<#assign parentCode><#if (menus?size>0)>${menus?first.code!}<#else>${parentCode!}</#if></#assign>
<table id="mainTable" style="width:100%;height:95%" cellpadding="0" cellspacing="0" border="0">
 <tr onClick="verticalSwitch()">
  <td width="100%" colspan="3" VALIGN="TOP" height="16" id="BakcGroup_image_td" style="background-image: url(images/home/push_top.jpg);font-size:0pt">&nbsp;</td>
 </tr>
 <tr>
   <td style="HEIGHT:100%;width:14%" border="0" id="leftTD">
	   <iframe HEIGHT="100%" WIDTH="100%" SCROLLING="AUTO" 
	       FRAMEBORDER="0" src="home.action?method=moduleList&parentCode=${parentCode}" name="leftFrame" >
	   </iframe>
   </td>
   <td width="0%" height="100%" bgcolor="#ffffff" style="cursor:w-resize;">
	   <a onClick="horizontalSwitch('left_tag')">
	      <img id="left_tag" style="cursor:hand" src="${base}/static/images/home/push_left.jpg" border="0" >
	   </a>
    </td>
   <td style="width:86%" id="rightTD">
	   <iframe HEIGHT="100%" WIDTH="100%" SCROLLING="auto" 
	     FRAMEBORDER="0" src="home.action?method=welcome" name="main" id="main">
	   </iframe>
   </td>
 </tr>
</table>
<div  id="msgNotificationDIV" style="display:none;width:300px;height:150px;position:absolute;bottom:0px;right:0px;border:solid;border-width:1px;background-color:#94aef3 ">
<iframe HEIGHT="100%" WIDTH="100%" SCROLLING="AUTO" 
	       FRAMEBORDER="0" src="#" name="msgFrame" >
</iframe>
</div>
</body>
<script>
	var obj = null;
	function changeColor(field){
		field.className = "menuSelected";
		if(obj != null && obj != field)obj.className = "menu_blue_13px2";
		obj = field;
		//home();
	}   
  var categories ={<#list categories as category>'${category.name}':${category.id}<#if category_has_next>,</#if></#list>};
  var categoryValue = ${Session['security.categoryId']};
  var userCategorySelect = document.getElementById('userCategorySelect');
  for(var category in categories){
     if(1){
        userCategorySelect.options[userCategorySelect.options.length]=new Option(category,categories[category]);
     }
  }
  
  if( userCategorySelect.options.length>1){
     userCategorySelect.style.display="block";
     var curCategory=${Parameters['user.category']!(1)};
     for(var i=0;i<userCategorySelect.options.length;i++){
         if(userCategorySelect.options[i].value==curCategory){
             userCategorySelect.options[i].selected=true;
         }
     }
  }else{
     document.getElementById("changeCategoryTd").innerHTML="";
  }
  
  function home() {
      main.location="home.action?method=welcome";
  }  
  function logout() {
      self.location = 'logout.action';
  }
  function changePassword(){
      var url = "${base}/security/my-account.action?method=edit";
      var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	  selector.moveTo(200,200);
	  selector.resizeTo(300,250);
  }
  function changeUserCategory(category){
     self.location="home.action?method=index&user.category="+category;
  }
  //调整水平比例
  function horizontalSwitch(id) {
      var fullpath = document.getElementById(id).src;
      var filename = fullpath.substr(fullpath.lastIndexOf("/")+1, fullpath.length);
	  switch (filename) {
			case "push_left.jpg":
				document.getElementById(id).src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"pull_left.jpg";
				break;
			case "pull_left.jpg":
				document.getElementById(id).src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"push_left.jpg";
				break;	
	  }
      if(leftTD.style.width=='14%'){
        leftTD.style.width="0%";rightTD.style.width="100%";
      }else{
         leftTD.style.width="14%";rightTD.style.width="86%";
      }
   }
  //垂直调整比例
  function verticalSwitch(){
    logoTable.style.display = (logoTable.style.display == "none") ? "" : "none";
    imageString = BakcGroup_image_td.style.backgroundImage;
    if(imageString.indexOf("pull")>0){
 		newImage = "url(images/home/push_top.jpg)";
	}else{
		newImage = "url(images/home/pull_top.jpg)";
	}
    BakcGroup_image_td.style.backgroundImage = newImage;
    if (mainTable.style.height=='100%') {
        mainTable.style.height='95%';
        //logoTable.style.height='80px';
    }else{
        mainTable.style.height='100%';
        logoTable.style.height='0%';
    }
  }
</script>
<#include "/template/foot.ftl"/>