<%@ page session="false" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script>
var i = 0;
var txt = 'Actualmente no hay ninguna mascota para adoptar.';
var speed = 70;

function typeWriter() {
  if (i < txt.length) {
    document.getElementById("demo").innerHTML += txt.charAt(i);
    i++;
    setTimeout(typeWriter, speed);
  }
}
</script>

<style>
.container {
  position: relative;
  text-align: center;
  color: white;
}

.centered {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>


<petclinic:layout pageName="adoptions">
	<jsp:body>
   <h2> <security:authorize access="isAuthenticated()"> Hola, <security:authentication
					property="principal.username" />! 
</security:authorize>
		</h2>
 <c:choose>
       <c:when
			test="${numOfPets eq 0}">	
<html>
<body onload="typeWriter()">
	<div class="container">
			<img id="gif" src="/resources/images/crazyCat.gif" width="1000px">
			 <div class="centered"><h2 id="demo"></h2></div>
</div>
</body>
</html>
 </c:when>
                    <c:otherwise>
                    <c:if test="${numOfPets eq 1}">
       					 <h1>Hay ${numOfPets} mascota para adoptar </h1>
       					 </c:if>
       					 <c:if test="${numOfPets gt 1}">
       					 <h1>Hay ${numOfPets} mascotas para adoptar </h1>
       					 </c:if>
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Fecha de nacimiento</th>
            <th style="width: 120px">Tipo</th>
            <th style="width: 120px">Dueño</th>
            <th style="width: 120px">Alta</th>
            <th style="width: 120px">Acción</th>
        </tr>
        </thead>
        <tbody> 
        <c:forEach items="${petsCollection}" var="adoption">
            <tr>    
                <td>
                    <c:out value="${adoption.pet.name}" />
                </td>
                <td>
                ${adoption.pet.birthDate.format( DateTimeFormatter.ofPattern("dd/MM/yyyy"))}
                </td>
                <td>
                ${adoption.pet.type} 
                </td>
                 <td>
                ${adoption.owner.user.username} 
                </td>
                <td>
                ${adoption.request_date.format( DateTimeFormatter.ofPattern("dd/MM/yyyy"))} 
                </td>
                 <td>
                  <c:choose>
                    <c:when
									test="${adoption.owner.user.username eq currentUserName}">
                   			No puedes adoptar tu mascota
     
                     <br />
                    </c:when>
                    <c:when
                    test="${adoption.owner.user.username eq currentUserName}">
                   			No puedes adoptar tu mascota
                     <br />
                    </c:when>
                    <c:otherwise>
                    <spring:url
										value="/adoption/{adoptionId}/manageAdoptionRequest" var="adoptionUrl">
                        <spring:param name="adoptionId" value="${adoption.id}" />
                    </spring:url>
                    <a  href="${fn:escapeXml(adoptionUrl)}"> Gestionar adopción </a> 
       					<br /> 
    				</c:otherwise>
    				</c:choose>
                   </td>
        </c:forEach>
        </tbody>
    </table>
       					<br />      
    				</c:otherwise>
    				</c:choose>
    </jsp:body>
</petclinic:layout>