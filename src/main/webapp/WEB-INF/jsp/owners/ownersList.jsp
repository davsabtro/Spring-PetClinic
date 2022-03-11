<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">
    <h2>Propietarios</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
            <tr>
            <th style="width: 150px; background-color: lightgray;">Nombre</th>
            <th style="width: 200px; background-color: lightgray;">Direccion</th>
            <th style="background-color: lightgray;">Ciudad</th>
            <th style="width: 120px; background-color: lightgray;">Telefono</th>
            <th style="background-color: lightgray;">Mascotas</th>
            <sec:authorize access="hasAnyAuthority('admin')">
                <th style="background-color: lightgray;">Acciones</th>
            </sec:authorize>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="owner">
            <tr>
                <td>
                    <spring:url value="/owners/{ownerId}" var="ownerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${owner.firstName} ${owner.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${owner.address}"/>
                </td>
                <td>
                    <c:out value="${owner.city}"/>
                </td>
                <td>
                    <c:out value="${owner.telephone}"/>
                </td>
                <td>
                    <c:forEach var="pet" items="${owner.pets}">
                        <c:out value="${pet.name} "/>
                    </c:forEach>
                </td>
            <sec:authorize access="hasAnyAuthority('admin')">   
                <td>
                    <spring:url value="/owners/{ownerId}/delete" var="deleteOwnerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteOwnerUrl)}" class="btn btn-default"><c:out value="Eliminar propietario"/></a>
                </td>
            </sec:authorize>
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>