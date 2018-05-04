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
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>


<nav class="navbar navbar-expand-md navbar-dark bg-primary shadow">
	<a class="navbar-brand" href="">
	<img src="images/dog-house.png" height="45px" class="mx-1"/><span class="d-lg-none d-md-none"><spring:message code="master.page.home"/></span>
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor01">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarColor01">
		<ul class="navbar-nav mr-auto">

			<security:authorize access="hasAnyRole('CUSTOMER')">

				<li class="nav-item">
					<a class="nav-link" href="request/customer/menu.do">
						<img src="images/request.png" class="img-menu" height="32" width="32"/>
						<spring:message code="master.page.request" />
					</a>
				</li>
				
				<%-- <li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" data-toggle="dropdown">
						<img src="images/request.png" class="img-menu"/><spring:message code="master.page.requests" /></a>
					<div class="dropdown-menu" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="request/customer/create.do">
				 		<spring:message code="master.page.request.create" /></a> 
						<a class="dropdown-item" href="request/customer/myList.do">
							<spring:message code="master.page.request.list" /></a>
					</div></li> --%>

			</security:authorize>

			<security:authorize access="hasAnyRole('CUSTOMER,PROFESSIONAL')">

				

				<li class="nav-item"> 
					<a class="nav-link" href="travel/menu.do">
					<img src="images/travel-pet.png" class="img-menu" height="32" width="32"/>
					<spring:message code="master.page.travel" />
					</a>

				</li>
			</security:authorize>
			
			<security:authorize access="hasAnyRole('CUSTOMER,ANIMALSHELTER')">
			<li class="nav-item"> 
				<a class="nav-link" href="animal/menu.do">
						<img src="images/animals.png" class="img-menu" height="32" width="32"/>
						<spring:message code="master.page.animal" />
				</a>
<%-- 				<li class="nav-item dropdown"> 
					<a class="nav-link dropdown-toggle" data-toggle="dropdown">
						<img src="images/animals.png" class="img-menu"/><spring:message	code="master.page.animal" /></a>
					<div class="dropdown-menu" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="animal/register.do">
							<spring:message code="master.page.animal.register" /></a> 
						<a class="dropdown-item" href="animal/list.do">
							<spring:message code="master.page.animal.list" /></a>
						<security:authorize access="hasRole('ANIMALSHELTER')">
						<a class="dropdown-item" href="animal/listAdopted.do">
							<spring:message code="master.page.animal.listAdopted" /></a>
						<a class="dropdown-item" href="animal/listNotAdopted.do">
							<spring:message code="master.page.animal.listNotAdopted" /></a>
						</security:authorize>
					</div>
				</li> --%>
			</security:authorize>

			<security:authorize access="hasRole('TRAINER')">
				<li class="nav-item"><a class="nav-link"
					href="request/trainer/list.do">
					<img src="images/request.png" class="img-menu"/><spring:message code="master.page.requests" /> </a></li>
				
				
				<li class="nav-item"><a class="nav-link"
					href="offer/trainer/list.do">
					<img src="images/deal1.png" class="img-menu"/><spring:message code="master.page.offer" /> </a></li>
	
			<%-- 	<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" data-toggle="dropdown">
								<img src="images/deal1.png" class="img-menu"/><spring:message code="master.page.offer" />
						</a>
						<div class="dropdown-menu" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
							<a class="dropdown-item" href="offer/trainer/list.do">
					 			<spring:message code="master.page.offer" /></a> 
							<a class="dropdown-item" href="offer/trainer/listPassed.do">
								<spring:message code="master.page.offer.passed" /></a>
						</div>
					</li> --%>
						
					
				<%-- <li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown">
					<spring:message code="master.page.trainer.training" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="training/trainer/list.do"><spring:message
								code="master.page.training.list" /></a>
						<a class="dropdown-item" href="training/trainer/create.do"><spring:message
								code="master.page.training.create" /></a>
					</div></li> --%>
			</security:authorize>

			<security:authorize access="hasRole('ADMIN')">
				<li class="nav-item"><a class="nav-link"
					href="administrator/listActors.do">
					<img src="images/users.png" class="img-menu"/>
					<spring:message code="master.page.administrator.actors" /> </a></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.administrator.species" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="specie/admin/list.do"><spring:message
								code="master.page.administrator.listSpecies" /></a>
						<a class="dropdown-item" href="specie/admin/create.do"><spring:message
								code="master.page.administrator.createSpecie" /></a>
					</div>
				</li>
				
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><spring:message
							code="master.page.administrator.breeds" /></a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="breed/admin/list.do"><spring:message
								code="master.page.administrator.listBreed" /></a>
						<a class="dropdown-item" href="breed/admin/create.do"><spring:message
								code="master.page.administrator.createBreed" /></a>
					</div>
				</li>
			</security:authorize>

			<security:authorize access="isAnonymous()">
				<li class="nav-item"><a class="nav-link"
					href="security/login.do"> <spring:message
							code="master.page.sign.in" />
				</a></li>

			</security:authorize>
			
			<security:authorize access="hasAnyRole('ADVERTISING')">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" data-toggle="dropdown">
							<img src="images/image.png" class="img-menu"/><spring:message code="master.page.banners" />
					</a>
					<div class="dropdown-menu" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="banner/actor/list.do">
							<spring:message code="master.page.banner.list" /></a>
						<security:authorize access="hasRole('ADVERTISING')">
							<a class="dropdown-item" href="banner/actor/create.do">
				 				<spring:message code="master.page.banner.create" /></a> 
						</security:authorize>
					</div>
				</li>
			</security:authorize>
			<security:authorize access="hasAnyRole('ADMIN')">
				<li class="nav-item">
					<a class="nav-link" href="banner/actor/list.do">
							<img src="images/image.png" class="img-menu"/><spring:message code="master.page.banners" />
					</a>

				</li>
			</security:authorize>
		</ul>
		
		<security:authorize access="isAuthenticated()">
			
			<ul class="navbar-nav float-lg-right">
					<li class="nav-item">
				<a class="nav-link"
					href="notification/actor/list.do">
							<img src="images/bell.png" class="img-menu"/>
							<span class="badge badge-default badge-pill bg-secondary pill-menu"><jstl:if test="${numberNoti > 0}" >${numberNoti}</jstl:if></span> 
							<span><spring:message code="master.page.notification.list" /></span>
				</a></li>
				
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" data-toggle="dropdown"><img src="images/user (1).png" class="img-menu"/>  <security:authentication
							property="principal.username" var="username" /> ${fn:toUpperCase(username)} </a>
					<div class="dropdown-menu" x-placement="bottom-start"
						style="position: absolute; transform: translate3d(0px, 40px, 0px); top: 0px; left: 0px; will-change: transform;">
						<a class="dropdown-item" href="profile/display.do"><%-- <spring:message
								code="master.page.profile.display" /> --%><spring:message
							code="master.page.profile" /></a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="j_spring_security_logout"><spring:message
								code="master.page.logout" /></a>
					</div></li></ul>
			</security:authorize>
	<!--<form class="form-inline">
			<input class="form-control mr-sm-2" type="text" placeholder="Search">
			<button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
		</form> -->
	</div>
</nav>
