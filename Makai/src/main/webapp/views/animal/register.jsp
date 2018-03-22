<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${RequestURI}" modelAttribute="animalForm" enctype="multipart/form-data">
	
	<form:hidden path="id" />

	<acme:textbox code="animal.name" path="name" mandatory="true"/>
	<acme:textbox code="animal.chipNumber" path="chipNumber" mandatory="false"/>
	<acme:input code="animal.age" path="age" type="number" min="0" mandatory="true" />
	
	<acme:selectNotEntity items="${sexs }" code="animal.sex" path="sex" mandatory="true"/>
	<br />
	<br />
	
	<acme:select id="specie" items="${species }" itemLabel="type" code="animal.specie" path="specie" onchange="getBreeds();" mandatory="true" />
	
	<br />
	<br />
	
	<jstl:set var="json" value="${jsonBreeds }" />
	<acme:select id="breed" items="${breeds }" itemLabel="breed" code="animal.breed" path="breeds" mandatory="true"/>
	<br />
	<br />
	

	<spring:message code="animal.picture" /><strong>(*)</strong>
	<form:input class="form-control-file" path="animalImage" type="file" />
	<spring:message code="image.formats" var="formats" /><jstl:out value="${formats}"/><br>
	<form:errors path="animalImage" cssClass="error" />
	<br />
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
	<br/>
	<br/>
	
	<acme:submit name="save" code="animal.save" />
	
	<acme:cancel url="./animal/list.do" code="animal.cancel" />
		
</form:form>

<script type="text/javascript">
	function getBreeds(){
	    var specieVal = document.getElementById("specie").selectedOptions[0].value;
	    var selectBreeds = document.getElementById("breed");
	    
	    var breeds = '<jstl:out value="${json }" />';
	    breeds = breeds.replace(/&#034;/g, '"');
	    var obj = JSON.parse(breeds);
	    
	    if(obj.length != selectBreeds.length){
	    	html ='<option value=0 selected="selected" disabled="true">----</option>';
	    	for(var i = 0; i < obj.length; i++) {
	    		var e = obj[i];
	            html += "<option value=" + e["id"]  + ">" +e["breed"] + "</option>"
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
