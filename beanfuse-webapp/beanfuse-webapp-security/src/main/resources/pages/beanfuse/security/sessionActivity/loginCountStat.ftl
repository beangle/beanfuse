﻿<#include "/template/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
   	<@table.table width="100%" sortable="true" id="listTable">
  		<@table.thead>
  			<@table.sortTd text="登录名" id="sessionActivity.name"/>
  			<@table.sortTd text="姓名" id="sessionActivity.fullname"/>
  			<@table.sortTd text="登录次数" id="count(sessionActivity.name)"/>
  		</@>
   		<#assign total=0>
   		<@table.tbody datas=loginCountStats;logonStat>
   			<td>${logonStat[0]}</td>
   			<td>${logonStat[1]}</td>
   			<td><#if logonStat[2]!=0><A href="session-activity.action?method=search&sessionActivity.name=${logonStat[0]}&startTime=${Parameters['startTime']}&endTime=${Parameters['endTime']}" >${logonStat[2]}</A></#if></td>
   			<#assign total=total+logonStat[2]>
   		</@>
  	</@>
 </body>
 <#include "/template/foot.ftl"/>