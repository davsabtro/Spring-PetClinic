<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="clinic_owner">
	<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
* {
    box-sizing: border-box;
}

.columns {
    float: left;
    width: 33.3%;
    padding: 8px;
}

.price {
    list-style-type: none;
    border: 1px solid #eee;
    margin: 0;
    padding: 0;
    -webkit-transition: 0.3s;
    transition: 0.3s;
}

.price:hover {
    box-shadow: 0 8px 12px 0 rgba(0,0,0,0.2)
}

.price .header {
    background-color: #111;
    color: white;
    font-size: 25px;
}

.price li {
    border-bottom: 1px solid #eee;
    padding: 20px;
    text-align: center;
}

.price .grey {
    background-color: #eee;
    font-size: 20px;
}

.button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 10px 25px;
    text-align: center;
    text-decoration: none;
    font-size: 18px;
}

@media only screen and (max-width: 600px) {
    .columns {
        width: 100%;
    }
}

img {
  margin: 20px auto;
  position: 30px right;

right: 20px;
width: 10%;
height: auto;
display: block;
}
</style>
</head>
<body>

<h2 style="text-align:center"><FONT SIZE=5>Suscríbete a Petclinic hoy y elige la que más te convenga de nuestras opciones de pago disponibles.</font>

</h2>
<p style="text-align:center"><FONT SIZE=4> Como suscriptor de Petclinic, se te cobrará automáticamente una vez al mes en el día en que te suscribiste. Tienes la flexibilidad de cambiar de plan o cancelar tu suscripción en línea en cualquier momento.</font></p>

<c:set var="plan" value="${currentClinicOwner.plan}"/>

<div class="columns">
  <ul class="price">
    <c:choose>
    <c:when test = "${plan eq 'BASIC' }">
            <li class="header" style="background-color:#4CAF50">Básico</li>
         </c:when>
         <c:otherwise>
         <li class="header">Basic</li>
         </c:otherwise>
         </c:choose>
    <li class="grey">Gratis <br>    &nbsp</li>
    <li ><FONT SIZE=3>Adopción<img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Causas <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> SLA <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Gestión de más de 1 clinica <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
     <li><FONT SIZE=3> Tipos de mascotas ilimitados <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Veterinarios ilimitados <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Visitas al mes ilimitadas <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Pet Hotel <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Soporte propietarios <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Generación de contraseñas seguras <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Notificaciones de inicio de sesión <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
 
     <c:choose>
    <c:when test = "${plan eq 'BASIC' }">
        <li class="grey"><a href="#" class="button">Tu plan actual</a></li>    
         </c:when>
   
         <c:otherwise>
         
         
         <spring:url value="/clinicowner/changePlan/{plan}" var="premiumUrl">
								<spring:param name="plan" value="BASIC" />
							</spring:url>
							<li class="grey"><a href="${fn:escapeXml(premiumUrl)}" class="button"
								><c:out value="Elegir"
							/></a></li>
          
         </c:otherwise>
      </c:choose>
  </ul>
</div>





<div class="columns">
  <ul class="price">
  <c:choose>
         
         <c:when test = "${plan eq 'ADVANCED' }">
            <li class="header" style="background-color:#4CAF50">Avanzado</li>
         </c:when>
         <c:otherwise>
         <li class="header">Advanced</li>
         </c:otherwise>
         </c:choose>
    
    <li class="grey">10.00€ / mes <br>100.00€ / año</li>
    <li ><FONT SIZE=3>Adopción<img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Causas <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> SLA <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Gestión de más de 1 clinica <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
     <li><FONT SIZE=3> Tipos de mascotas ilimitados <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Veterinarios ilimitados <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Visitas al mes ilimitadas <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Pet Hotel <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Soporte propietarios <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Generación de contraseñas seguras <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Notificaciones de inicio de sesión <img id="gif" src="/resources/images/red-mark-cross-removebg-preview.png" width="50px"></font></li>
    <c:choose>
         
         <c:when test = "${plan eq 'ADVANCED' }">
        <li class="grey"><a href="#" class="button">Tu plan actual</a></li>    
         </c:when>
   
         <c:otherwise>
         
         
         <spring:url value="/clinicowner/changePlan/{plan}" var="premiumUrl">
								<spring:param name="plan" value="ADVANCED" />
							</spring:url>
							<li class="grey"><a href="${fn:escapeXml(premiumUrl)}" class="button"
								><c:out value="Elegir"
							/></a></li>
          
         </c:otherwise>
      </c:choose>
  </ul>
</div>

<div class="columns">
  <ul class="price">
    <c:choose>
    <c:when test = "${plan eq 'PRO' }">
            <li class="header" style="background-color:#4CAF50">Pro</li>
         </c:when>
         <c:otherwise>
         <li class="header">Pro</li>
         </c:otherwise>
         </c:choose>
    <li class="grey"> 20.00€ / mes <br>200.00€ / año</br></li>
    <li ><FONT SIZE=3>Adopción<img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Causas <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> SLA <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Gestión de más de 1 clinica <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
     <li><FONT SIZE=3> Tipos de mascotas ilimitados <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Veterinarios ilimitados <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Visitas al mes ilimitadas <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Pet Hotel <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Soporte propietarios <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Generación de contraseñas seguras <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
    <li><FONT SIZE=3> Notificaciones de inicio de sesión <img id="gif" src="/resources/images/check-mark-removebg-preview.png" width="50px"></font></li>
   
	<c:choose>
    <c:when test = "${plan eq 'PRO' }">
        <li class="grey"><a href="#" class="button">Tu plan actual</a></li>    
         </c:when>
   
         <c:otherwise>
         
         
         <spring:url value="/clinicowner/changePlan/{plan}" var="premiumUrl">
								<spring:param name="plan" value="PRO" />
							</spring:url>
							<li class="grey"><a href="${fn:escapeXml(premiumUrl)}" class="button"
								><c:out value="Elegir"
							/></a></li>
          
         </c:otherwise>
      </c:choose>
  </ul>
</div>

</body>
</html>

</petclinic:layout>