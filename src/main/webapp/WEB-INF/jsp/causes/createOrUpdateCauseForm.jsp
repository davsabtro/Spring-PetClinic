<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2>
        <c:if test="${cause['new']}">Nueva </c:if> Causa
    </h2>
    <form:form modelAttribute="cause" class="form-horizontal" id="add-cause-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="name"/>
            <petclinic:inputField label="Descripcion" name="description"/>
            <petclinic:inputField label="Organizacion" name="organization"/>
            <petclinic:inputField label="Objetivo Recaudacion" name="budgetTarget"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${cause['new']}">
                        <button class="btn btn-default" type="submit">Nueva Causa</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Causa</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>