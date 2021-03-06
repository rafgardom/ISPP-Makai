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
	<form:hidden path="validated"/>
	<form:hidden path="currentViews"/>

	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-6">
			<acme:input type="number" code="banner.totalViews" path="totalViews" min="1" max="5000000" mandatory="true" id="totalViews" />
			
			<p>
				<spring:message code="banner.price" />:
				<font id="divPrice">0</font> &euro;
			</p>
			<acme:input type="url" code="banner.url" path="url" mandatory="true" placeholder="http://www.makai.es/"/>
			
		</div>
		<div class="col-md-5 offset-md-1">
<!-- 			<div class="form-group"> -->
<%-- 				<form:label path="zone"> --%>
<%-- 					<spring:message code="banner.zone" /> --%>
<!-- 					<img src="images/asterisk.png"	width="16"/>  -->
<%-- 				</form:label>	 --%>
<%-- 				<form:select path="zone" class="form-control" > --%>
<%-- 					<form:option value="0" label="----" selected="selected" disabled="disabled"/> --%>
<%-- 					<form:option value="izquierda" ><spring:message code="banner.zone.izquierda" /></form:option> --%>
<%-- 					<form:option value="derecha" ><spring:message code="banner.zone.derecha" /></form:option> --%>
<%-- 					<form:option value="abajo" ><spring:message code="banner.zone.abajo" /></form:option> --%>
<%-- 				</form:select> --%>
<%-- 				<form:errors path="zone" cssClass="alert alert-danger form-control" /> --%>
<!-- 			</div> -->
<!-- 			<br> -->
			<acme:inputImage path="bannerImage" mandatory="true"/>
			<small class="form-text text-muted">
				<spring:message code="banner.image.width"/>
				<br/>
				<spring:message code="banner.image.height"/>
			</small>
		</div>
		
	</div>
	
	<jstl:if test="${bannerForm.stringImage != null}">
		<p><spring:message code="banner.miniature"/></p>
		<div class="col-lg-10  col-12">
			<div class="row">
					<img src="${bannerForm.stringImage}" alt="<spring:message code='animal.no.picture' />" width="100%" height="100%">
			</div>
		</div>
	</jstl:if>
	<small class="form-text text-muted">
		*&nbsp;<spring:message code="banner.advice.edit"/>
	</small>
	<br>
	<jstl:if test="${bannerForm.id == 0 }">
		<acme:submit code="banner.create" name="save" />
	</jstl:if>
	<jstl:if test="${bannerForm.id != 0 }">
		<acme:submit code="banner.edit" name="save" />
	</jstl:if>
	<acme:cancel code="banner.cancel" url="" />
	
</form:form>

<script type="text/javascript">

	var price = '<jstl:out value="${bannerPrice }" />';
	
	if($("#totalViews").val()>0){
		var totalViews = $("#totalViews").val();
		$('#divPrice').text(Math.round((price*totalViews * 100 )) / 100);
	}
	
	$("#totalViews").on("input", function() {
		var totalViews = $("#totalViews").val();
		if(totalViews.length>7){
			$("#totalViews").val((totalViews-totalViews%10)/10);
		}else{
			$('#divPrice').text(Math.round((price*totalViews * 100 )) / 100);	
		}
	});
</script>