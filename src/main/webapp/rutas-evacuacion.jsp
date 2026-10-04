<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rutas de Evacuación Seguras (CU4) — AlerFire</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        #routeMap {
            height: 580px;
            border-radius: 8px;
        }
        .route-card {
            cursor: pointer;
            transition: all 0.2s ease;
        }
        .route-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.1);
        }
        .route-selected {
            border: 2px solid #198754 !important;
            background-color: #f6fff8 !important;
        }
    </style>
</head>
<body class="bg-light">

<jsp:include page="/common/navbar.jsp"/>

<div class="container my-4">
    <!-- Encabezado de Caso de Uso -->
    <div class="card shadow-sm border-0 mb-4 bg-white">
        <div class="card-body p-4">
            <div class="d-flex align-items-center mb-2">
                <span class="badge bg-success fs-6 me-2">Caso de Uso 4 (CU4)</span>
                <span class="badge bg-secondary">PantallaRutas / PantallaSalida</span>
            </div>
            <h2 class="fw-bold text-success mb-1">
                <i class="bi bi-signpost-2-fill me-2"></i> Solicitar Rutas de Evacuación Asistidas por Algoritmo de Riesgo
            </h2>
            <p class="text-muted mb-0">
                El sistema evalúa la distancia a las zonas seguras autorizadas de Quito y calcula el nivel de riesgo de cada trayecto tomando en cuenta la ubicación y propagación de los incendios activos, recomendando la alternativa más segura.
            </p>
        </div>
    </div>

    <!-- Barra de Origen / Ubicación del Usuario -->
    <form action="${pageContext.request.contextPath}/evacuacion" method="GET" class="card shadow-sm border-0 bg-white p-3 mb-4">
        <div class="row g-2 align-items-center">
            <div class="col-md-5">
                <label class="form-label small fw-bold mb-1">Tu Ubicación / Punto de Partida:</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-geo-alt"></i></span>
                    <input type="text" class="form-control" name="direccion" id="direccionInput"
                           value="${ubicacionUsuario.direccion}" required>
                </div>
            </div>
            <div class="col-md-2 col-6">
                <label class="form-label small fw-bold mb-1">Latitud:</label>
                <input type="number" step="any" class="form-control" name="lat" id="latInput"
                       value="${ubicacionUsuario.latitud}" required>
            </div>
            <div class="col-md-2 col-6">
                <label class="form-label small fw-bold mb-1">Longitud:</label>
                <input type="number" step="any" class="form-control" name="lng" id="lngInput"
                       value="${ubicacionUsuario.longitud}" required>
            </div>
            <div class="col-md-3 d-grid">
                <label class="form-label small fw-bold mb-1">&nbsp;</label>
                <button type="submit" class="btn btn-success fw-bold shadow-sm">
                    <i class="bi bi-search me-1"></i> Recalcular Rutas
                </button>
            </div>
        </div>
    </form>

    <div class="row g-4">
        <!-- Alternativas de Rutas (mostrarAlternativas) -->
        <div class="col-lg-5">
            <h5 class="fw-bold text-dark mb-3">
                <i class="bi bi-list-stars me-1 text-success"></i> Alternativas de Evacuación Evaluadas
            </h5>

            <c:choose>
                <c:when test="${empty rutasAlternativas}">
                    <div class="card p-4 text-center text-muted">
                        <i class="bi bi-exclamation-circle fs-1 text-warning mb-2"></i>
                        No se encontraron rutas disponibles para la ubicación seleccionada.
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="ruta" items="${rutasAlternativas}" varStatus="status">
                        <div class="card route-card mb-3 border ${rutaSeleccionada != null && rutaSeleccionada.id == ruta.id ? 'route-selected shadow' : 'bg-white shadow-sm'}"
                             onclick="window.location.href='${pageContext.request.contextPath}/evacuacion?lat=${ubicacionUsuario.latitud}&lng=${ubicacionUsuario.longitud}&direccion=${ubicacionUsuario.direccion}&idRuta=${ruta.id}'">
                            <div class="card-body p-3">
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <span class="badge ${status.index == 0 ? 'bg-success' : 'bg-secondary'}">
                                        ${status.index == 0 ? 'Opción Recomendada' : 'Alternativa ' + (status.index + 1)}
                                    </span>
                                    <span class="${ruta.nivelRiesgoBadge} px-2 py-1">
                                        Riesgo: ${ruta.nivelRiesgo} / 10 (${ruta.nivelRiesgoTexto})
                                    </span>
                                </div>

                                <h6 class="fw-bold text-dark mb-1">${ruta.nombre}</h6>
                                <p class="small text-muted mb-2">${ruta.descripcion}</p>

                                <div class="row text-center pt-2 border-top g-2">
                                    <div class="col-6 text-start">
                                        <small class="text-muted d-block">Destino Seguro:</small>
                                        <strong class="text-primary small">
                                            <i class="bi bi-shield-check"></i> ${ruta.zonaSeguraDestino != null ? ruta.zonaSeguraDestino.nombre : 'Zona Segura'}
                                        </strong>
                                    </div>
                                    <div class="col-6 text-end">
                                        <small class="text-muted d-block">Distancia Estimada:</small>
                                        <strong class="text-dark">${ruta.distanciaKm} km</strong>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Trazado en el Mapa (obtenerTrazado) -->
        <div class="col-lg-7">
            <div class="card shadow-sm border-0 bg-white">
                <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0 text-dark">
                        <i class="bi bi-map-fill text-success me-1"></i> Trazado y Puntos de Navegación
                    </h5>
                    <c:if test="${not empty rutaSeleccionada}">
                        <span class="badge bg-success">
                            ${rutaSeleccionada.nombre}
                        </span>
                    </c:if>
                </div>
                <div class="card-body p-2">
                    <div id="routeMap"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp"/>

<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    const userLat = ${ubicacionUsuario.latitud};
    const userLng = ${ubicacionUsuario.longitud};

    const map = L.map('routeMap').setView([userLat, userLng], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    // Marcador de Ubicación del Usuario
    const userIcon = L.divIcon({
        className: 'user-pin',
        html: '<div style="background-color:#0d6efd; color:white; border-radius:50%; width:32px; height:32px; display:flex; align-items:center; justify-content:center; border:2px solid white; box-shadow:0 2px 5px rgba(0,0,0,0.3);"><i class="bi bi-person-fill"></i></div>',
        iconSize: [32, 32],
        iconAnchor: [16, 16]
    });
    L.marker([userLat, userLng], {icon: userIcon})
        .addTo(map)
        .bindPopup("<b>Tu Ubicación</b><br>${ubicacionUsuario.direccion}")
        .openPopup();

    // Marcadores de Incendios en Peligro
    <c:forEach var="inc" items="${incendiosActivos}">
        <c:if test="${inc.ubicacion != null}">
            L.circle([${inc.ubicacion.latitud}, ${inc.ubicacion.longitud}], {
                radius: 600,
                color: '#dc3545',
                fillColor: '#dc3545',
                fillOpacity: 0.25
            }).addTo(map);

            L.circleMarker([${inc.ubicacion.latitud}, ${inc.ubicacion.longitud}], {
                radius: 8,
                fillColor: '#dc3545',
                color: '#fff',
                weight: 2,
                fillOpacity: 0.9
            }).addTo(map).bindPopup("<b>¡PELIGRO! Incendio #${inc.id}</b><br>${inc.ubicacion.direccion}<br>Estado: ${inc.estado.descripcion}");
        </c:if>
    </c:forEach>

    // Marcadores de Zonas Seguras
    <c:forEach var="z" items="${zonasSeguras}">
        <c:if test="${z.ubicacion != null}">
            L.circleMarker([${z.ubicacion.latitud}, ${z.ubicacion.longitud}], {
                radius: 10,
                fillColor: '#198754',
                color: '#fff',
                weight: 2,
                fillOpacity: 0.95
            }).addTo(map).bindPopup("<b>Zona Segura: ${z.nombre}</b><br>Capacidad: ${z.capacidad} personas");
        </c:if>
    </c:forEach>

    // Trazado de la Ruta Seleccionada (obtenerTrazado)
    <c:if test="${not empty rutaSeleccionada && not empty rutaSeleccionada.trazadoJson}">
        try {
            const rawPoints = ${rutaSeleccionada.trazadoJson};
            // Asegurar que el punto de partida del usuario esté conectado
            const routeCoordinates = [[userLat, userLng], ...rawPoints];

            const polyline = L.polyline(routeCoordinates, {
                color: "${rutaSeleccionada.nivelRiesgo <= 3.0 ? '#198754' : (rutaSeleccionada.nivelRiesgo <= 6.0 ? '#ffc107' : '#dc3545')}",
                weight: 6,
                opacity: 0.85,
                dashArray: '8, 8'
            }).addTo(map);

            map.fitBounds(polyline.getBounds(), {padding: [50, 50]});
        } catch (e) {
            console.error("Error al renderizar trazado de ruta: ", e);
        }
    </c:if>
</script>
</body>
</html>
