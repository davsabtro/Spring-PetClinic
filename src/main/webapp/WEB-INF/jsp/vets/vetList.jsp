<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="vets">
    <h2>Veterinarios</h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Especialidades</th>
            <sec:authorize access="hasAnyAuthority('admin')">
                <th>Acciones</th>
            </sec:authorize>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <spring:url value="/vets/{vetId}" var="vetUrl">
                        <spring:param name="vetId" value="${vet.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vetUrl)}"><c:out value="${vet.firstName} ${vet.lastName}"/></a>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                <sec:authorize access="hasAnyAuthority('admin')">
                <td>
                    <spring:url value="/vets/{vetId}/delete" var="deleteVetUrl">
                        <spring:param name="vetId" value="${vet.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteVetUrl)}" class="btn btn-default"><c:out value="Eliminar Veterinario"/></a>
                </td>
            </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <spring:url value="/vets/new" var="addUrl">
    </spring:url>
    <sec:authorize access="hasAnyAuthority('admin')">
        <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir Veterinario</a>
    </sec:authorize>
    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">Ver como XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
