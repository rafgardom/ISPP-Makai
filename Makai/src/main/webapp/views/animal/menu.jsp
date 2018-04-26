<%--
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="row text-center">
<security:authorize access="hasRole('CUSTOMER')">
	<div class="col-lg-5 mt-4">
</security:authorize>
<security:authorize access="hasRole('ANIMALSHELTER')">
	<div class="col-lg-4 mt-4">
</security:authorize>
	<div class="card bg-warning shadow text-light py-5">
		<div class="row align-items-center py-lg-5">
			<div class="offset-lg-2 col-lg-3 py-lg-5">
				<div class="center-div py-lg-4">
					<img src="images/pet-xl.png" alt="animals"  height="128" width="128" />
				</div>
			</div>
			<div class="col-lg-7">
				<h1><spring:message code="master.page.animal" /></h1>	
			</div>
		</div>
	</div>
	</div>
	
	<security:authorize access="hasRole('ANIMALSHELTER')">
	<div class="col-lg-8">
		<div class="row">
			<div class="col-md-6">
				<div class="card bg-warning mt-4 shadow">
					<div class="center-div">
						<img src="images/add.png" alt="Add animal" class="mt-5" height="64" width="64" />
						<img src="images/dog.png" alt="Add animal" class="mt-2" height="128" width="128" />
					</div>
					<div class="card-body"> <acme:link  href="animal/register.do" code="master.page.animal.register" type=" btn-outline-light" /></div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card bg-warning mt-4 shadow">
					<div class="center-div">
						<img src="images/list.png" alt="My animals" class="mt-2" height="128" width="128" />
					</div>
					<div class="card-body"> <acme:link  href="animal/list.do" code="master.page.animal.list" type=" btn-outline-light" /></div>				
				</div>
			</div>
			<div class="col-md-6">
				<div class="card bg-warning mt-4 shadow">
					<div class="center-div">
						<img src="images/dog-house-xl.png" alt="My current travels" class="mt-2" height="128" width="128" />
					</div>				
					<div class="card-body"> <acme:link  href="animal/listAdopted.do" code="master.page.animal.listAdopted" type=" btn-outline-light" /></div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card bg-warning mt-4 shadow">
					<div class="center-div">
						<img src="images/kitty-cry.png" alt="My past travels" class="mt-2" height="128" width="128" />
					</div>	
					<div class="card-body"> <acme:link  href="animal/listNotAdopted.do" code="master.page.animal.listNotAdopted" type=" btn-outline-light" /></div>			
				</div>
			</div>
		</div>
	</div>
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
	<div class="col-lg-7">
		<div class="row">
			<div class="col-lg-12 col-sm-6">
				<div class="card bg-warning mt-4 shadow">
					<div class="center-div">
						<img src="images/add.png" alt="Add animal" class="mt-5" height="64" width="64" />
						<img src="images/dog.png" alt="Add animal" class="mt-2" height="128" width="128" />
					</div>
					<div class="card-body"> <acme:link  href="animal/register.do" code="master.page.animal.register" type=" btn-outline-light" /></div>
				</div>
			</div>
			<div class="col-lg-12 col-sm-6">
				<div class="card bg-warning mt-4 shadow">
					<div class="center-div">
						<img src="images/list.png" alt="My animals" class="mt-2" height="128" width="128" />
					</div>
					<div class="card-body"> <acme:link  href="animal/list.do" code="master.page.animal.list" type=" btn-outline-light" /></div>				
				</div>
			</div>
		</div>
	</div>
	</security:authorize>

</div>