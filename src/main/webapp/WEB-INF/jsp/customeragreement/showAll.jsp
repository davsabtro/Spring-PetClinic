<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="customeragreement">

    <h2>Acuerdos de cliente</h2>

    <a href="/customeragreement/showBasic" class="btn btn-default">Acuerdo de cliente (Basic)</a>
    <a href="/customeragreement/showAdvanced" class="btn btn-default">Acuerdo de cliente (Advanced)</a>
    <a href="/customeragreement/showPro" class="btn btn-default">Acuerdo de cliente (Pro)</a>

</petclinic:layout>