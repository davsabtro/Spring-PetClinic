<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<style><%@ include file="pupup.less"%></style>

<petclinic:layout pageName="pethotels">

	<h2>
		<security:authorize access="isAuthenticated()"> Hola, <security:authentication
				property="principal.username" />! 
</security:authorize>
		<c:if test="${pethotel['new']}">Reserva una habitación para tu mascota en  </c:if>
		<img id="png" src="/resources/images/pet_hotel_logo.png" width="100px">
	</h2>

	<form:form modelAttribute="pethotel" class="form-horizontal"
		id="add-owner-form">
		<div class="form-group has-feedback">

			<label for="startDate">Entrada:</label> <input type="date"
				id="startDate" name="startDate" min="${today}"> <label
				for="finishDate">Salida:</label> <input type="date" id="finishDate"
				name="finishDate" min="${tomorrow}"> <br> </br>
			<h2>
				Para que mascota quieres la habitación?
				<c:if test="${not empty petHotelDataAboutThisOwner}">

					<div class="popup" onclick="myFunction()">
						<span class="glyphicon glyphicon-info-sign"></span> <span
							class="popuptext" id="myPopup"> <br> <c:forEach
								items="${petHotelDataAboutThisOwner}" var="petHotelData">
     								${petHotelData.pet.name} tiene una reserva desde <fmt:formatDate
									value="${petHotelData.startDate}" pattern="yyyy-MM-dd" /> hasta <fmt:formatDate
									value="${petHotelData.finishDate}" pattern="yyyy-MM-dd" />
								</br>
							</c:forEach>

						</span>
					</div>

					<script>
						function myFunction() {
							var popup = document.getElementById("myPopup");
							popup.classList.toggle("show");
						}
					</script>
				</c:if>
			</h2>
			<c:forEach items="${petsCollection}" var="pethotels">
				<tr>
					<td><input type="radio" id="pet" name="pet"
						value="${pethotels.id}"> <label for="pet">
							${pethotels.name} </label><br></td>
			</c:forEach>
			</br>
		</div>
		<div class="form-group">
			<div>
				<c:choose>
					<c:when test="${pethotel['new']}">
						<button class="btn btn-default" type="submit">Reservar</button>
					</c:when>
				</c:choose>
			</div>
		</div>
	</form:form>


</petclinic:layout>
