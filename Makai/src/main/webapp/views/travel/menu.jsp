<%--
 * action-1.jsp
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
	<div class="col-lg-4 mt-4">
	<div class="card bg-primary shadow text-light py-5">
		<div class="row align-items-center py-lg-5">
			<div class="offset-lg-2 col-lg-3 py-lg-5">
				<div class="center-div py-lg-4">
					<img src="images/travel-pet.png" alt="Travels"  height="128" width="128" />
				</div>
			</div>
			<div class="col-lg-7">
				<h1><spring:message code="master.page.travel" /></h1>	
			</div>
		</div>
	</div>
	</div>
	<div class="col-lg-8">
		<div class="row">
			<div class="col-md-6">
				<div class="card bg-primary mt-4 shadow">
					<div class="center-div">
						<img src="images/destination.png" alt="Makai" class="mt-2" height="128" width="128" />
					</div>
					<div class="card-body"> <acme:link  href="travel/list.do" code="master.page.travel.list" type=" btn-outline-light" /></div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card bg-primary mt-4 shadow">
					<div class="center-div">
						<img src="images/car2.png" alt="Vehicles" class="mt-2" height="128" width="128" />
					</div>
					<div class="card-body"> <acme:link  href="vehicle/list.do" code="master.page.vehicle.list" type=" btn-outline-light" /></div>				
				</div>
			</div>
			<div class="col-md-6">
				<div class="card bg-primary mt-4 shadow">
					<div class="center-div">
						<img src="images/tourist.png" alt="My current travels" class="mt-2" height="128" width="128" />
					</div>				
					<div class="card-body"> <acme:link  href="travel/myList.do" code="master.page.travel.myList" type=" btn-outline-light" /></div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card bg-primary mt-4 shadow">
					<div class="center-div">
						<img src="images/pictures.png" alt="My past travels" class="mt-2" height="128" width="128" />
					</div>	
					<div class="card-body"> <acme:link  href="travel/myPastList.do" code="master.page.travel.myPastList" type=" btn-outline-light" /></div>			
				</div>
			</div>
		</div>
	</div>

</div>