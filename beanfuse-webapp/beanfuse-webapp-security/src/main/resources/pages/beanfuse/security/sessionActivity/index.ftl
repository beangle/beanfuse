<#include "/template/head.ftl"/>
 
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','用户登录统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@text "action.help"/>");
</script> 
   <@s.form id="form" theme="xhtml" cssClass="frameTable_title">    
      <tr>
       <td  id="viewTD0" class="transfer"  onclick="javascript:changeView1('view0',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${base}/static/images/action/list.gif" valign="top" >登录查询</font>
       </td>
       <td  id="viewTD1" class="padding" onclick="javascript:changeView1('view1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${base}/static/images/action/list.gif" align="bottom" >次数统计</font> 
       </td>
       <td  id="viewTD2" class="padding" onclick="javascript:changeView1('view2',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${base}/static/images/action/list.gif" align="bottom" >分段统计</font> 
       </td>
		<td>登录时间从:</td>
		<td><input name="startTime" id="startTime" value="" style="width:130px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"  class="Wdate"  maxlength="10"/></td>
		<td>到:</td>
		<td><input name="endTime" id="endTime" value=""  style="width:130px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"  class="Wdate"  maxlength="10"/></td>
       <td width="30%">
       </td>
      </tr>
   </@s.form>
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" style="width:160px"  style="font-size:10pt">
     <#include "statForm.ftl"/>
	<td>
	<td valign="top">
     	<iframe name="statFrame" id="statFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<script>
    viewNum=3;
    function changeView1(id,event){
     changeView(getEventTarget(event));
     for(i=0;i<3;i++){
       document.getElementById("view"+i).style.display = "none";
     }
     document.getElementById(id).style.display = "block"; 
    }
   
	var action="session-activity.action";
	
 	function addTimeParam(form){
 		addInput(form,"startTime",document.getElementById("startTime").value);
 		addInput(form,"endTime",document.getElementById("endTime").value);
 	}
 	function loginCountStat(){
 	    var form =document.groupForm;
 	    addTimeParam(form);
 	 	form.action=action+"?method=loginCountStat";
 		form.submit();
 	}
    function timeIntervalStat(){
        var form =document.numForm;
        addTimeParam(form);
 	 	form.action=action+"?method=timeIntervalStat";
 		form.submit();
 	}
    function search(){
        var form =document.userForm;
        addTimeParam(form);
 		form.action=action+"?method=search";
 		form.submit();
 	}
 	search();
</script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/ViewSelect.js"></script> 
</body>
<#include "/template/foot.ftl"/>