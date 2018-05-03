<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authentication var="principalUserAccount" property="principal" />

<div class="card shadow">
	<div class="card-body text-center">
		<p><spring:message code="banner.sum.price" />: <jstl:out value="${bannersTotalBenefit }" />&euro;<p>
		<p><spring:message code="banner.avg.views" />: <jstl:out value="${bannersAvgBenefit }" />&euro;<p>
		<p><spring:message code="banner.monthly.earnings" />: <jstl:out value="${bannersMonthlyBenefit }" />&euro;<p>
	</div>
</div>

<div class="card-deck">
	<div class="card shadow mt-3 py-3">
		<h3 class="card-title"><spring:message code="banner.more.views" /></h3>
			<div class="table-responsive">
			<display:table name="bannerMoreViews" id="row" pagesize="2" requestURI="${requestURI}" class="displaytag">
				
				<acme:column code="banner.price" property="price" sortable="true" format="{0,number, 0.00}&euro;"/>
				
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
				
				<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
				<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
				
				<display:column>
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalImage${row.id }">
			   			<img class="icon-button" src="images/picture.png"/>
					</button>
					<!-- The Modal -->
					  <div class="modal fade" id="modalImage${row.id}">
					    <div class="modal-dialog modal-dialog-centered no-width">
					      <div class="modal-content">
					        <div class="modal-body">
					         	<img src="${row.stringImage}" alt="<spring:message code='profile.no.picture'  />" class="img-responsive">
					        </div>
					        
					      </div>
					    </div>
					  </div>
				</display:column>
			</display:table>
			</div>
		</div>
		
	
		<div class="card shadow mt-3 py-3">
		<h3 class="card-title"><spring:message code="banner.less.views" /></h3>
			<div class="table-responsive">
			<display:table name="bannerLessViews" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
				
				<acme:column code="banner.price" property="price" sortable="true" format="{0,number, 0.00}&euro;"/>
				
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
				
				<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
				<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
				
				<display:column>
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalImage${row.id }">
			   			<img class="icon-button" src="images/picture.png"/>
					</button>
					<!-- The Modal -->
					  <div class="modal fade" id="modalImage${row.id}">
					    <div class="modal-dialog modal-dialog-centered no-width">
					      <div class="modal-content">
					        <div class="modal-body">
					         	<img src="${row.stringImage}" alt="<spring:message code='profile.no.picture'  />" class="img-responsive">
					        </div>
					        
					      </div>
					    </div>
					  </div>
				</display:column>
			</display:table>
			</div>
		</div>
		
</div>	

<div class="card-deck">
	<div class="card shadow mt-3 py-3">
		<h3 class="card-title"><spring:message code="banner.more.clicks" /></h3>
			<div class="table-responsive">
			<display:table name="bannerMoreClicks" id="row" pagesize="2" requestURI="${requestURI}" class="displaytag">
				
				<acme:column code="banner.price" property="price" sortable="true" format="{0,number, 0.00}&euro;"/>
				
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
				
				<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
				<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
				
				<display:column>
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalImage${row.id }">
			   			<img class="icon-button" src="images/picture.png"/>
					</button>
					<!-- The Modal -->
					  <div class="modal fade" id="modalImage${row.id}">
					    <div class="modal-dialog modal-dialog-centered no-width">
					      <div class="modal-content">
					        <div class="modal-body">
					         	<img src="${row.stringImage}" alt="<spring:message code='profile.no.picture'  />" class="img-responsive">
					        </div>
					        
					      </div>
					    </div>
					  </div>
				</display:column>
			</display:table>
			</div>
		</div>
		
	
		<div class="card shadow mt-3 py-3">
		<h3 class="card-title"><spring:message code="banner.less.clicks" /></h3>
			<div class="table-responsive">
			<display:table name="bannerLessClicks" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
				
				<acme:column code="banner.price" property="price" sortable="true" format="{0,number, 0.00}&euro;"/>
				
				<display:column>
					<a href="profile/displayProfile.do?actorId=${row.actor.id}">${row.actor.name}</a>
				</display:column>
				
				<acme:column code="banner.currentViews" property="currentViews" sortable="true" />
				<acme:column code="banner.clicksNumber" property="clicksNumber" sortable="true" />
				
				<display:column>
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalImage${row.id }">
			   			<img class="icon-button" src="images/picture.png"/>
					</button>
					<!-- The Modal -->
					  <div class="modal fade" id="modalImage${row.id}">
					    <div class="modal-dialog modal-dialog-centered no-width">
					      <div class="modal-content">
					        <div class="modal-body">
					         	<img src="${row.stringImage}" alt="<spring:message code='profile.no.picture'  />" class="img-responsive">
					        </div>
					        
					      </div>
					    </div>
					  </div>
				</display:column>
			</display:table>
			</div>
		</div>
		
</div>	
		




