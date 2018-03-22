<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="requests" id="row" requestURI="${requestURI}" class="displaytag">

	<acme:column code="request.description" property="description" />
	<acme:column code="request.tags" property="tags" />
	<acme:column code="request.category" property="category.name" />
	<acme:column code="request.animal" property="animal.name" />
	
	<display:column>
		<div class="btn-group">
			<acme:link href="request/actor/display.do?requestId=${row.id}" code="request.display"/>
			<security:authorize access="hasRole('CUSTOMER')">
				<%-- <acme:link href="request/customer/edit.do?requestId=${row.id}" code="request.edit"/> --%>
				<acme:link href="offer/customer/list.do?requestId=${row.id}" code="request.list.offer"/>
				
				<jstl:set var="show" value="${true}"/>
				<jstl:if test="${!offersPendingReceipts.isEmpty()}">
					<jstl:forEach var="offer" items="${offersPendingReceipts}">
						<jstl:if test="${offer.request.id == row.id}">
							<jstl:set var="show" value="${false}"/>
						</jstl:if>
					</jstl:forEach>
				</jstl:if>
				<jstl:if test="${show == true}">
					<%-- <acme:link href="request/customer/edit.do?requestId=${row.id}" code="request.edit"/> --%>
					<acme:cancel url="request/customer/delete.do?requestId=${row.id}" code="request.delete"/>
				</jstl:if>
			</security:authorize>
			<security:authorize access="hasRole('TRAINER')">
				<acme:link href="offer/trainer/create.do?requestId=${row.id}" code="offer.create" type="success"/>
			</security:authorize>
		</div>
	</display:column>

</display:table>
<security:authorize access="hasRole('CUSTOMER')">
	<acme:link href="request/customer/create.do" code="request.create"/>
</security:authorize>