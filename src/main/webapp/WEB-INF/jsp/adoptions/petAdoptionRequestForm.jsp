<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<style>
* {
    box-sizing: border-box;
}

input[type=text], select, textarea {
    width: 100%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    resize: vertical;
}

label {
    padding: 12px 12px 12px 0;
    display: inline-block;
}

input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 12px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    float: left;
}

input[type=submit]:hover {
    background-color: #45a049;
}


.col-25 {
    float: right;
    width: 25%;
    margin-top: 6px;
}

.col-75 {
    float: left;
    width: 75%;
    margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
    content: "";
    display: table;
    clear: both;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
    .col-25, .col-75, input[type=submit] {
        width: 100%;
        margin-top: 0;
    }
}

</style>

<petclinic:layout pageName="owners">	
    <h2>   
    <security:authorize access="isAuthenticated()">  <security:authentication
					property="principal.username" />,  
</security:authorize>
        <c:if test="${adoption != null}">termine su solicitud de adopción </c:if> 
    	
    </h2>
    <h1>Por favor, describa abajo cómo piensa cuidar de ${adoption.pet.name}</h1>
    <form:form modelAttribute="adoption" class="form-horizontal" id="add-owner-form"> 
   <div class="container"> 
   <div class="row">
      <div class="col-25">
      </div>
      <div class="col-75">
        <textarea id="careDescription" name="careDescription" placeholder="Escriba aquí.." style="height:200px"></textarea>
      </div>
    </div>
     <input type="text" name="adoptionId" value="${adoption.id}" style="display:none">
     <input type="text" name="petId" value="${adoption.pet.id}" style="display:none">
     <input type="text" name="adopterId" value="${adoption.owner.id}" style="display:none">
        </div>
        <div class="form-group">
            <div class="col-sm-offset-0 col-sm-10">
                <c:choose>
                    <c:when test="${adoption != null}">
                        <button class="btn btn-default" type="submit">Solicitar adopción</button>
                    </c:when>
                   
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>