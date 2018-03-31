<%--
 * textarea.tag
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

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="readonly" required="false" %>
<%@ attribute name="rows" required="false" %>
<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="placeholder" required="false" %>
<%@ attribute name="maxlength" required="false" %>
<%@ attribute name="maxCharacters" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>
<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Definition --%>

<div class="form-group">
	<form:label path="${path}">
		<spring:message code="${code}" />
		<jstl:if test="${mandatory == true}">
			<img src="images/asterisk.png"	width="16"/>
		</jstl:if>
	</form:label>
	<form:textarea path="${path}" placeholder="${placeholder }" maxlength="${maxlength }" readonly="${readonly}" class="form-control" rows="${rows}" id="text" name="text" />
	<jstl:if test="${maxCharacters != null}">
		<span class="pull-right label label-default" id="count_message"></span>
	</jstl:if>
	<form:errors path="${path}" cssClass="alert alert-danger form-control" />
</div>

<script type="text/javascript">
	
	var text_max = '<jstl:out value="${maxCharacters }" />';;
	var chars = $('#text').val().length;
	
	$('#count_message').html(chars + ' / ' + text_max );
	
	if(chars>text_max){
		$('#count_message').addClass("overlimit");
		$('#text').addClass("is-invalid");
	}else{
		$('#count_message').removeClass("overlimit");
		$('#text').removeClass("is-invalid");
	}
	
	$('#text').keyup(function() {
		var text_length = $('#text').val().length;
		 
		$('#count_message').html(text_length + ' / ' + text_max);
		
		if(text_length>text_max){
			$('#count_message').addClass("text-danger");
			$('#text').addClass("is-invalid");
		}else{
			$('#count_message').removeClass("text-danger");
			$('#text').removeClass("is-invalid");
		}
	});
</script>
