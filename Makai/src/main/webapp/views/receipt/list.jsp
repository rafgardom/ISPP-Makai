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
		<acme:link href="request/list.do?requestId=${row.request.id}"
			code="receipt.request" />
	</display:column>
	<display:column>
		<acme:link href="trainer/list.do?trainerId=${row.trainer.id}"
			code="receipt.trainer" />
	</display:column>
	
	<display:column>
	Este boton es el que ejecuta el metodo de pago de la api
		<acme:link href="receipt/customer/payment.do?receiptAmount=${row.amount}"
			code="receipt.pay" />
			<br/>
			<br/>
		Este boton funciona bien pero no esta linkado a la api
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="hosted_button_id" value="LP6W3QDS5AQB8">
<input type="image" src="https://www.paypalobjects.com/en_US/ES/i/btn/btn_paynowCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
</form>

		 
			
	</display:column>

</display:table>
