<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="table-responsive">
<display:table name="notifications" id="row" pagesize="10" requestURI="${requestURI}" class="displaytag">

	<acme:emptyMsg/>
	
	<display:column>
	<jstl:if test="${!row.isRead}">
		<img src="images/new.png" height="45rem"/>
	</jstl:if>	
	</display:column>
	
	<acme:column code="notification.moment" property="moment" format="{0,date,HH:mm:ss dd/MM/yyyy}" sortable="true"/>
	
	<spring:message code="notification.reason" var="reasonHeader" />
	<display:column title="${reasonHeader}" class="text-center" sortable="true">
		<jstl:if test="${!row.reason.substring(0,1).equals('#')}">
			<jstl:out value="${row.reason}" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,2).equals('##')}">
			<jstl:out value="${row.reason.substring(1)}" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#TD0')}">
			<spring:message code="notification.info.reason.travelDelete" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#TR0')}">
			<spring:message code="notification.info.reason.travelRegister" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#RC0')}">
			<spring:message code="notification.info.reason.requestCreate" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#RN0')}">
			<spring:message code="notification.info.reason.ratingNegative" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#OC0')}">
			<spring:message code="notification.info.reason.offerCreate" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#OA0')}">
			<spring:message code="notification.info.reason.offerAccept" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#OP0')}">
			<spring:message code="notification.info.reason.offerPet" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#BC0')}">
			<spring:message code="notification.info.reason.bannerCreate" />
		</jstl:if>
		<jstl:if test="${row.reason.substring(0,4).equals('#BA0')}">
			<spring:message code="notification.info.reason.bannerAccept" />
		</jstl:if>
	</display:column>
	<display:column>

		<div class="btn-group">	
			<acme:link image="eye" href="notification/actor/display.do?notificationId=${row.id}"/>
			<acme:link image="trash" href="notification/actor/delete.do?notificationId=${row.id}" type="danger"/>
		</div>

	</display:column>
	
</display:table>
</div>

<div class="center-div">
	<jstl:if test="${!empty notifications}">
		<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalRemoveNotification">
		   	<img class="icon-button" src="images/trash.png"/>
				<spring:message code="notification.deleteall" />
		</button>
		
		<%-- <acme:delete href="notification/actor/deleteAll.do?notificationId=${row.id}" id="${row.id}" code="notification.deleteall" cuestioncode="notification.deleteall.cuestion"/>
 --%>	</jstl:if>
	
<security:authorize access="hasRole('ADMIN')">
	<acme:link href="notification/admin/create.do" image="megaphone" code="notification.create" type="success mx-3"/>
</security:authorize>

</div>



  <!-- The Modal -->
  <div class="modal fade" id="modalRemoveNotification">
    <div class="modal-dialog modal-dialog-centered modal-sm">
      <div class="modal-content">

        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title"><spring:message code="delete"/></h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
         	<spring:message code="notification.deleteall.cuestion"/>
        </div>
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location='notification/actor/deleteAll.do'"><spring:message code="delete"/></button>
          <button type="button" class="btn btn-dark" data-dismiss="modal"><spring:message code="cancel"/></button>
        </div>
        
      </div>
    </div>
  </div>