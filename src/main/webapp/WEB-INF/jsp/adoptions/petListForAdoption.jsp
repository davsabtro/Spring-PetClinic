<%@ page session="false" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.time.format.DateTimeFormatter"%>


<style>
        h1{text-align: center;}
        h2{text-align: center;}
    </style>
<petclinic:layout pageName="games">
	<jsp:body>
	<c:choose>
				<c:when test = "${empty myPetsCollection}">	
				<h1> <security:authorize access="isAuthenticated()"> Hola, <security:authentication
					property="principal.username" />! No tienes ninguna mascota registrada en Petclinic, <a href="/owners/${owner.id}/pets/new"> <span style = "color: #adff2f;">¡agrega una! </span></h3></a>
</security:authorize>
		</h1>					
				 </c:when>
	<c:otherwise>
   <h1> <security:authorize access="isAuthenticated()"> Hola, <security:authentication
					property="principal.username" />! En esta sección puedes dar tu mascota en adopción
</security:authorize>
		</h1>
 <c:if test="${numOfPets eq 1}">
       					 <h2>Tienes ${numOfPets} mascota registrada en Petclinic </h2>
       					 </c:if>			 
       					 <c:if test="${numOfPets gt 1}">
       					 <h3>Tienes ${numOfPets} mascotas registradas en Petclinic </h3>
       					 </c:if>
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Fecha de nacimiento</th>
            <th style="width: 120px">Tipo</th>
            <th style="width: 120px">Estado</th>
            <th style="width: 120px">Acción</th>    
        </tr>
        </thead>
        <tbody> 
        <c:forEach items="${petsCollection}" var="pet">
            <tr>    
                <td>
                    <c:out value="${pet.name}" />
                </td>
                <td>
                ${pet.birthDate.format( DateTimeFormatter.ofPattern("dd/MM/yyyy"))}
                </td>
                <td>
                ${pet.type} 
                </td>
                 <td>
                 <c:if test="${pet.isGivenForAdoption eq true}">
       					 Pendiente de adopción
       			 </c:if>
       			 <c:if test="${pet.isGivenForAdoption eq false}">
       					 Adopción no solicitada
       			 </c:if>
                 </td>
                 <td>
                  <c:choose>
                    <c:when
									test="${pet.isGivenForAdoption eq false}">
                   			<spring:url
										value="/adoption/{petId}/givePetInAdoption" var="petUrl">
                        <spring:param name="petId" value="${pet.id}" />
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(petUrl)}" role="button"> Dar en adopción </a> 
                     <br />
                    </c:when>
                    <c:otherwise>
                    <spring:url
										value="/adoption/{petId}/suitorsList" var="petSuitorsUrl">
                        <spring:param name="petId" value="${pet.id}" />
                    </spring:url>
       		
       					 <a href="${fn:escapeXml(petSuitorsUrl)}"> Ver solicitudes </a> 
       					<br />      
    				</c:otherwise>
    				</c:choose>
                   </td>
        </c:forEach>
        </tbody>
    </table>
	 </c:otherwise>
</c:choose>
    </jsp:body>
</petclinic:layout>