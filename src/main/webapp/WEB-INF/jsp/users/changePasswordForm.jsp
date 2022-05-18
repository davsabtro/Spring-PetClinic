<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>
        Cambiar contraseña
    </h2>
    <form:form modelAttribute="values" class="form-horizontal" id="change-password-form">
        <div class="form-group has-feedback">
            <div style="display: none;">
            <petclinic:inputField type="password" label="Contaseña" name="userPassword"/>
            </div>
            <petclinic:inputField type="password" label="Contraseña actual" name="currentPassword"/>
            <petclinic:inputField type="password" label="Contraseña nueva" name="newPassword"/>
            <petclinic:inputField type="password" label="Confirmar contraseña" name="confirmPassword" />
        </div>
        <button class="btn btn-default" type="button" id="update" onclick="javascript:ValidateForm()">Actualizar</button>
        <button class="btn btn-default" type="button" id="generate-password" onclick="javascript:GeneratePassword()">Generar contraseña segura</button>

        <script type="text/javascript">
            ValidateForm = function() {
                var userPassword = document.forms["change-password-form"]["userPassword"].value;
                var currentPassword = document.forms["change-password-form"]["currentPassword"].value;
                var newPassword = document.forms["change-password-form"]["newPassword"].value;
                var confirmPassword = document.forms["change-password-form"]["confirmPassword"].value;

                if (newPassword.length < 4) {
                    alert("La contraseña debe tener al menos 4 caracteres");
                    return false;
                }
                if (userPassword != currentPassword) {
                    alert("La contraseña actual no es correcta");
                    return false;
                }

                if (newPassword !== confirmPassword) {
                    alert("Las contraseñas no coinciden");
                    return false;
                }
                document.querySelector("#change-password-form").submit();
            }

            GeneratePassword = async function() {
                const options = {
                    method: 'POST',
                    headers: {
                        'content-type': 'application/json',
                        'X-RapidAPI-Host': 'passwordgen.p.rapidapi.com',
                        'X-RapidAPI-Key': '0d9a6f16camsh11dff2b762fab22p1e2b05jsn2ab9693be005'
                    },
                    body: '{"isUpperCase":true,"isLowerCase":true,"isNumber":true,"isSymbol":false,"maxLength":12}'
                };

                fetch('https://passwordgen.p.rapidapi.com/api/v1/custom-password', options)
                    .then(response => response.json())
                    .then(body => {
                        var securePassword = body.password
                        alert("Tu nueva contraseña segura es " + securePassword +". Cópiala antes de enviarla, no la podrás volver a ver!");
                        document.forms["change-password-form"]["newPassword"].value = securePassword;
                        document.forms["change-password-form"]["confirmPassword"].value = securePassword;
                    })
                    .catch(err => console.error(err));
            }
        </script>
    </form:form>
</petclinic:layout>
