<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-danger shadow-sm sticky-top">
    <div class="container">
        <a class="navbar-brand fw-bold d-flex align-items-center" href="${pageContext.request.contextPath}/home">
            <i class="bi bi-fire fs-3 me-2 text-warning"></i>
            <span>FireAlert <span class="badge bg-dark fs-6 ms-1">GR03_1BT3</span></span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
                aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link ${pageContext.request.servletPath == '/index.jsp' ? 'active fw-bold' : ''}"
                       href="${pageContext.request.contextPath}/home">
                        <i class="bi bi-speedometer2 me-1"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${pageContext.request.servletPath == '/reportar-incendio.jsp' ? 'active fw-bold' : ''}"
                       href="${pageContext.request.contextPath}/reportar">
                        <i class="bi bi-exclamation-triangle-fill text-warning me-1"></i> Reportar Incendio (CU1)
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${pageContext.request.servletPath == '/actualizar-estado.jsp' ? 'active fw-bold' : ''}"
                       href="${pageContext.request.contextPath}/incendios">
                        <i class="bi bi-shield-fill-check text-info me-1"></i> Panel Bomberos (CU2)
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${pageContext.request.servletPath == '/mapa.jsp' ? 'active fw-bold' : ''}"
                       href="${pageContext.request.contextPath}/mapa">
                        <i class="bi bi-geo-alt-fill me-1"></i> Mapa en Vivo (CU3)
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${pageContext.request.servletPath == '/rutas-evacuacion.jsp' ? 'active fw-bold' : ''}"
                       href="${pageContext.request.contextPath}/evacuacion">
                        <i class="bi bi-signpost-2-fill text-warning me-1"></i> Rutas Evacuación (CU4)
                    </a>
                </li>
            </ul>

            <div class="d-flex align-items-center text-white">
                <span class="badge bg-dark border border-warning text-warning px-3 py-2">
                    <i class="bi bi-broadcast me-1 text-danger"></i> Monitoreo Activo - DMQ Quito
                </span>
            </div>
        </div>
    </div>
</nav>

<!-- Mensajes Flash de Sesión -->
<div class="container mt-3">
    <c:if test="${not empty sessionScope.mensajeExito}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm d-flex align-items-center" role="alert">
            <i class="bi bi-check-circle-fill fs-4 me-2"></i>
            <div>${sessionScope.mensajeExito}</div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="mensajeExito" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.mensajeError}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm d-flex align-items-center" role="alert">
            <i class="bi bi-exclamation-octagon-fill fs-4 me-2"></i>
            <div>${sessionScope.mensajeError}</div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="mensajeError" scope="session"/>
    </c:if>

    <c:if test="${not empty requestScope.mensajeError}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm d-flex align-items-center" role="alert">
            <i class="bi bi-exclamation-octagon-fill fs-4 me-2"></i>
            <div>${requestScope.mensajeError}</div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
</div>
