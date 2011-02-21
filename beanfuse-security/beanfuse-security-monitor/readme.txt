	<bean id="profileProvider"
		class="org.beanfuse.security.concurrent.category.impl.SimpleCategoryProfileProvider"
		autowire="byName">
		<property name="profiles">
			<list>
				<bean
					class="org.beanfuse.security.concurrent.category.CategoryProfile">
					<property name="category" value="1"/>
					<property name="max" value="1000"/>
				</bean>
				<bean
					class="org.beanfuse.security.concurrent.category.CategoryProfile">
					<property name="category" value="2"/>
					<property name="max" value="500"/>
				</bean>
				<bean
					class="org.beanfuse.security.concurrent.category.CategoryProfile">
					<property name="category" value="3"/>
					<property name="max" value="500"/>
				</bean>
			</list>
		</property>
	</bean>
	
	<bean id="securityMonitor"
		class="org.beanfuse.security.monitor.DefaultSecurityMonitor">
		<property name="rememberMeService" ref="rememberMeService"/>
		<property name="sessionController" ref="sessionController"/>
		<property name="authorityDecisionService" ref="authorityDecisionService"/>
		<property name="providers">
			<list>
				<ref bean="ssoUserProvider"></ref>
				<ref bean="ldapUserProvider"></ref>
				<ref bean="daoUserProvider"></ref>
			</list>
		</property>
		<property name="ignoreResources">
			<set>
				<value>index.do</value>
				<value>randomCodeImage.do</value>
				<value>login.do</value>
				<value>error.do</value>
				<value>resetPassword.do</value>
				<value>dataTemplate.do</value>
				<value>teachOutlineSearch.do</value>
			</set>
		</property>
	</bean>