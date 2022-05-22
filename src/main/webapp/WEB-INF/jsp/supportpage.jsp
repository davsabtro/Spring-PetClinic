<<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Support">

    <h2>Informacion de contacto</h2>

    <table>
        <thead>
        <tr>
            <th style="width: 500px;">Correo</th>
            <th style="width: 500px;">Contacto</th>
            <th>Pagina de soporte</th>

        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <c:out value="soportePetClinic@gmail.com"/>
                </td>
                <td>
                    <c:out value="656 48 32 93"/>
                </td>
                <td>
                    <a href="https://github.com/gii-is-psg2/psg2-2122-g4-43"><c:out value="GitHub"></c:out></a>
                </td>
            </tr>
        </tbody>
    </table>
</petclinic:layout>
