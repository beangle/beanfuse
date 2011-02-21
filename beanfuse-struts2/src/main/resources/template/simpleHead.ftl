<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title>权限系统</title>
  <link href="${base}/static/css/default.css" rel="stylesheet" type="text/css">
 </head>
 <#import "/template/message.ftl" as msg>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/Common.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/common/ToolBar.js"></script>
 <link href="css/toolBar.css" rel="stylesheet" type="text/css">
 <#if Session['WW_TRANS_I18N_LOCALE']??>
 <#assign language= Session['WW_TRANS_I18N_LOCALE'].language>
 <#else>
 <#assign language="zh">
 </#if>
 <#macro i18nName(entity)><#if language?index_of("en")!=-1><#if entity.engName!?trim=="">${entity.name!}<#else>${entity.engName!}</#if><#else><#if entity.name!?trim!="">${entity.name!}<#else>${entity.engName!}</#if></#if></#macro>
 <#macro getMessage></#macro>
 <#macro text name><@msg.text name/></#macro>
 <#macro getBeanListNames(beanList)><#list beanList as bean>${bean.name}&nbsp;</#list></#macro>
