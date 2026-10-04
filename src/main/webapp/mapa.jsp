<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visualizar Mapa de Incendios en Vivo (CU3) — FireAlert</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        #fullMap {
            height: 600px;
            border-radius: 8px;
        }
        .legend-box {
            background: rgba(255, 255, 255, 0.95);
            padding: 10px 15px;
            border-radius: 6px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
            font-size: 13px;
        }
        .legend-item {
            display: flex;
            align-items: center;
            margin-bottom: 5px;
        }
        .legend-color {
            width: 14px;
            height: 14px;
            border-radius: 50%;
            margin-right: 8px;
            display: inline-block;
        }
    </style>
</head>
<body class="bg-light">

<jsp:include page="/common/navbar.jsp"/>

<div class="container-fluid px-4 my-4">
    <!-- Encabezado de Caso de Uso -->
    <div class="card shadow-sm border-0 mb-3 bg-white">
        <div class="card-body p-3 d-flex justify-content-between align-items-center">
            <div>
                <span class="badge bg-warning text-dark me-2">Caso de Uso 3 (CU3)</span>
                <span class="badge bg-secondary">Rol: Ciudadano / Público</span>
                <h4 class="fw-bold text-danger mb-0 mt-1">
                    <i class="bi bi-geo-alt-fill me-1"></i> Monitoreo Geoespacial de Incendios y Zonas Seguras en Quito
                </h4>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/reportar" class="btn btn-danger btn-sm shadow-sm me-2">
                    <i class="bi bi-plus-circle me-1"></i> Nuevo Reporte (CU1)
                </a>
                <a href="${pageContext.request.contextPath}/evacuacion" class="btn btn-success btn-sm shadow-sm">
                    <i class="bi bi-signpost-2-fill me-1"></i> Evacuar (CU4)
                </a>
            </div>
        </div>
    </div>

    <div class="row g-3">
        <!-- Mapa Principal -->
        <div class="col-lg-8">
            <div class="card shadow-sm border-0 bg-white">
                <div class="card-body p-2 position-relative">
                    <div id="fullMap"></div>
                </div>
            </div>
        </div>

        <!-- Panel Lateral de Detalles (mostrarDetalleIncendio) -->
        <div class="col-lg-4">
            <div class="card shadow-sm border-0 bg-white h-100">
                <div class="card-header bg-white py-3 border-bottom">
                    <h5 class="fw-bold mb-0 text-dark">
                        <i class="bi bi-info-circle-fill text-danger me-2"></i> Detalle del Foco Seleccionado
                    </h5>
                </div>
                <div class="card-body p-3 overflow-auto" style="max-height: 540px;">
                    <c:choose>
                        <c:when test="${not empty incendioDetalle}">
                            <div class="mb-3">
                                <span class="${incendioDetalle.estado.badgeClass} fs-6 px-3 py-1 mb-2 d-inline-block">
                                    ${incendioDetalle.estado.descripcion}
                                </span>
                                <h5 class="fw-bold text-dark mb-1">Incendio #${incendioDetalle.id}</h5>
                                <p class="text-muted small mb-0">
                                    <i class="bi bi-geo-alt me-1"></i>${incendioDetalle.ubicacion.direccion}
                                </p>
                                <p class="text-muted small">
                                    <i class="bi bi-clock me-1"></i>${incendioDetalle.fechaHora.toLocalDate()} a las ${incendioDetalle.fechaHora.toLocalTime().toString().substring(0, 5)}
                                </p>
                            </div>

                            <div class="mb-3">
                                <h6 class="fw-bold text-secondary small text-uppercase mb-1">Situación Reportada</h6>
                                <p class="small bg-light p-3 rounded border text-dark">${incendioDetalle.descripcion}</p>
                            </div>

                            <c:if test="${not empty incendioDetalle.evidencias}">
                                <div class="mb-3">
                                    <h6 class="fw-bold text-secondary small text-uppercase mb-1">Evidencia Visual</h6>
                                    <img src="${incendioDetalle.evidencias[0].archivoUrl}" class="img-fluid rounded border shadow-sm mb-1" alt="Evidencia">
                                    <small class="text-muted d-block">Tipo: ${incendioDetalle.evidencias[0].tipo.descripcion}</small>
                                </div>
                            </c:if>

                            <c:if test="${not empty incendioDetalle.historial}">
                                <div class="mb-3">
                                    <h6 class="fw-bold text-secondary small text-uppercase mb-2">Bitácora de Bomberos</h6>
                                    <div class="list-group list-group-flush border rounded">
                                        <c:forEach var="h" items="${incendioDetalle.historial}">
                                            <div class="list-group-item p-2">
                                                <div class="d-flex justify-content-between small text-muted">
                                                    <span>${h.bombero != null ? h.bombero.nombre : 'Bombero de Guardia'}</span>
                                                    <span>${h.fechaHora.toLocalTime().toString().substring(0, 5)}</span>
                                                </div>
                                                <p class="small mb-0 text-dark fw-semibold mt-1">${h.observacion}</p>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>

                            <a href="${pageContext.request.contextPath}/evacuacion?lat=${incendioDetalle.ubicacion.latitud}&lng=${incendioDetalle.ubicacion.longitud}"
                               class="btn btn-outline-success w-100 btn-sm">
                                <i class="bi bi-compass-fill me-1"></i> Calcular Ruta de Evacuación desde este punto
                            </a>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5 text-muted">
                                <i class="bi bi-cursor-fill fs-1 text-secondary d-block mb-2"></i>
                                <p class="fw-semibold">Haga clic sobre cualquier marcador en el mapa para ver la información y estado en tiempo real.</p>
                                <span class="badge bg-light text-dark border">
                                    ${incendiosActivos.size()} focos activos disponibles
                                </span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp"/>

<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    const map = L.map('fullMap').setView([-0.180653, -78.467838], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    // Leyenda flotante en el mapa
    const legend = L.control({position: 'bottomleft'});
    legend.onAdd = function () {
        const div = L.DomUtil.create('div', 'legend-box');
        div.innerHTML = `
            <strong>Simbología</strong><br>
            <div class="legend-item"><span class="legend-color" style="background:#dc3545;"></span> No Controlado</div>
            <div class="legend-item"><span class="legend-color" style="background:#ffc107;"></span> En Proceso de Atención</div>
            <div class="legend-item"><span class="legend-color" style="background:#0dcaf0;"></span> Bajo Control</div>
            <div class="legend-item"><span class="legend-color" style="background:#198754;"></span> Zona Segura / Refugio</div>
        `;
        return div;
    };
    legend.addTo(map);

    // Dibujar Zonas Seguras
    <c:forEach var="z" items="${zonasSeguras}">
        <c:if test="${z.ubicacion != null}">
            L.circle([${z.ubicacion.latitud}, ${z.ubicacion.longitud}], {
                radius: 400,
                color: '#198754',
                fillColor: '#198754',
                fillOpacity: 0.25
            }).addTo(map);

            L.marker([${z.ubicacion.latitud}, ${z.ubicacion.longitud}], {
                title: "${z.nombre}"
            }).addTo(map).bindPopup("<b>Zona Segura: ${z.nombre}</b><br>Tipo: ${z.tipo}<br>Capacidad estimada: ${z.capacidad} personas");
        </c:if>
    </c:forEach>

    // Dibujar Marcadores de Incendios
    <c:forEach var="inc" items="${incendiosActivos}">
        <c:if test="${inc.ubicacion != null}">
            const color = "${inc.estado == 'NO_CONTROLADO' ? '#dc3545' : (inc.estado == 'EN_PROCESO_DE_ATENCION' ? '#ffc107' : '#0dcaf0')}";
            const circle = L.circleMarker([${inc.ubicacion.latitud}, ${inc.ubicacion.longitud}], {
                radius: 12,
                fillColor: color,
                color: "#ffffff",
                weight: 3,
                opacity: 1,
                fillOpacity: 0.9
            }).addTo(map);

            circle.bindPopup(`
                <div style="min-width: 200px;">
                    <h6 class="fw-bold mb-1">Incendio #${inc.id}</h6>
                    <span class="badge ${inc.estado == 'NO_CONTROLADO' ? 'bg-danger' : 'bg-warning text-dark'}">${inc.estado.descripcion}</span>
                    <p class="small mt-2 mb-2">${inc.ubicacion.direccion}</p>
                    <a href="${pageContext.request.contextPath}/mapa?idSeleccionado=${inc.id}" class="btn btn-sm btn-primary text-white w-100">
                        <i class="bi bi-eye"></i> Ver Detalles Completos
                    </a>
                </div>
            `);

            <c:if test="${incendioDetalle != null && incendioDetalle.id == inc.id}">
                circle.openPopup();
                map.setView([${inc.ubicacion.latitud}, ${inc.ubicacion.longitud}], 14);
            </c:if>
        </c:if>
    </c:forEach>
</script>
</body>
</html>
