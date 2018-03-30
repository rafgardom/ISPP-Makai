<%--
 * textbox.tag
 *
 * Copyright (C) 2014 Universidad de Sevilla
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
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="href" required="true" %>

<%@ attribute name="linkCode" required="false" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="varConditions" required="false" %>
<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="target" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${varConditions == null}">
	<jstl:set var="varConditions" value="false" />
</jstl:if>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Definition --%>

<spring:bind path="${path}">
		<div class="form-check">
		<jstl:if test="${mandatory == true}">
			<img src="images/asterisk.png"	width="16"/> 
		</jstl:if>
		<form:label class="form-check-label" path="${path}">
			<spring:message code="${code}" />
			<a href="${href}" target="${target }" data-load-url="${href}" data-toggle="modal" data-target="#myModal"><spring:message code="${linkCode}" /></a>
		</form:label>
		<form:checkbox path="${path}"/>
		<form:errors path="${path}"  cssClass="alert alert-danger" />
		</div>
		
</spring:bind>

<!-- The Modal -->
<div class="modal fade" id="myModal">
	<div class="modal-dialog modal-dialog-centered modal-lg">
		<div class="modal-content">
		
			<!-- Modal Header -->
			<div class="modal-header">
				<h3 class="modal-title"></h3>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
		
			<!-- Modal body -->
			<div class="modal-body">
			</div>
			
			<!-- Modal footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="ok"/></button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$('#myModal').on('show.bs.modal', function (e) {
	    var loadurl = $(e.relatedTarget).data('load-url');
	    $modalTitle = $(this).find('.modal-title');
	    $modalTitle.load(loadurl+" .jumbotron h1", function(){
	            $modalTitle.find('h1').replaceWith(function(){
	                return $(this).text();
	            });
	        });
	    $(this).find('.modal-body').load(loadurl+" #privacyPolicy");
	});
</script>

