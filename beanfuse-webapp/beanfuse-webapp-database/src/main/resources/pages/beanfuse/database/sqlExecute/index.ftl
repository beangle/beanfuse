<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>自定义查询－SQL方案</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../css/main.css">
</head>

<body>
<div align="center">
<form name="form1" method="post" action="${base}/database/sql-execute!executeSql.action">
  <table class="emptyTable" cellpadding="0" cellspacing="0">
    <tr>
      <td><div align="left">SQL语句：</div>
            <textarea name="sql" cols="100" rows="5" id="sql"></textarea>
        </td>
    </tr>
    <tr>
      <td><div align="right"><input type="submit"  name="Submit" value="-提交-"></div>
        </td>
    </tr>
  </table>
</form>
</div>
</BODY>
</HTML>
