<%@ page session="false" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="upstream" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.time.format.DateTimeFormatter"%>


<upstream:layout pageName="games">
	<jsp:body>
   <h2> <security:authorize access="isAuthenticated()"> Hello, <security:authentication
					property="principal.username" />! 
</security:authorize>
		</h2>

    <h1>Tienes ${numOfPets} mascotas registradas en Petclinic! </h1>
   	
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Fecha de nacimiento</th>
            <th style="width: 120px">Tipo</th>
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
       					 Pendiente de adopción
       					<br />      
    				</c:otherwise>
    				</c:choose>
                   </td>
                </td>
                
       
        </c:forEach>
        </tbody>
    </table>
      

	</div>
    </jsp:body>
</upstream:layout>

