<%--
 * carousel.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%-- Definition --%>

<jstl:if test="${imagesBottom != null}">
	<jstl:if test="${!empty imagesBottom}">
	<small class="text-muted text-center" style="font-size: 60%;"><i>*<spring:message code="advertising"/></i></small>
	<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
	  <div class="carousel-inner" role="listbox">
	  	
	  	<jsp:useBean id="util" class="utilities.Utilities" scope="page" />
	  	<jstl:set value="true" var="first"/>
	   	<jstl:forEach var = "banner" items="${imagesBottom }">
		   	<jstl:if test="${first}">
		   		<jstl:set value="active" var="active"/>
		   		<jstl:set value="false" var="first"/>
		   	</jstl:if>
		   	
         	<div class="carousel-item ${active}">
	      		<a href="${banner.url}" target="_blank"><img class="d-block w-100" src="${util.showImage(banner.getPicture())}" data-src="holder.js/900x400?theme=industrial">
	   		</a>
	   		</div>
	   		<jstl:set value="" var="active"/>
      	</jstl:forEach>
	 
		</div>
<!-- 		<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="sr-only">Previous</span>
		</a>
		<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="sr-only">Next</span>
		</a> -->
	</div>
	</jstl:if>
</jstl:if>
<%-- Notes --%>

