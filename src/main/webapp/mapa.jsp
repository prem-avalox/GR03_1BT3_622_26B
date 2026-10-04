<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visualizar Mapa de Incendios en Vivo (CU3) — AlerFire</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        #fullMap {
            height: 600px;
            width: 100%;
            border-radius: 8px;
            background-color: #e5e3df;
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
        <div class="card-body p-3 d-flex flex-wrap justify-content-between align-items-center gap-2">
            <div>
                <div class="d-flex align-items-center gap-2 mb-1">
                    <span class="badge bg-warning text-dark">Caso de Uso 3 (CU3)</span>
                    <span class="badge bg-secondary">Rol: Ciudadano / Público</span>
                    <span class="badge bg-danger">${incendiosActivos.size()} focos activos</span>
                    <span class="badge bg-success">${zonasSeguras.size()} zonas seguras</span>
                </div>
                <h4 class="fw-bold text-danger mb-0">
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
                <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                    <h5 class="fw-bold mb-0 text-dark">
                        <i class="bi bi-info-circle-fill text-danger me-2"></i> Detalle del Foco Seleccionado
                    </h5>
                    <button type="button" id="btnCerrarDetalle" class="btn btn-sm btn-outline-secondary d-none" onclick="limpiarDetalle()">
                        <i class="bi bi-x"></i> Cerrar
                    </button>
                </div>
                <div class="card-body p-3 overflow-auto" id="panelDetalleContenedor" style="max-height: 540px;">
                    <!-- Estado Inicial Vacío -->
                    <div id="estadoVacio" class="text-center py-5 text-muted">
                        <i class="bi bi-cursor-fill fs-1 text-danger d-block mb-2"></i>
                        <h6 class="fw-bold text-dark">Selecciona un foco en el mapa</h6>
                        <p class="small text-secondary mb-3">
                            Haz clic sobre cualquier círculo de incendio en Quito para cargar al instante su ficha técnica, fotos reales y bitácora de los bomberos.
                        </p>
                        <span class="badge bg-light text-dark border">
                            ${incendiosActivos.size()} focos activos disponibles
                        </span>
                    </div>

                    <!-- Contenedor Dinámico de Información -->
                    <div id="contenidoFoco" class="d-none">
                        <div class="mb-3">
                            <span id="focoBadge" class="badge fs-6 px-3 py-1 mb-2 d-inline-block"></span>
                            <h5 id="focoTitulo" class="fw-bold text-dark mb-1"></h5>
                            <p class="text-muted small mb-0">
                                <i class="bi bi-geo-alt me-1 text-danger"></i><span id="focoDireccion"></span>
                            </p>
                            <p class="text-muted small mb-0">
                                <i class="bi bi-compass me-1"></i><span id="focoCoord"></span>
                            </p>
                            <p class="text-muted small">
                                <i class="bi bi-clock me-1"></i><span id="focoFecha"></span>
                            </p>
                        </div>

                        <div class="mb-3">
                            <h6 class="fw-bold text-secondary small text-uppercase mb-1">Situación Reportada</h6>
                            <p id="focoDescripcion" class="small bg-light p-3 rounded border text-dark"></p>
                        </div>

                        <div id="seccionEvidencia" class="mb-3 d-none">
                            <h6 class="fw-bold text-secondary small text-uppercase mb-1">Evidencia Visual en Quito</h6>
                            <img id="focoImagen" src="" class="img-fluid rounded border shadow-sm mb-1 w-100"
                                 style="max-height: 220px; object-fit: cover;" alt="Evidencia de Incendio">
                            <small id="focoEvidenciaTipo" class="text-muted d-block"></small>
                        </div>

                        <div id="seccionHistorial" class="mb-3 d-none">
                            <h6 class="fw-bold text-secondary small text-uppercase mb-2">Bitácora de Bomberos</h6>
                            <div id="focoHistorialLista" class="list-group list-group-flush border rounded"></div>
                        </div>

                        <a id="btnEvacuarDesdeFoco" href="#" class="btn btn-outline-success w-100 btn-sm">
                            <i class="bi bi-compass-fill me-1"></i> Calcular Ruta de Evacuación desde este punto
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Elementos Ocultos con Datos Seguros para JavaScript (Sin errores de EL en JSP) -->
<div id="incendiosDataDom" class="d-none">
    <c:forEach var="inc" items="${incendiosActivos}">
        <div id="inc-data-${inc.id}"
             data-id="${inc.id}"
             data-estado="${inc.estado.descripcion}"
             data-badge="${inc.estado.badgeClass}"
             data-direccion="<c:out value='${inc.ubicacion.direccion}'/>"
             data-lat="${inc.ubicacion.latitud}"
             data-lng="${inc.ubicacion.longitud}"
             data-fecha="${inc.fechaHora.toLocalDate()} a las ${inc.fechaHora.toLocalTime().toString().substring(0, 5)}"
             data-evidencia="${not empty inc.evidencias ? inc.evidencias[0].archivoUrl : ''}"
             data-evidenciatipo="${not empty inc.evidencias ? inc.evidencias[0].tipo.descripcion : ''}">
            <div class="desc-text"><c:out value="${inc.descripcion}"/></div>
            <div class="historial-items">
                <c:forEach var="h" items="${inc.historial}">
                    <div class="hist-item"
                         data-bombero="<c:out value='${h.bombero != null ? h.bombero.nombre : \"Bombero de Guardia\"}'/>"
                         data-hora="${h.fechaHora.toLocalTime().toString().substring(0, 5)}">
                        <c:out value="${h.observacion}"/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="/common/footer.jsp"/>

<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    const contextPath = "${pageContext.request.contextPath}";

    // Función del Diagrama de Secuencia: mostrarDetalleIncendio(incendio)
    function mostrarDetalleIncendio(id) {
        const dom = document.getElementById('inc-data-' + id);
        if (!dom) return;

        document.getElementById('estadoVacio').classList.add('d-none');
        document.getElementById('contenidoFoco').classList.remove('d-none');
        document.getElementById('btnCerrarDetalle').classList.remove('d-none');

        const estado = dom.dataset.estado;
        const badgeClass = dom.dataset.badge;
        const direccion = dom.dataset.direccion;
        const lat = dom.dataset.lat;
        const lng = dom.dataset.lng;
        const fecha = dom.dataset.fecha;
        const descEl = dom.querySelector('.desc-text');
        const desc = descEl ? descEl.innerText : '';
        const evUrl = dom.dataset.evidencia;
        const evTipo = dom.dataset.evidenciatipo;

        document.getElementById('focoTitulo').innerText = "Incendio #" + id;
        document.getElementById('focoBadge').className = badgeClass + " fs-6 px-3 py-1 mb-2 d-inline-block";
        document.getElementById('focoBadge').innerText = estado;
        document.getElementById('focoDireccion').innerText = direccion;
        document.getElementById('focoCoord').innerText = "Coord: " + lat + ", " + lng;
        document.getElementById('focoFecha').innerText = fecha;
        document.getElementById('focoDescripcion').innerText = desc;

        // Evidencia
        const seccionEv = document.getElementById('seccionEvidencia');
        if (evUrl && evUrl.trim() !== '') {
            const urlFinal = (evUrl.startsWith('http://') || evUrl.startsWith('https://'))
                ? evUrl
                : contextPath + "/" + evUrl;
            document.getElementById('focoImagen').src = urlFinal;
            document.getElementById('focoEvidenciaTipo').innerText = "Tipo: " + (evTipo || "Fotografía");
            seccionEv.classList.remove('d-none');
        } else {
            seccionEv.classList.add('d-none');
        }

        // Historial
        const seccionH = document.getElementById('seccionHistorial');
        const listaH = document.getElementById('focoHistorialLista');
        listaH.innerHTML = "";
        const histElements = dom.querySelectorAll('.hist-item');
        if (histElements.length > 0) {
            histElements.forEach(h => {
                const item = document.createElement('div');
                item.className = "list-group-item p-2";
                item.innerHTML = `
                    <div class="d-flex justify-content-between small text-muted">
                        <span>\${h.dataset.bombero}</span>
                        <span>\${h.dataset.hora}</span>
                    </div>
                    <p class="small mb-0 text-dark fw-semibold mt-1">\${h.innerText}</p>
                `;
                listaH.appendChild(item);
            });
            seccionH.classList.remove('d-none');
        } else {
            seccionH.classList.add('d-none');
        }

        // Botón de ruta de evacuación
        document.getElementById('btnEvacuarDesdeFoco').href =
            contextPath + "/evacuacion?lat=" + lat + "&lng=" + lng + "&direccion=" + encodeURIComponent(direccion);
    }

    function limpiarDetalle() {
        document.getElementById('contenidoFoco').classList.add('d-none');
        document.getElementById('btnCerrarDetalle').classList.add('d-none');
        document.getElementById('estadoVacio').classList.remove('d-none');
    }

    document.addEventListener("DOMContentLoaded", function () {
        const map = L.map('fullMap', {
            center: [-0.180653, -78.467838],
            zoom: 12,
            scrollWheelZoom: true
        });

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; OpenStreetMap contributors'
        }).addTo(map);

        setTimeout(function () {
            map.invalidateSize();
        }, 200);

        window.addEventListener('resize', function () {
            map.invalidateSize();
        });

        // Leyenda flotante
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

        // Zonas Seguras
        <c:forEach var="z" items="${zonasSeguras}">
            <c:if test="${z.ubicacion != null}">
                L.circle([${z.ubicacion.latitud}, ${z.ubicacion.longitud}], {
                    radius: 400,
                    color: '#198754',
                    fillColor: '#198754',
                    fillOpacity: 0.2
                }).addTo(map);

                L.circleMarker([${z.ubicacion.latitud}, ${z.ubicacion.longitud}], {
                    radius: 8,
                    fillColor: '#198754',
                    color: '#ffffff',
                    weight: 2,
                    opacity: 1,
                    fillOpacity: 0.95
                }).addTo(map).bindPopup("<b>Zona Segura: <c:out value='${z.nombre}'/></b><br>Tipo: ${z.tipo}<br>Capacidad: ${z.capacidad} personas");
            </c:if>
        </c:forEach>

        // Marcadores de Incendios
        <c:forEach var="inc" items="${incendiosActivos}">
            <c:if test="${inc.ubicacion != null}">
                const color_${inc.id} = "${inc.estado == 'NO_CONTROLADO' ? '#dc3545' : (inc.estado == 'EN_PROCESO_DE_ATENCION' ? '#ffc107' : '#0dcaf0')}";
                const circle_${inc.id} = L.circleMarker([${inc.ubicacion.latitud}, ${inc.ubicacion.longitud}], {
                    radius: 13,
                    fillColor: color_${inc.id},
                    color: "#ffffff",
                    weight: 3,
                    opacity: 1,
                    fillOpacity: 0.9
                }).addTo(map);

                // Al hacer clic en el círculo, abre popup Y actualiza el panel lateral de inmediato
                circle_${inc.id}.on('click', function () {
                    mostrarDetalleIncendio(${inc.id});
                });

                circle_${inc.id}.bindPopup(`
                    <div style="min-width: 200px;">
                        <h6 class="fw-bold mb-1">Incendio #${inc.id}</h6>
                        <span class="badge ${inc.estado == 'NO_CONTROLADO' ? 'bg-danger' : 'bg-warning text-dark'}">${inc.estado.descripcion}</span>
                        <p class="small mt-2 mb-2 text-dark"><c:out value="${inc.ubicacion.direccion}"/></p>
                        <button type="button" onclick="mostrarDetalleIncendio(${inc.id})" class="btn btn-sm btn-primary text-white w-100">
                            <i class="bi bi-eye"></i> Ver Detalles Completos
                        </button>
                    </div>
                `);

                // Si viene preseleccionado desde URL
                <c:if test="${incendioDetalle != null && incendioDetalle.id == inc.id}">
                    circle_${inc.id}.openPopup();
                    map.setView([${inc.ubicacion.latitud}, ${inc.ubicacion.longitud}], 14);
                    mostrarDetalleIncendio(${inc.id});
                </c:if>
            </c:if>
        </c:forEach>
    });
</script>
</body>
</html>
