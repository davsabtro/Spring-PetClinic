<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptionDetails">

    <table id="adoptionDetails" class="table table-striped">
        <tr>
            <th style="width: 150px;">Nombre</th>
            <td>
                <c:out value="${request.suitorToAdopt.firstName}" />
                &nbsp
                <c:out value="${request.suitorToAdopt.lastName}" />
            </td>
        </tr>
        <tr>
            <th style="width: 150px;">Username</th>
            <td>
                ${request.suitorToAdopt.user.username} 
            </td>
        </tr>
        <tr>
            <th style="width: 200px;">Dirección</th>
            <td>
            ${request.suitorToAdopt.address} 
            </td>
        </tr>
        <tr>
            <th style="width: 120px">Cuidad</th>
            <td>
            ${request.suitorToAdopt.city} 
            </td>
        </tr>    
        <tr>
            <th style="width: 120px">Teléfono</th>
            <td>
                ${request.suitorToAdopt.telephone} 
            </td>
         </tr>   
         <tr>
            <th style="width: 120px">Fecha solicitud</th>
            <td>
                ${request.request_date} 
            </td>
        </tr>    
        <tr>
            <th style="width: 120px">Mensaje</th> 
            <td>
                ${request.careDescription} 
            </td>
        </tr>  
    </table>

    <spring:url value="/adoption/{adoptionId}/suitor/{suitorId}/approve" var="aprobacionURL">
        <spring:param name="adoptionId" value="${request.adoption.id}"/>
        <spring:param name="suitorId" value="${request.suitorToAdopt.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(aprobacionURL)}" class="btn btn-default"><c:out value="Aprobar adopción"/></a>

</petclinic:layout>