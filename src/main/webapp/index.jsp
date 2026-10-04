<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AlerFire — Dashboard de Monitoreo de Incendios</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        .metric-card {
            border-left: 5px solid;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .metric-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
        #miniMapa {
            height: 340px;
            border-radius: 8px;
        }
    </style>
</head>
<body class="bg-light">

<jsp:include page="/common/navbar.jsp"/>

<div class="container my-4">
    <!-- Header Hero -->
    <div class="p-4 mb-4 bg-white rounded-3 shadow-sm border border-danger border-2">
        <div class="row align-items-center">
            <div class="col-lg-8">
                <h1 class="display-6 fw-bold text-danger mb-2">
                    <i class="bi bi-fire"></i> Sistema de Alerta Temprana y Evacuación de Incendios
                </h1>
                <p class="lead text-secondary mb-3">
                    Plataforma inteligente para la notificación comunitaria, despacho y actualización de conatos de incendio por el Cuerpo de Bomberos, y cálculo de rutas seguras de evacuación.
                </p>
                <div class="d-flex flex-wrap gap-2">
                    <a href="${pageContext.request.contextPath}/reportar" class="btn btn-danger btn-lg shadow-sm">
                        <i class="bi bi-megaphone-fill me-1"></i> Reportar Incendio (CU1)
                    </a>
                    <a href="${pageContext.request.contextPath}/incendios" class="btn btn-outline-dark btn-lg shadow-sm">
                        <i class="bi bi-shield-shaded me-1"></i> Panel Bomberos (CU2)
                    </a>
                    <a href="${pageContext.request.contextPath}/mapa" class="btn btn-warning btn-lg shadow-sm text-dark fw-semibold">
                        <i class="bi bi-map-fill me-1"></i> Ver Mapa (CU3)
                    </a>
                    <a href="${pageContext.request.contextPath}/evacuacion" class="btn btn-success btn-lg shadow-sm">
                        <i class="bi bi-compass-fill me-1"></i> Evacuación (CU4)
                    </a>
                </div>
            </div>
            <div class="col-lg-4 text-center mt-3 mt-lg-0">
                <div class="card bg-danger text-white border-0 shadow">
                    <div class="card-body py-4">
                        <i class="bi bi-exclamation-triangle-fill display-4 text-warning mb-2"></i>
                        <h4 class="card-title fw-bold">Alerta Activa DMQ</h4>
                        <p class="card-text small mb-2">Distrito Metropolitano de Quito</p>
                        <span class="badge bg-warning text-dark fs-6 px-3 py-2">
                            ${noControladosCount} Incendios No Controlados
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Métricas del Sistema -->
    <div class="row g-3 mb-4">
        <div class="col-md-3 col-sm-6">
            <div class="card metric-card border-danger shadow-sm h-100 bg-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted text-uppercase fw-semibold mb-1">Activos</h6>
                            <h2 class="fw-bold text-danger mb-0">${activosCount}</h2>
                        </div>
                        <i class="bi bi-fire fs-1 text-danger"></i>
                    </div>
                    <small class="text-muted">Focos en monitoreo</small>
                </div>
            </div>
        </div>

        <div class="col-md-3 col-sm-6">
            <div class="card metric-card border-warning shadow-sm h-100 bg-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted text-uppercase fw-semibold mb-1">Pendientes</h6>
                            <h2 class="fw-bold text-warning mb-0">${pendientesCount}</h2>
                        </div>
                        <i class="bi bi-hourglass-split fs-1 text-warning"></i>
                    </div>
                    <small class="text-muted">Por atender / en curso</small>
                </div>
            </div>
        </div>

        <div class="col-md-3 col-sm-6">
            <div class="card metric-card border-success shadow-sm h-100 bg-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted text-uppercase fw-semibold mb-1">Extintos</h6>
                            <h2 class="fw-bold text-success mb-0">${extintosCount}</h2>
                        </div>
                        <i class="bi bi-check-circle-fill fs-1 text-success"></i>
                    </div>
                    <small class="text-muted">Liquidación confirmada</small>
                </div>
            </div>
        </div>

        <div class="col-md-3 col-sm-6">
            <div class="card metric-card border-primary shadow-sm h-100 bg-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted text-uppercase fw-semibold mb-1">Zonas Seguras</h6>
                            <h2 class="fw-bold text-primary mb-0">${zonasSeguras.size()}</h2>
                        </div>
                        <i class="bi bi-shield-check fs-1 text-primary"></i>
                    </div>
                    <small class="text-muted">Puntos de evacuación</small>
                </div>
            </div>
        </div>
    </div>

    <!-- Sección de Mapa y Reportes Recientes -->
    <div class="row g-4">
        <!-- Mini Mapa -->
        <div class="col-lg-7">
            <div class="card shadow-sm h-100 bg-white">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0 text-danger">
                        <i class="bi bi-geo-alt-fill me-1"></i> Vista Previa del Mapa en Vivo
                    </h5>
                    <a href="${pageContext.request.contextPath}/mapa" class="btn btn-sm btn-outline-danger">
                        Ver Mapa Completo <i class="bi bi-arrow-right"></i>
                    </a>
                </div>
                <div class="card-body p-2">
                    <div id="miniMapa"></div>
                </div>
            </div>
        </div>

        <!-- Alertas Recientes -->
        <div class="col-lg-5">
            <div class="card shadow-sm h-100 bg-white">
                <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0 text-dark">
                        <i class="bi bi-bell-fill text-warning me-1"></i> Últimas Notificaciones
                    </h5>
                    <span class="badge bg-danger rounded-pill">${notificaciones.size()}</span>
                </div>
                <div class="card-body p-0">
                    <div class="list-group list-group-flush">
                        <c:choose>
                            <c:when test="${empty notificaciones}">
                                <div class="p-4 text-center text-muted">
                                    <i class="bi bi-check2-all fs-2 text-success"></i>
                                    <p class="mb-0 mt-2">No hay alertas recientes.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="notif" items="${notificaciones}">
                                    <div class="list-group-item p-3">
                                        <div class="d-flex w-100 justify-content-between mb-1">
                                            <span class="badge ${notif.tipoAlerta == 'NUEVO_INCENDIO' ? 'bg-danger' : 'bg-warning text-dark'}">
                                                ${notif.tipoAlerta}
                                            </span>
                                            <small class="text-muted">
                                                <i class="bi bi-clock me-1"></i>${notif.fechaHora.toLocalTime().toString().substring(0, 5)}
                                            </small>
                                        </div>
                                        <p class="mb-1 small fw-semibold text-dark">${notif.mensaje}</p>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp"/>

<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    // Inicializar mini mapa centrado en Quito
    const map = L.map('miniMapa').setView([-0.180653, -78.467838], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    // Cargar marcadores de incendios activos
    <c:forEach var="inc" items="${incendiosRecientes}">
        <c:if test="${inc.ubicacion != null}">
            L.circleMarker([${inc.ubicacion.latitud}, ${inc.ubicacion.longitud}], {
                radius: 9,
                fillColor: "${inc.estado == 'NO_CONTROLADO' ? '#dc3545' : (inc.estado == 'EN_PROCESO_DE_ATENCION' ? '#ffc107' : '#0dcaf0')}",
                color: "#fff",
                weight: 2,
                opacity: 1,
                fillOpacity: 0.85
            }).addTo(map).bindPopup("<b>Incendio #${inc.id}</b><br>${inc.ubicacion.direccion}<br><span class='badge bg-danger'>${inc.estado.descripcion}</span>");
        </c:if>
    </c:forEach>

    // Cargar Zonas Seguras
    <c:forEach var="z" items="${zonasSeguras}">
        <c:if test="${z.ubicacion != null}">
            L.circleMarker([${z.ubicacion.latitud}, ${z.ubicacion.longitud}], {
                radius: 7,
                fillColor: "#198754",
                color: "#fff",
                weight: 2,
                opacity: 1,
                fillOpacity: 0.85
            }).addTo(map).bindPopup("<b>Zona Segura: ${z.nombre}</b><br>Capacidad: ${z.capacidad} personas");
        </c:if>
    </c:forEach>
</script>
</body>
</html>
