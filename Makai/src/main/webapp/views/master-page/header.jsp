<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Makai" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="animal/customer/register.do"><spring:message code="master.page.customer.animal" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasAnyRole('CUSTOMER,PROFESSIONAL')">
			<li><a class="fNiv"><spring:message	code="master.page.travel" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="travel/create.do"><spring:message code="master.page.travel.create" /></a></li>
					<li><a href="travel/list.do"><spring:message code="master.page.travel.list" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.register" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/register.do"><spring:message code="master.page.customer.register" /></a></li>
					<li><a href="professional/register.do"><spring:message code="master.page.professional.register" /></a></li>
					<li><a href="trainer/register.do"><spring:message code="master.page.trainer.register" /></a></li>					
					<li><a href="animalShelter/register.do"><spring:message code="master.page.animalShelter.register" /> </a></li>
				</ul>
			</li>
			
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="notification/actor/list.do"><spring:message code="master.page.notification.list" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/display.do"><spring:message code="master.page.profile.display" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="javascript:setParam('language', 'en');">en</a> | <a href="javascript:setParam('language', 'es');">es</a>
</div>

<script> 
    function setParam(name, value) {
        var l = window.location;

        /* build params */
        var params = {};        
        var x = /(?:\??)([^=&?]+)=?([^&?]*)/g;        
        var s = l.search;
        for(var r = x.exec(s); r; r = x.exec(s))
        {
            r[1] = decodeURIComponent(r[1]);
            if (!r[2]) r[2] = '%%';
            params[r[1]] = r[2];
        }

        /* set param */
        params[name] = encodeURIComponent(value);

        /* build search */
        var search = [];
        for(var i in params)
        {
            var p = encodeURIComponent(i);
            var v = params[i];
            if (v != '%%') p += '=' + v;
            search.push(p);
        }
        search = search.join('&');

        /* execute search */
        l.search = search;
    }
</script>

