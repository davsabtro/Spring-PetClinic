<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">

    <h2>Informacion del Veterinario</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${vet.firstName} ${vet.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Especialidades</th>
            <td>
            <c:forEach var="specialty" items="${vet.specialties}">
                <c:out value="${specialty.name} "/>
            </c:forEach>
           <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
           </td>
        </tr>
    </table>

    <spring:url value="{vetId}/edit" var="editUrl">
        <spring:param name="vetId" value="${vet.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Veterinario</a>

</petclinic:layout>
