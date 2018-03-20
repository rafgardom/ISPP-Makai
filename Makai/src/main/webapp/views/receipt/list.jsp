<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="receipts" id="row" requestURI="${RequestURI}" class="displaytag">

	<acme:column code="receipt.amount" property="amount" />
	<jstl:if test="${row.moment != null}">
		<acme:column code="receipt.moment" property="moment" />
	</jstl:if>
	<display:column>
		<acme:cancel url="request/list.do?requestId=${row.request.id}"
			code="receipt.request" />
	</display:column>
	<display:column>
		<acme:cancel url="trainer/list.do?trainerId=${row.trainer.id}"
			code="receipt.trainer" />
	</display:column>
	
	<display:column>
		<acme:cancel url="receipt/pay.do?receiptId=${row.id}"
			code="receipt.pay" />
	</display:column>

</display:table>
