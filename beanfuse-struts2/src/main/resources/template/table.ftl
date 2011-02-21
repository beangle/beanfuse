 <#import "/template/message.ftl" as msg>
 <#macro sortTd id extra...>
   <td <#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list> id="${id}" class="tableHeaderSort"><#if extra['name']??><@msg.text name="${extra['name']}"/><#else>${extra['text']}</#if></td>
 </#macro>
 <#macro td extra...>
   <td <#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list>><#if extra['name']??><@msg.text name="${extra['name']}"/><#else>${extra['text']}</#if></td>
 </#macro>
 <#macro thead extra...>
   <tr align="center" class="darkColumn" <#if (extra?size!=0)><#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list></#if>><#nested></tr>
 </#macro>
 <#macro  tr class >
   <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)"onmouseout="swapOutTR(this)" onclick="onRowChange(event)"><#nested></tr>
 </#macro>
 <#macro selectAllTd id extra...>
   <td class="select" <#if (extra?size!=0)><#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list></#if>><input type="checkBox" id="${id}Box" class="box" onClick="toggleCheckBox(document.getElementsByName('${id}'),event);"></td>
 </#macro>
  <#macro selectTd id value extra...>
   <td class="select"><input class="box" name="${id}" value="${value}" <#if (extra?size!=0)><#list extra?keys as attr><#if attr != "type"> ${attr}="${extra[attr]}"</#if></#list></#if> type="${extra['type']!("checkbox")}"><#nested></td>
 </#macro>
 
 <#macro table extra...>
   <#if !(tableClass??)><#assign tableClass="listTable"/></#if>
   <table class=${tableClass} <#if (extra?size!=0)><#list extra?keys as attr>${attr}="${extra[attr]?html}" </#list></#if>>
   <#nested>
   <#if thisPageSize?? || extra['sortable']??>
	   <form  name="queryForm" action="" method="post">
	      <#list Parameters?keys as key>
	      <input type="hidden" name="${key}" value="${Parameters[key]}" />
	      </#list>
	   </form>
	   <script>
	   <#if extra['sortable']??>
	    <#assign sortTableId="${extra['id']}">
	    <#assign headIndex=extra['headIndex']!("0")>
	    <#if sortTableId??>
	    initSortTable('${sortTableId}',${headIndex!(0)},"${Parameters['orderBy']!('null')}");
	    <#else>
	    initSortTable('sortTable',${headIndex!(0)},"${Parameters['orderBy']!('null')}");
	    </#if>
		orderBy=function(what){
		    goToPage(queryForm,1,${pageSize!("null")},what);
		}
	   </#if>
	   //addInput(queryForm,'params',"");
	   var queryStr=getInputParams(queryForm,null,false);
	   refreshQuery=function (){queryForm.submit();}
	   var orderByStr='${Parameters['orderBy']!('')}';
	   <#if thisPageSize??>
	    function pageGoWithSize(pageNo,pageSize){
		  goToPage(queryForm,pageNo,pageSize,orderByStr);
	    }
	   </#if>
	   queryForm.action=self.location.protocol+'//'+self.location.host+self.location.pathname;
	   </script>
  </#if>
  </table>
</#macro>

 <#macro tbody datas extra...>
    <tbody>
    <#list datas as data>
	  <#if data_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if data_index%2==0 ><#assign class="brightStyle" ></#if>
	   <@tr class="${class}"><#nested data,data_index></@tr>
    </#list>
    <#if thisPageSize??>
        <#if extra['simplePageBar']!false>
            <#include "/template/simplePageBar.ftl"/>
        <#else>
            <#include "/template/pageBar.ftl"/>
        </#if>
    </#if>
  </tbody>
</#macro>