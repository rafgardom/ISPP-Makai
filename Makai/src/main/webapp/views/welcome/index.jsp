<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="card" style="background-color: rgba(248, 249, 250, 0.58);">
	<div class="row align-items-center py-3">
		<div class="offset-md-1 col-md-3 col-12">
			<div class="center-div">
				<img src="images/logo.png" alt="Makai" style="height: 220px;" />
			</div>
		</div>
		<div class="offset-md-1 col-md-7 col-12">
			<h3 class="text-center"><spring:message code="welcome.greeting.prefix" /></h3>	
		</div>
	</div>
</div>

<br>
<div class="card-deck text-light text-center">
	<div class="card bg-secondary">
		<div class="center-div py-2">
		<img alt="dog-training" src="images/trainer.png" height="128" width="128">
		</div>
		<h4 class="card-title">Entrenadores profesionales</h4>
		<a href="#!" class="card-link text-warning" ></a>
		<h5 class="card-footer card-text">Mas de 146 entrenadores, para el adiestramiento de todo tipo de mascotas, según la tarea requerida.</h5>
	</div>
	<div class="card bg-primary">
		<div class="center-div py-2">
		<img alt="travel-pet" src="images/travel-pet.png" height="128" width="128">
		</div>
		<h4 class="card-title">¡Recorre el mundo con tu mascota!</h4>
		<h5 class="card-footer card-text">Terceras personas y empresas ofrecen su propio transpote, ya no te preocuparas más de tu mascota en los viajes.</h5>
	
	</div>
	<div class="card bg-warning">
		<div class="center-div py-2">
			<img alt="dog-training" src="images/medical-report (1).png" height="128" width="128">
		</div>
		<h4 class="card-title">Asociaciones y mascotas</h4>
		<h5 class="card-footer">Trabajamos con asociasiones de animales que aportan animales necesitados en los adiestramientos.</h5>
	
	</div>
</div>




