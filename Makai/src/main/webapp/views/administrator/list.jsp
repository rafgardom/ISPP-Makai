<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authentication var="principalUserAccount" property="principal" />



<spring:message code="administrator.actor.customers" var="customersHeader" />
<spring:message code="administrator.actor.trainers" var="trainersHeader" />
<spring:message code="administrator.actor.animalShelters" var="animalSheltersHeader" />
<spring:message code="administrator.actor.advertisings" var="advertisingsHeader" />

<div class="col-12">
<div class="my-3">
	<button onclick="tableCustomer()" class="btn btn-lg btn-primary mx-3 disabled" id="button1">${customersHeader}</button>
	<button onclick="tableTrainers()" class="btn btn-lg btn-primary mx-3" id="button2">${trainersHeader}</button>
	<button onclick="tableAnimalshelters()" class="btn btn-lg btn-primary mx-3" id="button3">${animalSheltersHeader}</button>
	<button onclick="tableAdvertisings()" class="btn btn-lg btn-primary mx-3" id="button4">${advertisingsHeader}</button>
</div>
</div>


<div id='tableCustomers'>
<h2 class="text-center"><jstl:out value="${customersHeader}"/></h2>
<div class="table-responsive">
<display:table name="customers" id="row" requestURI="${requestURI }" pagesize="10" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				<br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				<br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			<br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>  
	
	<acme:column code="administrator.actor.rating" property="avgRating"/>
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalban-${row.id }">
						<img class="icon-button" src="images/ban.png" height="25px"/>
						<spring:message code="administrator.actor.ban" />
					</button>
					  <div class="modal fade" id="modalban-${row.id}">
					    <div class="modal-dialog modal-dialog-centered modal-sm">
					      <div class="modal-content">
					
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title"><spring:message code="administrator.actor.ban"/></h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        
					        <!-- Modal body -->
					        <div class="modal-body">
					         	<spring:message code="cuestion.ban"/>
					        </div>
					        <!-- Modal footer -->
					        <div class="modal-footer">
					          <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location='administrator/ban.do?actorId=${row.id}'"><spring:message code="confirm"/></button>
					          <button type="button" class="btn btn-dark" data-dismiss="modal"><spring:message code="cancel"/></button>
					        </div>
					        
					      </div>
					    </div>
					  </div>
			</jstl:when>
			<jstl:otherwise>
				<form:form action="administrator/unban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" name="unban" code="administrator.actor.unban" />
				</form:form>
			</jstl:otherwise>
		</jstl:choose>
	</display:column> 
</display:table>
</div>

</div>

<div id='tableTrainers' class="d-none">
<h2 class="text-center"><jstl:out value="${trainersHeader}"/></h2>
<div class="table-responsive">
<display:table name="trainers" id="row" requestURI="${requestURI }" pagesize="10" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				<br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				<br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			<br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>  
	
	<acme:column code="administrator.actor.rating" property="avgRating"/>
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalban-${row.id }">
						<img class="icon-button" src="images/ban.png" height="25px"/>
						<spring:message code="administrator.actor.ban" />
					</button>
					  <div class="modal fade" id="modalban-${row.id}">
					    <div class="modal-dialog modal-dialog-centered modal-sm">
					      <div class="modal-content">
					
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title"><spring:message code="administrator.actor.ban"/></h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        
					        <!-- Modal body -->
					        <div class="modal-body">
					         	<spring:message code="cuestion.ban"/>
					        </div>
					        <!-- Modal footer -->
					        <div class="modal-footer">
					          <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location='administrator/ban.do?actorId=${row.id}'"><spring:message code="confirm"/></button>
					          <button type="button" class="btn btn-dark" data-dismiss="modal"><spring:message code="cancel"/></button>
					        </div>
					        
					      </div>
					    </div>
					  </div>
			</jstl:when>
			<jstl:otherwise>
				<form:form action="administrator/unban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" name="unban" code="administrator.actor.unban" />
				</form:form>
			</jstl:otherwise>
		</jstl:choose>
	</display:column> 
</display:table>
</div>


</div>

<div id='tableAnimalshelters' class="d-none">
<h2 class="text-center"><jstl:out value="${animalSheltersHeader}"/></h2>
<div class="table-responsive">
<display:table name="animalShelters" id="row" requestURI="${requestURI }" pagesize="10" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				<br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				<br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			<br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>  
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
					<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalban-${row.id }">
						<img class="icon-button" src="images/ban.png" height="25px"/>
						<spring:message code="administrator.actor.ban" />
					</button>
					  <div class="modal fade" id="modalban-${row.id}">
					    <div class="modal-dialog modal-dialog-centered modal-sm">
					      <div class="modal-content">
					
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title"><spring:message code="administrator.actor.ban"/></h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        
					        <!-- Modal body -->
					        <div class="modal-body">
					         	<spring:message code="cuestion.ban"/>
					        </div>
					        <!-- Modal footer -->
					        <div class="modal-footer">
					          <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location='administrator/ban.do?actorId=${row.id}'"><spring:message code="confirm"/></button>
					          <button type="button" class="btn btn-dark" data-dismiss="modal"><spring:message code="cancel"/></button>
					        </div>
					        
					      </div>
					    </div>
					  </div>
			</jstl:when>
			<jstl:otherwise>
				<form:form action="administrator/unban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" name="unban" code="administrator.actor.unban" />
				</form:form>
			</jstl:otherwise>
		</jstl:choose>
	</display:column> 
</display:table>
</div>

</div>


<div id='tableAdvertisings' class="d-none">
<h2 class="text-center"><jstl:out value="${advertisingsHeader}"/></h2>
<div class="table-responsive">
<display:table name="advertisings" id="row" requestURI="${requestURI }" pagesize="10" class="displaytag">
	
	<acme:column code="administrator.actor.username" property="userAccount.username" sortable="true"/>
	<acme:column code="administrator.actor.name" property="name"/>	
	<acme:column code="administrator.actor.email" property="email"/>
	<acme:column code="administrator.actor.phone" property="phone"/>
		
	<spring:message code="administrator.actor.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		
				<spring:message code="administrator.actor.country" />: <jstl:out value="${row.coordinates.country }"></jstl:out>
			
			
			<jstl:if test="${not empty row.coordinates.state }">
				<br>
					<spring:message code="administrator.actor.state" />: <jstl:out value="${row.coordinates.state }"></jstl:out>
				
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.province }">
				<br>
					<spring:message code="administrator.actor.provice" />: <jstl:out value="${row.coordinates.province }"></jstl:out>
				
			</jstl:if>
			
			<br>
				<spring:message code="administrator.actor.city" />: <jstl:out value="${row.coordinates.city }"></jstl:out> 
			
	</display:column>   
	
	<spring:message code="administrator.actor.profile" var="profileHeader" />
	<display:column title="${profileHeader}" sortable="false">
		<acme:link image="eye" href="profile/displayProfile.do?actorId=${row.id}"/>
	</display:column> 
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.userAccount.enabled }">
				<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalban-${row.id }">
						<img class="icon-button" src="images/ban.png" height="25px"/>
						<spring:message code="administrator.actor.ban" />
					</button>
					  <div class="modal fade" id="modalban-${row.id}">
					    <div class="modal-dialog modal-dialog-centered modal-sm">
					      <div class="modal-content">
					
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title"><spring:message code="administrator.actor.ban"/></h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        
					        <!-- Modal body -->
					        <div class="modal-body">
					         	<spring:message code="cuestion.ban"/>
					        </div>
					        <!-- Modal footer -->
					        <div class="modal-footer">
					          <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location='administrator/ban.do?actorId=${row.id}'"><spring:message code="confirm"/></button>
					          <button type="button" class="btn btn-dark" data-dismiss="modal"><spring:message code="cancel"/></button>
					        </div>
					        
					      </div>
					    </div>
					  </div>
			</jstl:when>
			<jstl:otherwise>
				<form:form action="administrator/unban.do?actorId=${row.id}" modelAttribute="actor">
					<acme:submit image="ban" name="unban" code="administrator.actor.unban" />
				</form:form>
			</jstl:otherwise>
		</jstl:choose>
	</display:column> 
</display:table>
</div>

</div>

<script>
function tableCustomer(){
	document.getElementById("button1").classList.add('disabled');
	document.getElementById("button2").classList.remove('disabled');
	document.getElementById("button3").classList.remove('disabled');
	document.getElementById("button4").classList.remove('disabled');
	document.getElementById("tableTrainers").classList.add('d-none');
	document.getElementById("tableAdvertisings").classList.add('d-none');
	document.getElementById("tableAnimalshelters").classList.add('d-none');
	document.getElementById("tableCustomers").classList.remove('d-none');
}
function tableTrainers(){
	document.getElementById("button1").classList.remove('disabled');
	document.getElementById("button2").classList.add('disabled');
	document.getElementById("button3").classList.remove('disabled');
	document.getElementById("button4").classList.remove('disabled');
	document.getElementById("tableTrainers").classList.remove('d-none');
	document.getElementById("tableAdvertisings").classList.add('d-none');
	document.getElementById("tableAnimalshelters").classList.add('d-none');
	document.getElementById("tableCustomers").classList.add('d-none');
}
function tableAdvertisings(){
	document.getElementById("button1").classList.remove('disabled');
	document.getElementById("button2").classList.remove('disabled');
	document.getElementById("button3").classList.remove('disabled');
	document.getElementById("button4").classList.add('disabled');
	document.getElementById("tableTrainers").classList.add('d-none');
	document.getElementById("tableAdvertisings").classList.remove('d-none');
	document.getElementById("tableAnimalshelters").classList.add('d-none');
	document.getElementById("tableCustomers").classList.add('d-none');
}
function tableAnimalshelters(){
	document.getElementById("button1").classList.remove('disabled');
	document.getElementById("button2").classList.remove('disabled');
	document.getElementById("button3").classList.add('disabled');
	document.getElementById("button4").classList.remove('disabled');
	document.getElementById("tableTrainers").classList.add('d-none');
	document.getElementById("tableAdvertisings").classList.add('d-none');
	document.getElementById("tableAnimalshelters").classList.remove('d-none');
	document.getElementById("tableCustomers").classList.add('d-none');
}


</script>