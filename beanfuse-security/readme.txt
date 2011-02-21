1.1.0升级到1.2.0
1、资源中增加了可见范围(public,provided,private)
2、将角色置换成用户组，用户组支持上下级关系
3、删除了用户权限，将所有的权限放在用户组上
4、限制权限增加ParamGroup，将Restriction中的Pattern换成了ParamGroup
1.0.0升级到1.1.0
1、增加了xtqx_yhlbpz_t
   create table || sequence
2、删除系统中的categoryProfile配置
3、hibernate.hbm.xml增加<mapping resource="org/beanfuse/security/model/CategoryProfile.hbm.xml"/>
4、变更在线记录表
   update xtqx_zxjl_t set yhlbid=yhlx;
   alter table xtqx_zxjl_t drop column yhlx;
5、web.xml中无权限页面地址为：
   error.do?errorCode=security.error.notEnoughAuthority

6、国际化文件中增加security.error.notEnoughAuthority
7、增加dashboard.do和userDashboard.do的系统资源，并赋予管理员
8、spring的配置文件文件中加入/dashboard和/userDashboard说明
9、WEB-INF/增加cewolf.tld

