<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:if test="${successMessage != null}">
	<div class="alert alert-success"><spring:message code="${successMessage}" /></div>
</jstl:if>

<form:form action="${requestURI}" modelAttribute="bannerForm" enctype="multipart/form-data">
	<form:hidden path="id"/>
	<form:hidden path="price"/>
	<form:hidden path="currentViews"/>

	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-6">
			<acme:input type="number" code="banner.totalViews" path="totalViews" mandatory="true" id="totalViews" />
			
			<p>
				<spring:message code="banner.price" />:
				<font id="divPrice">0</font> &euro;
			</p>
			
		</div>
		<div class="col-md-5 offset-md-1">
			<acme:textbox code="banner.zone" path="zone" mandatory="true" />
			<br>
			<acme:inputImage path="bannerImage"/>
		</div>
	</div>
	<br><br>
	<acme:submit code="banner.create" name="save" />
	<acme:cancel code="banner.cancel" url="" />
	
</form:form>

<script type="text/javascript">
	
	
	$("#totalViews").on("input", function() {
		var totalViews = $("#totalViews").val();
		$('#divPrice').text(Math.round((0.01*totalViews * 100 )) / 100);
	});
</script>