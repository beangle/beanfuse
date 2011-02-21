<#include "/template/simpleHead.ftl"/>
<body>
  <p>
  你还没有登录系统，请首先登录.
  </p>
  <script>
     window.open("index.action");
     self.window.top.close();
  </script>
</body>
<#include "/template/foot.ftl"/>
  