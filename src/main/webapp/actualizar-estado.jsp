<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar Estado de Incendio (CU2) — AlerFire</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<jsp:include page="/common/navbar.jsp"/>

<div class="container my-4">
    <!-- Encabezado de Caso de Uso -->
    <div class="card shadow-sm border-0 mb-4 bg-white">
        <div class="card-body p-4">
            <div class="d-flex align-items-center mb-2">
                <span class="badge bg-primary fs-6 me-2">Caso de Uso 2 (CU2)</span>
                <span class="badge bg-dark">Rol: Cuerpo de Bomberos</span>
            </div>
            <h2 class="fw-bold text-dark mb-1">
                <i class="bi bi-shield-shaded text-danger me-2"></i> Gestión Operativa: Actualizar Estado de Incendio
            </h2>
            <p class="text-muted mb-0">
                Visualice los reportes de incendios pendientes, evalúe la situación en campo y registre el cambio de estado (En proceso de atención, Bajo control, No controlado o Extinto) con su respectiva bitácora de auditoría.
            </p>
        </div>
    </div>

    <!-- Si se ha seleccionado un incendio para actualizar -->
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
                            <label class="form-label text-muted small fw-bold">REPORTADO POR</label>
                            <p class="mb-0">${incendioSeleccionado.ciudadano != null ? incendioSeleccionado.ciudadano.nombre : 'Ciudadano Anónimo'}</p>
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
                            <div class="form-text">
                                Reglas: "Bajo control" si el fuego no avanza; "No controlado" si requiere refuerzos; "Extinto" al liquidar completamente.
                            </div>
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

    <!-- Lista de Reportes Pendientes de Gestión (Tabla) -->
    <div class="card shadow-sm border-0 bg-white">
        <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
            <h5 class="fw-bold mb-0 text-dark">
                <i class="bi bi-list-task me-2 text-danger"></i> Reportes Pendientes de Atención y Vigilancia
            </h5>
            <span class="badge bg-secondary">${reportesPendientes.size()} reportes en curso</span>
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                    <tr>
                        <th class="ps-4"># ID</th>
                        <th>Fecha / Hora</th>
                        <th>Ubicación / Sector</th>
                        <th>Estado Actual</th>
                        <th>Evidencias</th>
                        <th class="text-end pe-4">Acción</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty reportesPendientes}">
                            <tr>
                                <td colspan="6" class="text-center py-5 text-muted">
                                    <i class="bi bi-shield-check fs-1 text-success d-block mb-2"></i>
                                    No hay incendios pendientes de atención en este momento.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="inc" items="${reportesPendientes}">
                                <tr class="${incendioSeleccionado != null && incendioSeleccionado.id == inc.id ? 'table-primary' : ''}">
                                    <td class="ps-4 fw-bold">#${inc.id}</td>
                                    <td>
                                        <small class="text-muted d-block">
                                            ${inc.fechaHora.toLocalDate()}
                                        </small>
                                        <span class="fw-semibold">
                                            ${inc.fechaHora.toLocalTime().toString().substring(0, 5)}
                                        </span>
                                    </td>
                                    <td>
                                        <div class="fw-semibold text-dark">${inc.ubicacion.direccion}</div>
                                        <small class="text-muted text-truncate d-inline-block" style="max-width: 320px;">
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
                                            <c:when test="${not empty inc.evidencias}">
                                                <a href="${inc.evidencias[0].archivoUrl}" target="_blank" class="btn btn-sm btn-outline-secondary">
                                                    <i class="bi bi-image"></i> Ver Foto (${inc.evidencias.size()})
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted small">Sin adjunto</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-end pe-4">
                                        <a href="${pageContext.request.contextPath}/incendios?id=${inc.id}"
                                           class="btn btn-sm btn-primary px-3 shadow-sm">
                                            <i class="bi bi-pencil-square me-1"></i> Actualizar Estado
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp"/>
</body>
</html>
