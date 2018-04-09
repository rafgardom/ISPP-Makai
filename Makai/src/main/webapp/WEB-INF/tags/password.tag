<%--
 * password.tag
 *
 * Copyright (C) 2016 Universidad de Sevilla
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
 
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="code" required="true" rtexprvalue="true" %>

<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="placeholder" required="false" %>
<%@ attribute name="image" required="false" %>
<%@ attribute name="infoButton" required="false" %>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Definition --%>

<spring:bind path="${path}">
	<div class="form-group ${status.error? 'has-error':''}">
		<form:label path="${path}">
				<spring:message code="${code}" />
				<jstl:if test="${mandatory == true}">
					<img src="images/asterisk.png"	width="16"/>
				</jstl:if>
		</form:label>
		
		<jstl:if test="${infoButton != null}">
			<button type="button" class="btn btn-info btn-sm float-right" data-toggle="modal" data-target="#myModal"><b>?</b></button>
		</jstl:if>
		
	<jstl:if test="${image != null}">
		<div class="input-group"><!-- NO BORRAR -->
			<div class="input-group-prepend">
				<span class="input-group-text btn-light"><img src="images/${image}.png"/></span>
			</div>
	</jstl:if>
		<form:password path="${path}" class="form-control" placeholder="${placeholder}" />
<jstl:if test="${image == null}">
		<form:errors path="${path}" cssClass="alert alert-danger form-control" />
</jstl:if>
	</div>
<jstl:if test="${image != null}">
	<form:errors path="${path}" cssClass="alert alert-danger form-control" />
	</div><!-- NO BORRAR -->
</jstl:if>
</spring:bind>

<!-- MODAL Button info -->
<jstl:if test="${infoButton != null}">
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-centered modal">
      <div class="modal-content">
        <div class="modal-header">	
        	<ul>
		        <li>
		        	<h6><b><spring:message code="customer.password.error1"/></b></h6>
		        </li>
		        <li> 
		            <h6><b><spring:message code="customer.password.error2"/></b></h6>
		        </li>
	        </ul>
        	<button type="button" class="close" data-dismiss="modal">&times;</button>
         </div>   	
      </div>
    </div>
  </div>
</jstl:if>