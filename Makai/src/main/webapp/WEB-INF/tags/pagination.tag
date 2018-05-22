<%--
 * cancel.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<jstl:if test="${page==1}">
	<jstl:set value="disabled" var="disabled1"/>
</jstl:if>
<jstl:if test="${pageNumbers==page}">
	<jstl:set value="disabled" var="disabled2"/>
</jstl:if>

<jstl:if test="${pageNumbers>0}">
<div class="center-div mt-4">
  <ul class="pagination pagination-lg">
	  <jstl:forEach var = "i" begin="0" end="${pageNumbers-1}">
	    
	    <jstl:if test="${i==0}">
		    <li class="page-item ${disabled1}">
		      <a class="page-link" href="${requestURI}?page=1&nElem=${nElem}">&laquo;</a>
		    </li>
			<jstl:set value=" " var="disabled"/>
	    </jstl:if>
	    
	    <jstl:if test="${page == i+1}">
	    		<jstl:set value="active" var="active"/>
	    </jstl:if>
	    
	    <li class="page-item ${active}">
	      <a class="page-link" href="${requestURI}?page=${i+1}&nElem=${nElem}"><jstl:out value="${i+1}"/></a>
	    </li>
	    <jstl:set value=" " var="active"/>

		    <jstl:if test="${pageNumbers-1==i}">
			    <li class="page-item ${disabled2}">
			      <a class="page-link" href="${requestURI}?page=${i+1}&nElem=${nElem}">&raquo;</a>
			    </li>
		    <jstl:set value=" " var="disabled"/>
			</jstl:if>
	
	    </jstl:forEach>
	    
   
  </ul>
</div>
</jstl:if>