<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page session="false" trimDirectiveWhitespaces="true"
%> <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> <%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib prefix="petclinic"
tagdir="/WEB-INF/tags" %> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">
	<h2>Causas disponibles</h2>
	
	

	<table id="causesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Descripción</th>
				<th>Organización</th>
				<th>Recaudación</th>
				<th>Objetivo recaudación</th>
				<th>Donar</th>

				<sec:authorize access="hasAnyAuthority('admin')">
					<th>Acciones</th>
				</sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${causes}" var="cause">
				<tr>
					<td>
						<spring:url value="/causes/{causeId}" var="causeUrl">
							<spring:param name="causeId" value="${cause.id}" />
						</spring:url>
						<a href="${fn:escapeXml(causeUrl)}"
							><c:out value="${cause.name}"
						/></a>
					</td>
					<td>
						<c:out value="${cause.description}" />
					</td>
					<td>
						<c:out value="${cause.organization}" />
					</td>
					<td>
						<c:out value="${cause.donated}" />
					</td>
					<td>
						<c:out value="${cause.budgetTarget}" />
					</td>
					<td>
						<spring:url value="/causes/{causeId}/donate/new" var="donateUrl">
							<spring:param name="causeId" value="${cause.id}" />
						</spring:url>
						<a href="${fn:escapeXml(donateUrl)}" class="btn btn-default"
							><c:out value="Dona a esta causa"
						/></a>
					</td>
					<sec:authorize access="hasAnyAuthority('admin')">
						<td>
							<spring:url value="/causes/{causeId}/delete" var="deleteCauseUrl">
								<spring:param name="causeId" value="${cause.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteCauseUrl)}" class="btn btn-default"
								><c:out value="Eliminar Causa"
							/></a>
						</td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<sec:authorize access="hasAnyAuthority('owner', 'advancedClinicOwner', 'admin')">
		<spring:url value="/causes/new" var="addUrl"> </spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Crear causa</a>
	</sec:authorize>
</petclinic:layout>
