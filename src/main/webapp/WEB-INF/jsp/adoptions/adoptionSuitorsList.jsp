<%@ page session="false" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<script>
var i = 0;
var txt = 'Actualmente no hay ninguna solicitud.';
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

<petclinic:layout pageName="suitors">
	<jsp:body>
   <h2> <security:authorize access="isAuthenticated()"> Hello, <security:authentication
					property="principal.username" />! 
</security:authorize>
		</h2>

<c:choose>
<c:when
			test="${numOfSuitors eq 0}">
			
			<html>
<body onload="typeWriter()">
	<div class="container">
			<img id="gif" src="/resources/images/crazyCat2.gif" width="800px">
			 <div class="centered"><h2 id="demo"></h2></div>
</div>
</body>
</html>
			
</c:when>
                    <c:otherwise>
                    
                     <c:if test="${numOfSuitors eq 1}">
       					 <h1>¡Tienes ${numOfSuitors} solicitud para tu mascota! </h1>
       					 </c:if>
       					 
       					 <c:if test="${numOfSuitors gt 1}">
       					 <h1>¡Tienes ${numOfSuitors} solicitudes para tu mascota! </h1>
       					 </c:if>
                    
                     
   	
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
             <th style="width: 150px;">Username</th>
            <th style="width: 200px;">Dirección</th>
            <th style="width: 120px">Cuidad</th>
            <th style="width: 120px">Teléfono</th>
            
        </tr>
        </thead>
        <tbody> 
        
        <c:forEach items="${suitors}" var="suitor">
            <tr>    
                <td>
                    <c:out value="${suitor.firstName}" />
                    &nbsp
                    <c:out value="${suitor.lastName}" />
                </td>
                
                <td>
                  ${suitor.user.username} 
                </td>
                <td>
                ${suitor.address}
                    
                </td>
                <td>
                ${suitor.city} 
                </td>
                
                 <td>
                  ${suitor.telephone} 
                </td>
                
       
        </c:forEach>
        </tbody>
    </table>
      
                    
                    </c:otherwise>
    				</c:choose>

   


    </jsp:body>
</petclinic:layout>

