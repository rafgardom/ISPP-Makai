<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />


<div class="w-100" style="background: #F8F9FA;">
<div class="sticky-top"><hr /></div>
<div class="row mx-5 ">

	<div class="col-md-5">
		<a href="javascript:setParam('language', 'en');"><img src="images/uk.png"/>&nbsp;English</a> | <a
		href="javascript:setParam('language', 'es');"><img src="images/spain.png"/>&nbsp;Español</a>
		<h6 class="mt-3"><b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> team Makai&trade;</b></h6>
	</div>
	<div class="col-md-2 center-div my-2">
		<a href="https://twitter.com/Makai_official" target="_blank" class="mx-2"><img src="images/twitter.png" onmouseover="this.src='images/twitter-black.png'" onmouseout="this.src='images/twitter.png'"/></a>
		<a href="https://www.facebook.com/OficialMakai/" target="_blank"><img src="images/facebook.png" onmouseover="this.src='images/facebook-black.png'" onmouseout="this.src='images/facebook.png'"/></a>
	</div>
	<div class="col-md-5 text-md-right">
		<a href="misc/privacyPolicy.do?sc=true"><spring:message code="master.page.privacyPolicy" /></a>&nbsp;&nbsp;&#124;
		<a href="misc/cookiesPolicy.do"><spring:message code="master.page.cookiesPolicy" /></a>&nbsp;&nbsp;&#124;
		<a href="misc/faq.do"><img src="images/info.png"/>&nbsp;<spring:message code="master.page.faq" /></a>
		<br/>
		<div class="tooltip mt-3">
			<img src="images/email.png"  onmouseover="this.src='images/email (1).png';" onmouseout="this.src='images/email.png';" > 
			<p class="my-0"><span class="tooltiptext">Ispp.makai.1718@gmail.com</span></p>
		</div>
		<small class="mt-3"><i><spring:message code="master.page.time" /><fmt:formatDate value="${date}" pattern="dd/MM HH:mm:ss" /></i></small>
	</div>
</div>

</div>


<div id="barraaceptacion">
    <div class="inner row">
			<div class="col-10">
			<spring:message code="master.page.cookies" />
	    		<a href="misc/cookiesPolicy.do" class="info"><spring:message code="master.page.cookies.moreInfo" /></a>
			</div>
			<div class="col-2 text-sm-right">
			 	<button type="button" class="btn btn-warning btn-lg ok" onclick="PonerCookie();"><b><spring:message code="master.page.cookies.ok" /></b></button>
			</div>
	</div>
</div>
 
<script>
function getCookie(c_name){
    var c_value = document.cookie;
    var c_start = c_value.indexOf(" " + c_name + "=");
    if (c_start == -1){
        c_start = c_value.indexOf(c_name + "=");
    }
    if (c_start == -1){
        c_value = null;
    }else{
        c_start = c_value.indexOf("=", c_start) + 1;
        var c_end = c_value.indexOf(";", c_start);
        if (c_end == -1){
            c_end = c_value.length;
        }
        c_value = unescape(c_value.substring(c_start,c_end));
    }
    return c_value;
}
 
function setCookie(c_name,value,exdays){
    var exdate=new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
    document.cookie=c_name + "=" + c_value;
}
 
if(getCookie('tiendaaviso')!="1"){
    document.getElementById("barraaceptacion").style.display="block";
}
function PonerCookie(){
    setCookie('tiendaaviso','1',365);
    document.getElementById("barraaceptacion").style.display="none";
}
</script>
<script>
	function setParam(name, value) {
		var l = window.location;

		/* build params */
		var params = {};
		var x = /(?:\??)([^=&?]+)=?([^&?]*)/g;
		var s = l.search;
		for ( var r = x.exec(s); r; r = x.exec(s)) {
			r[1] = decodeURIComponent(r[1]);
			if (!r[2])
				r[2] = '%%';
			params[r[1]] = r[2];
		}

		/* set param */
		params[name] = encodeURIComponent(value);

		/* build search */
		var search = [];
		for ( var i in params) {
			var p = encodeURIComponent(i);
			var v = params[i];
			if (v != '%%')
				p += '=' + v;
			search.push(p);
		}
		search = search.join('&');

		/* execute search */
		l.search = search;
	}
	
	function setParamComprobar(name, value) {
		var url = window.location.href;
		var urlAdicional = window.location.origin + window.location.pathname;
		
		if(url.indexOf("create")>-1 ||  url.indexOf("edit")>-1 || url.indexOf("register")>-1){
			if(value=='en'){
				location.href =window.location.origin + "/Makai?language=en";
			}
			if(value=='es'){
				location.href =window.location.origin + "/Makai?language=es";
			}
		}
		
		if(url.indexOf('create')==-1 &&  url.indexOf('edit')==-1 && url.indexOf('register')==-1){
			setParam(name, value);
		}
		
	}
	
</script>

<!-- <script>
function startTime() {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('txt').innerHTML = h + ":" + m + ":" + s;
    var t = setTimeout(startTime, 500);
}
function checkTime(i) {
    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}
</script> -->