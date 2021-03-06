<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>

<form:form action="${requestURI}" modelAttribute="animalForm" enctype="multipart/form-data">
	
	<form:hidden path="id" />

	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
<div class="row" >
	<div class="col-md-5">
			<acme:textbox code="animal.name" path="name" mandatory="true"/>
			<acme:input code="animal.chipNumber" path="chipNumber" mandatory="false" image="chip" />
			<acme:input id="datepicker" image="calendar" code="animal.birthday" path="birthday" mandatory="true" placeholder="dd/MM/yyyy"/>
			
			<acme:radioSex/> 
			
				
	<%-- <acme:selectNotEntity items="${sexs}" lang="${lang}" code="animal.sex" path="sex" mandatory="true"/>  --%>
	</div>
	
	<div class="offset-md-1 col-md-6">
			<jstl:if test="${lang == 'es'}">
				<acme:select id="specie" firstLabel="animal.choose.specie" items="${species }" itemLabel="typeSpa" code="animal.specie" path="specie" onchange="getBreeds('nameSpa');" mandatory="true" />
			</jstl:if>
			<jstl:if test="${lang == 'en'}">
				<acme:select id="specie" firstLabel="animal.choose.specie" items="${species }" itemLabel="typeEng" code="animal.specie" path="specie" onchange="getBreeds('nameEng');" mandatory="true" />
			</jstl:if>
			<small>*<spring:message code="animal.specie.advice"/></small>
			<br><br>
			<jstl:set var="json" value="${jsonBreeds }" />
			<jstl:if test="${lang == 'es'}">
				<acme:select firstLabel="animal.choose.breed" size="9" id="breed" items="${breeds }" itemLabel="nameSpa" code="animal.breed" path="breeds" mandatory="true"/>
			</jstl:if>
			<jstl:if test="${lang == 'en'}">
				<acme:select firstLabel="animal.choose.breed" size="9" id="breed" items="${breeds }" itemLabel="nameEng" code="animal.breed" path="breeds" mandatory="true"/>
			</jstl:if>

	</div>
	<div class="col-md-6">
		<br>
		<acme:inputImage path="animalImage" mandatory="true"/>
	</div>
</div>

	
	<br>
	<acme:submit name="save" code="animal.save" />
	
	<acme:cancel url="animal/menu.do" code="animal.cancel" />
		
</form:form>

<!-- Datepicker -->
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
<jstl:if test="${lang =='es'}">
	<script>
		$.datepicker.setDefaults($.datepicker.regional['es']);
	</script>
</jstl:if>

<script>
$( function() {
    $( "#datepicker" ).datepicker({ 
    	dateFormat: 'dd/mm/yy', 
    	maxDate: 0,
    	firstDay: 1
    	});
  } );
</script>

<script type="text/javascript">
	var specieVal = document.getElementById("specie").selectedOptions[0].value;
	var selectBreeds = document.getElementById("breed");
	
	var breeds = '<jstl:out value="${json }" />';
	breeds = breeds.replace(/&#034;/g, '"');
	var obj = JSON.parse(breeds);
	
	for (var i = 0; i < obj.length; i++){
    	var e = obj[i];
    	
        if(e["specie"]["id"] != specieVal){
			for (var j=0; j<selectBreeds.length; j++){
				if (selectBreeds.options[j].value == e["id"] ){
					selectBreeds.remove(j);
				}
			}
        }
    }

	function getBreeds(languague){
	    var specieVal = document.getElementById("specie").selectedOptions[0].value;
	    var selectBreeds = document.getElementById("breed");
	    
	    var breeds = '<jstl:out value="${json }" />';
	    breeds = breeds.replace(/&#034;/g, '"');
	    var obj = JSON.parse(breeds);
	    
	    if(obj.length != selectBreeds.length){
	    	html ='<option value=0 selected="selected" disabled="true"><spring:message code="animal.choose.breed"/></option>';
	    	for(var i = 0; i < obj.length; i++) {
	    		var e = obj[i];
	            html += "<option value=" + e["id"]  + ">" +e[languague] + "</option>";
	        }
	    	selectBreeds.innerHTML = html;
	    }
	    
	    for (var i = 0; i < obj.length; i++){
	    	var e = obj[i];
	    	
	        if(e["specie"]["id"] != specieVal){
				for (var j=0; j<selectBreeds.length; j++){
					if (selectBreeds.options[j].value == e["id"] ){
						selectBreeds.remove(j);
					}
				}
	        }
	    }
	    
	}
</script>


<script type="text/javascript">
	value1 = document.getElementById("customRadio2").value;
	valueForm = document.getElementById("valueForm").value;
	if (value1 === valueForm) {
		document.getElementById("customRadio2").checked = true;
	}else{
		document.getElementById("customRadio1").checked = true;
	}
</script>