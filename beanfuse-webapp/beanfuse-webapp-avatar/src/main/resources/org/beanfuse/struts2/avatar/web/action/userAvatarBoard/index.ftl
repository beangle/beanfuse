<#include "/template/head.ftl"/>
<html>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<table width="100%" border="0">
    <tr>
      <td class="infoTitle" width="20%" style="height:22px;">
       <img src="${base}/static/images/action/info.gif" align="top"/><B>
          <B>照片库信息</B>
      </td>
      <td class="infoTitle" width="20%" style="height:22px;">
 		<font color="red">&nbsp;<@s.actionmessage/></font>
      </td>
    </tr>
</table>

<table align="center">
    <#list names?chunk(5) as nameList>
    <tr>
      <#list nameList as name>
      <td colspan="5">
          <img src="${base}/userAvatar!show.action?user.name=${name}" width="120px" align="top"><br><a href="userAvatarBoard!info.action?user.name=${name}">${name}</a>
      </td>
      </#list>
   </tr>
   </#list>
   <#include "/template/pageBar.ftl"/>
</table>
<form name="queryForm" action="userAvatarBoard.action" method="post"/>
<script>
function pageGoWithSize(pageNo,pageSize){
  goToPage(queryForm,pageNo,pageSize);
}
</script>
</body>
<#include "/template/foot.ftl"/>