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
<h2> Ya tienes una solicitud de adopción en curso cuyos datos se encuentran abajo. ¡Espera su aprobación! </h2>

<table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Fecha de nacimiento</th>
            <th style="width: 120px">Tipo</th>
            <th style="width: 120px">Dueño</th>
            
            
        </tr>
        </thead>
        <tbody> 
        
   
            <tr>    
                <td>
                    <c:out value="${petData.pet.name}" />
                </td>
                <td>
                ${petData.pet.birthDate.format( DateTimeFormatter.ofPattern("dd/MM/yyyy"))}
                    
                </td>
                <td>
                ${petData.pet.type} 
                </td>
                 <td>
                ${petData.owner.user.username} 
                </td>
             </tbody>
    </table>      
               
         
    </jsp:body>
</petclinic:layout>



