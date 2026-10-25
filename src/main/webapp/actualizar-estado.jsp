<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Módulo de Despacho Bomberil y Gestión de Estados (HU-01, HU-02, HU-03) — AlerFire</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<jsp:include page="/common/navbar.jsp"/>

<div class="container my-4">
    <!-- Mensajes Flash de Confirmación / Rechazo de Criterios de Aceptación -->
    <c:if test="${not empty sessionScope.mensajeExito}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm border-success" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>
            <strong>Confirmación Operativa:</strong> ${sessionScope.mensajeExito}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="mensajeExito" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.mensajeError}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm border-danger" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
            <strong>Alerta de Validación:</strong> ${sessionScope.mensajeError}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="mensajeError" scope="session"/>
    </c:if>

    <!-- Encabezado de Incremento XP -->
    <div class="card shadow-sm border-0 mb-4 bg-white">
        <div class="card-body p-4">
            <div class="d-flex flex-wrap gap-2 align-items-center mb-2">
                <span class="badge bg-danger fs-6">Incremento XP: Despacho Bomberil</span>
                <span class="badge bg-primary">HU-01: Despacho de Estación</span>
                <span class="badge bg-success">HU-02: Liquidación y Liberación</span>
                <span class="badge bg-warning text-dark">HU-03: Estimación de ETA</span>
            </div>
            <h2 class="fw-bold text-dark mb-1">
                <i class="bi bi-truck-flatbed text-danger me-2"></i> Centro de Comando: Despacho de Estaciones y Control de Emergencias
            </h2>
            <p class="text-muted mb-0">
                Consulte en tiempo real la disponibilidad operativa y el Tiempo Estimado de Arribo (ETA) de cada cuartel (HU-03), despache estaciones disponibles a conatos reportados (HU-01) y liquide emergencias liberando automáticamente los recursos (HU-02).
            </p>
        </div>
    </div>

    <!-- ========================================================================= -->
    <!-- PANEL HU-03: TABLA DE ESTACIONES DE BOMBEROS Y ESTIMACIÓN DE ARRIBO (ETA) -->
    <!-- ========================================================================= -->
    <div class="card shadow-sm border-0 mb-4 bg-white">
        <div class="card-header bg-dark text-white py-3 d-flex justify-content-between align-items-center">
            <h5 class="fw-bold mb-0">
                <i class="bi bi-speedometer2 me-2 text-warning"></i> Cuarteles de Bomberos: Disponibilidad y Estimación de Tiempo de Arribo (HU-03)
            </h5>
            <span class="badge bg-warning text-dark">Velocidad Urbana Promedio: 40 km/h + 2 min alistamiento</span>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                    <tr>
                        <th class="ps-4">Código</th>
                        <th>Estación de Bomberos</th>
                        <th>Distancia Referencial</th>
                        <th>Tiempo Estimado de Arribo (ETA)</th>
                        <th>Clasificación de Respuesta (HU-03)</th>
                        <th class="text-end pe-4">Disponibilidad Operativa</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="est" items="${estacionesBomberos}">
                        <tr>
                            <td class="ps-4 fw-bold text-primary">${est.codigo}</td>
                            <td>
                                <div class="fw-semibold text-dark">${est.nombre}</div>
                                <small class="text-muted">${est.ubicacion != null ? est.ubicacion.direccion : 'Quito DM'}</small>
                            </td>
                            <td>
                                <span class="badge bg-light text-dark border fs-6">${est.distanciaKmReferencia} km</span>
                            </td>
                            <td>
                                <span class="fw-bold fs-5 ${est.respuestaExtendida ? 'text-danger' : 'text-dark'}">
                                    <i class="bi bi-clock-history me-1"></i> ${est.etaMinutos} min
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${est.distanciaKmReferencia <= 0.5}">
                                        <span class="badge bg-info text-dark px-3 py-2">
                                            <i class="bi bi-lightning-charge-fill me-1"></i> Escenario 2: Proximidad Inmediata (ETA Mínimo 3 min)
                                        </span>
                                    </c:when>
                                    <c:when test="${est.respuestaExtendida}">
                                        <span class="badge bg-warning text-dark border border-danger px-3 py-2">
                                            <i class="bi bi-exclamation-octagon-fill text-danger me-1"></i> Escenario 3: Respuesta Extendida (&gt; 15 min)
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-primary px-3 py-2">
                                            <i class="bi bi-check2-circle me-1"></i> Escenario 1: Respuesta Urbana Estándar (&le; 15 min)
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-end pe-4">
                                <c:choose>
                                    <c:when test="${est.disponible}">
                                        <span class="badge bg-success px-3 py-2 fs-6">
                                            <i class="bi bi-check-circle me-1"></i> DISPONIBLE
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger px-3 py-2 fs-6">
                                            <i class="bi bi-slash-circle me-1"></i> OCUPADA (EN ATENCIÓN)
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Si se ha seleccionado un incendio para actualizar manualmente (CU2) -->
    <c:if test="${not empty incendioSeleccionado}">
        <div class="card border-primary shadow-sm mb-4 bg-white">
            <div class="card-header bg-primary text-white py-3">
                <h5 class="mb-0 fw-bold">
                    <i class="bi bi-pencil-square me-2"></i> Actualizando Incendio #${incendioSeleccionado.id}
                </h5>
            </div>
            <div class="card-body p-4">
                <form action="${pageContext.request.contextPath}/incendios" method="POST">
                    <input type="hidden" name="idIncendio" value="${incendioSeleccionado.id}">

                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label class="form-label text-muted small fw-bold">UBICACIÓN REGISTRADA</label>
                            <p class="fw-semibold mb-0">${incendioSeleccionado.ubicacion.direccion}</p>
                            <small class="text-muted">Coord: ${incendioSeleccionado.ubicacion.latitud}, ${incendioSeleccionado.ubicacion.longitud}</small>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label text-muted small fw-bold">ESTADO ACTUAL</label>
                            <div>
                                <span class="${incendioSeleccionado.estado.badgeClass} fs-6 px-3 py-1">
                                    ${incendioSeleccionado.estado.descripcion}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label text-muted small fw-bold">ESTACIÓN ASIGNADA</label>
                            <p class="mb-0 fw-semibold">
                                ${incendioSeleccionado.estacionAsignada != null ? incendioSeleccionado.estacionAsignada.nombre : 'Sin estación asignada'}
                            </p>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label text-muted small fw-bold">DESCRIPCIÓN DEL REPORTE</label>
                        <div class="p-3 bg-light rounded border text-secondary">${incendioSeleccionado.descripcion}</div>
                    </div>

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="nuevoEstado" class="form-label fw-bold text-dark">
                                Seleccionar Nuevo Estado de Operación <span class="text-danger">*</span>
                            </label>
                            <select class="form-select form-select-lg" id="nuevoEstado" name="nuevoEstado" required>
                                <c:forEach var="est" items="${estadosDisponibles}">
                                    <option value="${est.name()}" ${est == incendioSeleccionado.estado ? 'selected' : ''}>
                                        ${est.descripcion}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="observacion" class="form-label fw-bold text-dark">
                                Observaciones de la Cuadrilla / Justificación
                            </label>
                            <textarea class="form-control" id="observacion" name="observacion" rows="3"
                                      placeholder="Detalle unidades en el sitio, uso de agua/espuma, condiciones de viento, etc."></textarea>
                        </div>
                    </div>

                    <div class="mt-4 d-flex justify-content-between align-items-center">
                        <a href="${pageContext.request.contextPath}/incendios" class="btn btn-outline-secondary">
                            Cancelar Edición
                        </a>
                        <button type="submit" class="btn btn-primary btn-lg px-4 shadow">
                            <i class="bi bi-check2-circle me-1"></i> Confirmar y Notificar Cambio de Estado
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </c:if>

    <!-- ================================================================================= -->
    <!-- PANEL HU-01 Y HU-02: TABLA DE INCENDIOS CON CONTROLES DE DESPACHO Y LIQUIDACIÓN   -->
    <!-- ================================================================================= -->
    <div class="card shadow-sm border-0 bg-white">
        <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
            <h5 class="fw-bold mb-0 text-dark">
                <i class="bi bi-fire me-2 text-danger"></i> Operaciones de Despacho (HU-01) y Liquidación con Liberación de Cuartel (HU-02)
            </h5>
            <span class="badge bg-secondary">${reportesPendientes.size()} emergencias registradas</span>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                    <tr>
                        <th class="ps-4"># ID</th>
                        <th>Ubicación / Conato</th>
                        <th>Estado Actual</th>
                        <th>Estación Asignada</th>
                        <th>Despachar Estación (HU-01)</th>
                        <th class="text-end pe-4">Liquidar / Actualizar (HU-02)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="inc" items="${reportesPendientes}">
                        <tr class="${incendioSeleccionado != null && incendioSeleccionado.id == inc.id ? 'table-primary' : ''}">
                            <td class="ps-4 fw-bold">#${inc.id}</td>
                            <td>
                                <div class="fw-semibold text-dark">${inc.ubicacion.direccion}</div>
                                <small class="text-muted d-block" style="max-width: 280px;">
                                    ${inc.descripcion}
                                </small>
                            </td>
                            <td>
                                <span class="${inc.estado.badgeClass} px-3 py-1">
                                    ${inc.estado.descripcion}
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${inc.estacionAsignada != null}">
                                        <span class="badge bg-dark px-2 py-1">
                                            <i class="bi bi-building-check me-1"></i> ${inc.estacionAsignada.codigo} — ${inc.estacionAsignada.nombre}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted small fst-italic">Sin estación asignada</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <!-- Formulario HU-01: Despachar Estación -->
                                <form action="${pageContext.request.contextPath}/incendios" method="POST" class="d-flex gap-1 align-items-center">
                                    <input type="hidden" name="accion" value="despachar">
                                    <input type="hidden" name="idIncendio" value="${inc.id}">
                                    <select name="idEstacion" class="form-select form-select-sm" style="min-width: 210px;" required>
                                        <c:forEach var="est" items="${estacionesBomberos}">
                                            <option value="${est.id}">
                                                ${est.codigo} - ${est.nombre} (ETA: ${est.etaMinutos}m | ${est.disponible ? 'DISP' : 'OCUPADA'})
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <button type="submit" class="btn btn-sm btn-danger text-nowrap shadow-sm">
                                        <i class="bi bi-send-fill me-1"></i> Despachar
                                    </button>
                                </form>
                            </td>
                            <td class="text-end pe-4">
                                <div class="d-flex justify-content-end gap-1">
                                    <!-- Formulario HU-02: Liquidar Emergencia y Liberar Estación -->
                                    <form action="${pageContext.request.contextPath}/incendios" method="POST" class="d-inline">
                                        <input type="hidden" name="accion" value="liquidar">
                                        <input type="hidden" name="idIncendio" value="${inc.id}">
                                        <button type="submit" class="btn btn-sm btn-success text-nowrap shadow-sm">
                                            <i class="bi bi-droplet-fill me-1"></i> Liquidar y Liberar
                                        </button>
                                    </form>
                                    <a href="${pageContext.request.contextPath}/incendios?id=${inc.id}"
                                       class="btn btn-sm btn-outline-primary px-2 shadow-sm" title="Editar estado manual">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap 5 JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>
