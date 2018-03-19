<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Makai"
		style="height: 300px; padding-top: 1%; padding-bottom: 1%; padding-left: 33.3333%;" />
</div>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<a class="navbar-brand" href="#">Home</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor01">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarColor01">
		<ul class="navbar-nav mr-auto">

			<security:authorize access="hasAnyRole('CUSTOMER')">
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.customer" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="animal/customer/register.do"><spring:message
								code="master.page.customer.animal" /></a>
					</div></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.request" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="request/customer/create.do"><spring:message
								code="master.page.request.create" /></a> <a class="dropdown-item"
							href="request/customer/list.do"><spring:message
								code="master.page.request.list" /></a>
					</div></li>


			</security:authorize>

			<security:authorize access="hasAnyRole('CUSTOMER,PROFESSIONAL')">

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.travel" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="travel/create.do"><spring:message
								code="master.page.travel.create" /></a> <a class="dropdown-item"
							href="travel/list.do"><spring:message
								code="master.page.travel.list" /></a>
					</div></li>
			</security:authorize>

			<security:authorize access="hasRole('TRAINER')">
				<li class="nav-item"><a class="nav-link"
					href="offer/trainer/list.do"><spring:message
							code="master.page.offer" /> </a></li>
			</security:authorize>

			<security:authorize access="hasAnyRole('CUSTOMER,PROFESSIONAL')">

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.vehicle" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="vehicle/register.do"><spring:message
								code="master.page.vehicle.register" /></a> <a class="dropdown-item"
							href="vehicle/list.do"><spring:message
								code="master.page.vehicle.list" /></a>
					</div></li>
			</security:authorize>

			<security:authorize access="isAnonymous()">
				<li class="nav-item"><a class="nav-link"
					href="security/login.do"> <spring:message
							code="master.page.login" />
				</a></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.register" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="customer/register.do"><spring:message
								code="master.page.customer.register" /></a> <a
							class="dropdown-item" href="professional/register.do"><spring:message
								code="master.page.professional.register" /></a> <a
							class="dropdown-item" href="trainer/register.do"><spring:message
								code="master.page.trainer.register" /></a> <a class="dropdown-item"
							href="animalShelter/register.do"><spring:message
								code="master.page.animalShelter.register" /></a>
					</div></li>
			</security:authorize>

			<security:authorize access="isAuthenticated()">

				<li class="nav-item"><a class="nav-link"
					href="notification/actor/list.do"><spring:message
							code="master.page.notification.list" /> </a></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.profile" /> (<security:authentication
							property="principal.username" />)</a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="profile/display.do"><spring:message
								code="master.page.profile.display" /></a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="j_spring_security_logout"><spring:message
								code="master.page.logout" /></a>
					</div></li>
			</security:authorize>

		</ul>
		<form class="form-inline my-2 my-lg-0">
			<input class="form-control mr-sm-2" type="text" placeholder="Search">
			<button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
		</form>
	</div>
</nav>



<%-- <div>
	<ul class="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv "><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message
								code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message
								code="master.page.administrator.action.2" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="animal/customer/register.do"><spring:message
								code="master.page.customer.animal" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.request" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/customer/create.do"><spring:message
								code="master.page.request.create" /></a></li>
					<li><a href="request/customer/list.do"><spring:message
								code="master.page.request.list" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasAnyRole('CUSTOMER,PROFESSIONAL')">
			<li><a class="fNiv"><spring:message
						code="master.page.travel" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="travel/create.do"><spring:message
								code="master.page.travel.create" /></a></li>
					<li><a href="travel/list.do"><spring:message
								code="master.page.travel.list" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('TRAINER')">
			<li><a class="fNiv" href="offer/trainer/list.do"><spring:message
						code="master.page.offer" /></a></li>
		</security:authorize>

		<security:authorize access="hasAnyRole('CUSTOMER,PROFESSIONAL')">
			<li><a class="fNiv"><spring:message
						code="master.page.vehicle" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="vehicle/register.do"><spring:message
								code="master.page.vehicle.register" /></a></li>
					<li><a href="vehicle/list.do"><spring:message
								code="master.page.vehicle.list" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.register" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/register.do"><spring:message
								code="master.page.customer.register" /></a></li>
					<li><a href="professional/register.do"><spring:message
								code="master.page.professional.register" /></a></li>
					<li><a href="trainer/register.do"><spring:message
								code="master.page.trainer.register" /></a></li>
					<li><a href="animalShelter/register.do"><spring:message
								code="master.page.animalShelter.register" /> </a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="notification/actor/list.do"><spring:message
						code="master.page.notification.list" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/display.do"><spring:message
								code="master.page.profile.display" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div> --%>

<div>
	<a href="javascript:setParam('language', 'en');">en</a> | <a
		href="javascript:setParam('language', 'es');">es</a>
</div>

<script>
	function setParam(name, value) {
		var l = window.location;

		/* build params */
		var params = {};
		var x = /(?:\??)([^=&?]+)=?([^&?]*)/g;
		var s = l.search;
		for ( var r = x.exec(s); r; r = x.exec(s)) {
			r[1] = decodeURIComponent(r[1]);
			if (!r[2])
				r[2] = '%%';
			params[r[1]] = r[2];
		}

		/* set param */
		params[name] = encodeURIComponent(value);

		/* build search */
		var search = [];
		for ( var i in params) {
			var p = encodeURIComponent(i);
			var v = params[i];
			if (v != '%%')
				p += '=' + v;
			search.push(p);
		}
		search = search.join('&');

		/* execute search */
		l.search = search;
	}
</script>

