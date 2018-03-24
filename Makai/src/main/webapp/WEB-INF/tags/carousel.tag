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


	<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
	  <div class="carousel-inner" role="listbox">
	    <div class="carousel-item active">
	      	<img class="d-block w-100" src="https://23k8dv4brsbv18vf5lh7gb21-wpengine.netdna-ssl.com/wp-content/uploads/2015/04/banner-web-development.png" data-src="holder.js/900x400?theme=social" alt="First slide">
	    </div>
	    <div class="carousel-item">
	      	<img class="d-block w-100" src="https://crearpaginawebpanama.com/wp-content/uploads/2016/07/banner.jpg" data-src="holder.js/900x400?theme=industrial" alt="Second slide">
	    </div>
		</div>
		<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="sr-only">Previous</span>
		</a>
		<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="sr-only">Next</span>
		</a>
	</div>

<%-- Notes --%>

