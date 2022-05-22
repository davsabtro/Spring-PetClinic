<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Dueños</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="veterinarians">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Veterinarios</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'causes'}" url="/causes"
					title="causes">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Causas</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'pethotels'}" url="/pethotels/new"
					title="hotel">
					<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
					<span>Hotel</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'adoption'}" url="/adoption/petsList"
					title="hotel">
					<span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
					<span>Dar en adopción</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'adoption'}" url="/adoption/petsOnAdoptionList"
					title="hotel">
					<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
					<span>Adoptar</span>
				</petclinic:menuItem>
				
				<sec:authorize access="hasAnyAuthority('basicClinicOwner','advancedClinicOwner','proClinicOwner') and isAuthenticated()">
					<petclinic:menuItem active="${name eq 'premium'}" url="/clinicowner/premium"
						title="hotel">
						<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
						<span>Premium</span>
					</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="!isAuthenticated()">
					<petclinic:menuItem active="${name eq 'customeragreementsAll'}" url="/customeragreement/showAll"
						title="customeragreementsall">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Acuerdos de cliente</span>
					</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="hasAuthority('basicClinicOwner') and isAuthenticated()">
					<petclinic:menuItem active="${name eq 'customeragreementBasic'}" url="/customeragreement/showBasic"
						title="customeragreementbasic">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mi acuerdo de cliente</span>
					</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="hasAuthority('advancedClinicOwner') and isAuthenticated()">
					<petclinic:menuItem active="${name eq 'customeragreementAdvanced'}" url="/customeragreement/showAdvanced"
						title="customeragreementadvanced">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mi acuerdo de cliente</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('proClinicOwner') and isAuthenticated()">
					<petclinic:menuItem active="${name eq 'customeragreementPro'}" url="/customeragreement/showPro"
						title="customeragreementpro">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						<span>Mi acuerdo de cliente</span>
					</petclinic:menuItem>
				</sec:authorize>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />"><strong>Entrar</strong></a></li>
				<!--  	<li><a href="<c:url value="/users/new" />">Registro</a></li> -->
				
				<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">
							<strong>Registro</strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							
				<li><a href="<c:url value="/clinics/new"/>">Clinica</a></li>
				<li><a href="<c:url value="/users/new"/>">Dueño</a></li>
				</ul>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											
													
													<p class="text-left">
													<a href="<c:url value="/users/changePassword" />"
													class="btn btn-primary btn-block btn-sm">Cambiar contraseña</a>
													
													<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Salir</a>
													
													<p class="text-left">
											</p>
											
											
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">Mi Perfil</a>
												<a href="#" class="btn btn-danger btn-block">Cambiar
													Contraseña>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
