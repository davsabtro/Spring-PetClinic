<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
   <c:if test="${owner['new']}"> <h2>
        Nuevo  Propietario
    </h2>
    <form:form modelAttribute="owner" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="firstName"/>
            <petclinic:inputField label="Apellido" name="lastName"/>
            <petclinic:inputField label="Direccion" name="address"/>
            <petclinic:inputField label="Ciudad" name="city"/>
            <petclinic:inputField label="Telefono" name="telephone"/>
            <petclinic:inputField label="Usuario" name="user.username"/>
            <petclinic:inputField label="Contraseña" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${owner['new']}">
                        <button class="btn btn-default" type="submit">Añadir Propietario</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Propietario</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</c:if>    
    <c:if test="${clinicOwner['new']}">
    	<h2>
		Nueva  clinica
	</h2>
	<form:form modelAttribute="clinicOwner" class="form-horizontal"
		id="add-owner-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Nombre" name="clinicName" />
			<petclinic:inputField label="Email" name="email" />
			<petclinic:inputField label="CIF/NIF" name="cif" />
			<petclinic:inputField label="Direccion" name="address" />
			<petclinic:inputField label="Ciudad" name="city" />
			<petclinic:inputField label="Telefono" name="telephone" />
			<petclinic:inputField label="Usuario" name="user.username" />
			<petclinic:inputField label="Contraseña" name="user.password" />
		</div>
		 <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${clinicOwner['new']}">
                        <button class="btn btn-default" type="submit">Añadir clinica</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar clinica</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
	</form:form>
    </c:if>
</petclinic:layout>
