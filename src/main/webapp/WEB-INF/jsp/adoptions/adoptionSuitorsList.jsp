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
   <h2> <security:authorize access="isAuthenticated()"> Hola, <security:authentication
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
            <th style="width: 120px">Fecha solicitud</th>
        </tr>
        </thead>
        <tbody> 
        <c:forEach items="${detailsAdoption}" var="details">
            <tr>    
                <td>
                    <spring:url value="/adoption/{adoptionId}/suitor/{suitorId}" var="detailsAdoptionURL">
                        <spring:param name="adoptionId" value="${details.adoption.id}"/>
                        <spring:param name="suitorId" value="${details.suitorToAdopt.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(detailsAdoptionURL)}">
                      <c:out value="${details.suitorToAdopt.firstName} ${details.suitorToAdopt.lastName}"/>
                    </a>
                </td>

                <td>
                  ${details.request_date} 
                </td>
        </c:forEach>
        </tbody>
    </table>
                    </c:otherwise>
    				</c:choose>
    </jsp:body>
</petclinic:layout>