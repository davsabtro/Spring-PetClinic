<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">

    <h2>Información de la causa</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${cause.name}"/></b></td>
        </tr>
        <tr>
            <th>Descripción</th>
            <td><c:out value="${cause.description}"/></td>
        </tr>
        <tr>
            <th>Organización</th>
            <td><c:out value="${cause.organization}"/></td>
        </tr>
        <tr>
            <th>Recaudación</th>
            <td><c:out value="${cause.donated}"/></td>
        </tr>

        <tr>
            <th>Objetivo recaudación</th>
            <td><c:out value="${cause.budgetTarget}"/></td>
        </tr>
    </table>

    <spring:url value="/causes/{causeId}/donate/new" var="donateUrl">
        <spring:param name="causeId" value="${cause.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(donateUrl)}" class="btn btn-default">Dona a esta causa</a>
     


    <br/>
    <br/>
    <br/>
    <h2>Donaciones:</h2>

    <table class="table table-striped">
        <c:forEach var="donation" items="${donationList}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Donante</dt>
                        <dd><c:out value="${donation.client.username}"/></dd>
                        <dt>Fecha</dt>
                        <dd><petclinic:localDate date="${donation.donationDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Cantidad</dt>
                        <dd><c:out value="${donation.amount}"/></dd>
                    </dl>
                </td>
            
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>