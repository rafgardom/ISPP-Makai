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

<form:form action="${requestURI}" modelAttribute="banner" enctype="multipart/form-data">
	<form:hidden path="id"/>
	<form:hidden path="price"/>

	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-5">
			<acme:input type="number" code="banner.totalViews" path="totalViews" mandatory="true" id="totalViews" />
			
			<p>
				<spring:message code="banner.price" />:
				<font id="divPrice">0</font> &euro;
			</p>
			<spring:message code="image.formats" var="formats" />
			<spring:message code="banner.picture" var="picture" />
			<jstl:out value="${picture}"/>
			<form:input type="file" path="bannerImage" id="bannerImage" name="bannerImage" mandatory="true" class="form:input-large" enctype="multipart/form-data" code="banner.picture"></form:input>
			<jstl:out value="${formats}"/>
			
		</div>
		<div class="col-md-6 offset-md-1">
			<br>
			<acme:textbox code="banner.enterprise" path="enterprise" mandatory="true" />
			<acme:input image="at" code="banner.email" path="email" mandatory="true" />
		</div>
	</div>
	<br><br>
	<acme:submit code="banner.create" name="save" />
	<acme:cancel code="banner.cancel" url="" />
	
</form:form>

<script type="text/javascript">
	
	
	$("#totalViews").on("input", function() {
		var totalViews = $("#totalViews").val();
		$('#divPrice').text(0.01*totalViews);
	});
</script>