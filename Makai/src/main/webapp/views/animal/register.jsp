<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="animal/edit.do" modelAttribute="animal" enctype="multipart/form-data">
	
	<form:hidden path="id" />

	<acme:input code="animal.name" path="name" />
	<acme:input code="animal.chipNumber" path="chipNumber"/>
	<acme:input code="animal.age" path="age" type="number" min="0" />
	
	<spring:message code="animal.sex" />
	<form:select path="sex">
		<form:option value="${null }" label="----" />    
		<jstl:forEach items="${sexs }" var="sex">
			<form:option value="${sex}" label="${sex}" />
		</jstl:forEach>
	</form:select>
	<br />
	
	<form:input path="animalImage" type="file" />
	<spring:message code="image.formats" var="formats" /><jstl:out value="${formats}"/>
	<br />
	
	<spring:message code="animal.specie" />
	<select name="specie" id="specie" onchange="getBreeds();" >
		<option value='0' selected="selected" disabled="true" >----</option>  
		<jstl:forEach var="specie" items="${species }">
			<option value='${specie.id }'>${specie.type }</option>
		</jstl:forEach>
	</select>
	<br />
	
	<jstl:set var="json" value="${jsonBreeds }" />
	<spring:message code="animal.breed" />
	<form:select name="breed" id="breed" path="breeds" >
		<form:option value='0' selected="selected" disabled="true" >----</form:option>  
		<jstl:forEach var="breed" items="${breeds }">
			<form:option value='${breed.id }'>${breed.breed }</form:option>
		</jstl:forEach>
	</form:select>
	<br />
	
	<acme:submit name="save" code="animal.save" />
	
	<acme:cancel url="/" code="animal.cancel" />
		
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
