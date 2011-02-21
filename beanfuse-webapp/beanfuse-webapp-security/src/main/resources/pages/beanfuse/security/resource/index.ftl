<#include "/template/head.ftl"/>
<#include "scope.ftl"/>
<body> 
 <table id="resourceBar"></table>
 
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
 <script>
   var form=document.pageGoForm;
   var action="menu.action";
   function search(){
      form.submit();
   }
   function redirectTo(url){
     window.open(url);
   }
   search();
   var bar = new ToolBar('resourceBar','<@text "ui.resourceIndex"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("权限仪表盘","redirectTo('dashboard.action')");
   bar.addItem("数据限制模式","redirectTo('restriction-pattern.action?method=search')");
   bar.addItem("数据限制参数","redirectTo('param.action?method=index')");
   bar.addHelp();
  </script>
</script>
</body>
<#include "/template/head.ftl"/>