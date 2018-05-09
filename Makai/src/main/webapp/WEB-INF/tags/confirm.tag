<%--
 * cancel.tag
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
 
 <%@ attribute name="id" required="true" %>
 
<%@ attribute name="code" required="false" %>
<%@ attribute name="href" required="true" %>
<%@ attribute name="cuestioncode" required="false" %>
<%@ attribute name="image" required="true" %>

<jstl:if test="${cuestioncode == null}">        
	<jstl:set var="cuestioncode" value="confirm.question" />
</jstl:if>

<%-- Definition --%>

<button type="button" class="btn btn-danger rounded-right btn-lg" data-toggle="modal" data-target="#myModal${id }">
   	<img class="icon-button" src="images/${image}.png"/>
   	<jstl:if test="${code != null}">
		<spring:message code="${code}" />
	</jstl:if>
</button>

  <!-- The Modal -->
  <div class="modal fade" id="myModal${id }">
    <div class="modal-dialog modal-dialog-centered modal-sm">
      <div class="modal-content">

        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title"><spring:message code="milestone.complete"/></h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
         	<spring:message code="${cuestioncode}"/>
        </div>
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location='${href}'"><spring:message code="confirm"/></button>
          <button type="button" class="btn btn-dark" data-dismiss="modal"><spring:message code="cancel"/></button>
        </div>
        
      </div>
    </div>
  </div>

<%-- Notes --%>


