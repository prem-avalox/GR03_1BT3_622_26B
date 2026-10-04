<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reportar Incendio (CU1) — AlerFire</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        #mapPicker {
            height: 320px;
            border-radius: 8px;
            cursor: crosshair;
        }
    </style>
</head>
<body class="bg-light">

<jsp:include page="/common/navbar.jsp"/>

<div class="container my-4">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <!-- Encabezado de Caso de Uso -->
            <div class="card shadow-sm border-0 mb-4 bg-white">
                <div class="card-body p-4">
                    <div class="d-flex align-items-center mb-2">
                        <span class="badge bg-danger fs-6 me-2">Caso de Uso 1 (CU1)</span>
                        <span class="badge bg-secondary">Rol: Ciudadano</span>
                    </div>
                    <h2 class="fw-bold text-danger mb-1">
                        <i class="bi bi-megaphone-fill me-2"></i> Reportar Conato o Incendio Forestal
                    </h2>
                    <p class="text-muted mb-0">
                        Proporcione la descripción de la situación observada, adjunte evidencia visual (fotografía o video) e indique la ubicación en el mapa. El sistema notificará de inmediato a las unidades del Cuerpo de Bomberos y a la población circundante.
                    </p>
                </div>
            </div>

            <!-- Formulario de Reporte -->
            <form action="${pageContext.request.contextPath}/reportar" method="POST" class="card shadow-sm border-0 bg-white p-4">
                <div class="row g-4">
                    <!-- Columna Izquierda: Información y Evidencia -->
                    <div class="col-md-6">
                        <h5 class="fw-bold text-dark border-bottom pb-2 mb-3">
                            <i class="bi bi-card-text me-1 text-danger"></i> 1. Información del Evento
                        </h5>

                        <div class="mb-3">
                            <label for="descripcion" class="form-label fw-semibold">
                                Descripción de la situación observada <span class="text-danger">*</span>
                            </label>
                            <textarea class="form-control" id="descripcion" name="descripcion" rows="4" required
                                      placeholder="Describa el tamaño de las llamas, color del humo, propagación y si hay viviendas o estructuras cercanas en peligro..."></textarea>
                            <div class="form-text">Mínimo 5 caracteres para validación del reporte.</div>
                        </div>

                        <h5 class="fw-bold text-dark border-bottom pb-2 mb-3 mt-4">
                            <i class="bi bi-camera-fill me-1 text-danger"></i> 2. Adjuntar Evidencia Visual
                        </h5>

                        <div class="mb-3">
                            <label for="tipoEvidencia" class="form-label fw-semibold">Tipo de Evidencia</label>
                            <select class="form-select" id="tipoEvidencia" name="tipoEvidencia">
                                <option value="FOTO" selected>Fotografía (.jpg, .png)</option>
                                <option value="VIDEO">Video (.mp4)</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="archivoUrl" class="form-label fw-semibold">URL o Enlace de la Fotografía/Video</label>
                            <input type="text" class="form-control" id="archivoUrl" name="archivoUrl"
                                   placeholder="https://ejemplo.com/evidencia.jpg o /uploads/foto1.png"
                                   value="images/incendio-guapulo.jpg">
                            <div class="form-text">
                                Ingrese una URL de imagen o deje el enlace de prueba predeterminado.
                            </div>
                        </div>
                    </div>

                    <!-- Columna Derecha: Ubicación y Selector GPS -->
                    <div class="col-md-6">
                        <h5 class="fw-bold text-dark border-bottom pb-2 mb-3">
                            <i class="bi bi-geo-alt-fill me-1 text-danger"></i> 3. Indicar Ubicación del Incendio
                        </h5>

                        <div class="mb-3">
                            <label for="direccion" class="form-label fw-semibold">Referencia o Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" required
                                   value="${ubicacionGPS != null ? ubicacionGPS.direccion : 'Sector La Carolina, Quito'}">
                        </div>

                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label for="latitud" class="form-label small fw-semibold">Latitud</label>
                                <input type="number" step="any" class="form-control form-control-sm" id="latitud" name="latitud" required
                                       value="${ubicacionGPS != null ? ubicacionGPS.latitud : -0.180653}">
                            </div>
                            <div class="col-6">
                                <label for="longitud" class="form-label small fw-semibold">Longitud</label>
                                <input type="number" step="any" class="form-control form-control-sm" id="longitud" name="longitud" required
                                       value="${ubicacionGPS != null ? ubicacionGPS.longitud : -78.467838}">
                            </div>
                        </div>

                        <div class="mb-2">
                            <label class="form-label small text-muted">
                                <i class="bi bi-hand-index-thumb"></i> Haga clic sobre el mapa para ajustar el punto exacto:
                            </label>
                            <div id="mapPicker"></div>
                        </div>
                    </div>
                </div>

                <hr class="my-4">

                <div class="d-flex justify-content-between align-items-center">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Cancelar y Volver
                    </a>
                    <button type="submit" class="btn btn-danger btn-lg px-4 shadow">
                        <i class="bi bi-send-fill me-2"></i> Enviar Reporte de Incendio
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp"/>

<!-- Leaflet JS -->
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    const defaultLat = parseFloat(document.getElementById('latitud').value) || -0.180653;
    const defaultLng = parseFloat(document.getElementById('longitud').value) || -78.467838;

    const map = L.map('mapPicker').setView([defaultLat, defaultLng], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    let marker = L.marker([defaultLat, defaultLng], {draggable: true}).addTo(map);

    function updateInputs(lat, lng) {
        document.getElementById('latitud').value = lat.toFixed(6);
        document.getElementById('longitud').value = lng.toFixed(6);
    }

    marker.on('dragend', function (e) {
        const pos = e.target.getLatLng();
        updateInputs(pos.lat, pos.lng);
    });

    map.on('click', function (e) {
        marker.setLatLng(e.latlng);
        updateInputs(e.latlng.lat, e.latlng.lng);
    });
</script>
</body>
</html>
