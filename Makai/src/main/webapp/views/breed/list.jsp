<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="table-responsive">
<display:table name="breeds" id="row" pagesize="10" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="breed.name" property="name" sortable="true"/>
	
	<display:column>

		<div class="btn-group">	
			<acme:link image="edit" href="breed/admin/edit.do?breedId=${row.id}" type="warning"/>
			<acme:link image="trash" href="breed/admin/delete.do?breedId=${row.id}" type="danger"/>
		</div>

	</display:column>
	
</display:table>
</div>