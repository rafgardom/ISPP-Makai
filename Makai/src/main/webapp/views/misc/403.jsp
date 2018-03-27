<%--
 * 403.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<img src="images/security.png" style="margin-left:39%"/>
<h3 style="margin-left:39%; font-family: verdana">You should not be here.</h3>
<h3 style="margin-left:39%; font-family: verdana">You don't have access.</h3> 
<p style="margin-left:46%; font-size: 18px;font-family: verdana"><a href="<spring:url value='/' />">Go Back</a><p>