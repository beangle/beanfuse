<#include "/template/head.ftl"/>
<link href="${base}/static/themes/default/css/panel.css" rel="stylesheet" type="text/css">
<#assign cewolf=JspTaglibs["/WEB-INF/cewolf.tld"]>
<body>
  <table id="userInfoBar"></table>
  <div id="category" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('category');">用户分类(允许在线最大数)</a>
     </h2>
     <div class="modulebody">
	  <@cewolf.chart  id="line"  title=""   type="pie" 
		  xaxislabel="Page"  yaxislabel="Views" showlegend=false
		  backgroundimagealpha=0.5>   
		 <@cewolf.data>
		    <@cewolf.producer id="categoryProfilesData"/>    
		 </@cewolf.data>
		 </@cewolf.chart>
		 <p>
		 <@cewolf.img chartid="line" renderer="cewolf" width=400 height=200/>
     </div>
  </div>
  
  <div id="userStat" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('userStat');">用户统计</a>
     </h2>
     <div class="modulebody">
	  <@cewolf.chart  id="line2"  title=""   type="pie" 
		  xaxislabel="Page"  yaxislabel="Views" showlegend=false
		  backgroundimagealpha=0.5>   
			 <@cewolf.data>
			    <@cewolf.producer id="userStatData"/>    
			 </@cewolf.data>
		 </@cewolf.chart>
		 <p>
		 <@cewolf.img chartid="line2" renderer="cewolf" width=400 height=200/>
    </div>
   </div>
   
   <div id="groupStat" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('groupStat');">用户组统计</a>
     </h2>
     <div class="modulebody">
  	 <@table.table width="100%" id="groupStat" sortable="true">
	    <@table.thead>
	      <@table.td width="15%" text="类别"/>
	      <@table.td width="10%" text="状态"/>
	      <@table.td width="15%" text="数量"/>
	   </@>
	   <@table.tbody datas=groupStat;stat>
	      <td>${categories[stat[0]?string].name}</td>
	      <td>${stat[1]?string("激活","禁用")}</td>
	      <td>${stat[2]}</td>
	   </@>
	  </@>
    </div>
   </div>
   
  <div id="menu" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('menu');">菜单设置</a>
     </h2>
     <div class="modulebody">
	     <@table.table width="100%" id="groupStat" sortable="true">
		    <@table.thead>
		      <@table.td width="15%" text="类别"/>
		      <@table.td width="10%" text="状态"/>
		      <@table.td width="15%" text="菜单数"/>
		   </@>
	 		<#list menuProfiles as profile>
		   <@table.tbody datas=menuStats[profile.id?string];stat>
		      <td>${profile.name}</td>
		      <td>${stat[0]?string("激活","禁用")}</td>
		      <td>${stat[1]}</td>
		   </@>
			</#list>
		  </@>
    </div>
   </div>
   
  <div id="resource" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('resource');">系统资源</a>
     </h2>
     <div class="modulebody">
     	   <@table.table width="100%" id="resourceStat" sortable="true">
		    <@table.thead>
		      <@table.td width="10%" text="状态"/>
		      <@table.td width="15%" text="资源数"/>
		   </@>
		   <@table.tbody datas=resourceStat;stat>
		      <td>${stat[0]?string("激活","禁用")}</td>
		      <td>${stat[1]}</td>
		   </@>
		  </@>
     </div>
</div>

    
  <div id="restriction" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('restriction');">数据权限</a>
     </h2>
     <div class="modulebody">
     	 限制模式数量:${patternStat[0]}<br>
     	 模式参数数量:${paramStat[0]}<br>
     </div>
</div>

  <script>
   var bar = new ToolBar('userInfoBar','权限系统面板',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addClose("<@text name="action.close"/>");
   
   function _wi_tm(moudleId){
       var id= document.getElementById(moudleId);   
	   if(id.className=="module collapsed"){
	     id.className="module expanded";
	   }else{
	     id.className="module collapsed";
	   }
  }
  </script>
 </body>
<#include "/template/foot.ftl"/>