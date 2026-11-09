<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Perfil Cívico e Historial (CU6 / HU-I1-03) — AlerFire</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .badge-oro {
            background: linear-gradient(135deg, #d4af37, #f39c12);
            color: #fff;
            box-shadow: 0 2px 4px rgba(212, 175, 55, 0.4);
        }
        .badge-plata {
            background: linear-gradient(135deg, #7f8c8d, #bdc3c7);
            color: #2c3e50;
            box-shadow: 0 2px 4px rgba(127, 140, 141, 0.3);
        }
        .badge-bronce {
            background: linear-gradient(135deg, #cd7f32, #b87333);
            color: #fff;
            box-shadow: 0 2px 4px rgba(205, 127, 50, 0.3);
        }
    </style>
</head>
<body class="bg-light">

<jsp:include page="/common/navbar.jsp"/>

<div class="container my-4">

    <!-- Encabezado de Caso de Uso -->
    <div class="card shadow-sm border-0 mb-4 bg-white">
        <div class="card-body p-4">
            <div class="d-flex flex-wrap gap-2 align-items-center mb-2">
                <span class="badge bg-danger fs-6">Release 2: Gobernanza y Reputación</span>
                <span class="badge bg-primary">CU6 / HU-I1-03</span>
                <span class="badge bg-secondary">Rol: Ciudadano Informante</span>
            </div>
            <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                <div>
                    <h2 class="fw-bold text-dark mb-1">
                        <i class="bi bi-person-badge text-danger me-2"></i> Perfil Ciudadano de Confiabilidad y Gobernanza
                    </h2>
                    <p class="text-muted mb-0">
                        Consulte su reputación cívica, su nivel de confiabilidad comunitaria asignado por el Cuerpo de Bomberos y el historial de sus alertas reportadas.
                    </p>
                </div>
                <div class="text-end">
                    <span class="text-muted small d-block">Usuario Autenticado:</span>
                    <strong class="text-dark fs-5">${ciudadano.nombre}</strong>
                    <div class="small text-muted">${ciudadano.email} | Tel: ${ciudadano.telefono}</div>
                </div>
            </div>
        </div>
    </div>

    <!-- Banner de Suspensión Preventiva (CA-03) -->
    <c:if test="${ciudadano.suspendido}">
        <div class="alert alert-danger shadow-sm border-danger p-4 mb-4" role="alert">
            <div class="d-flex align-items-start">
                <i class="bi bi-slash-circle-fill text-danger fs-1 me-3 flex-shrink-0"></i>
                <div class="flex-grow-1">
                    <h4 class="alert-heading fw-bold mb-1">
                        Cuenta Suspendida Preventivamente (Bloqueo de Emisión Activo)
                    </h4>
                    <p class="mb-2">
                        Su perfil ha alcanzado el límite crítico de <strong>${ciudadano.reportesFalsos} falsas alarmas</strong> sancionadas por el Cuerpo de Bomberos del DMQ (Criterio de Aceptación HU-I1-02.3).
                    </p>
                    <p class="small text-danger mb-0">
                        <i class="bi bi-shield-x me-1"></i> La facultad de emitir nuevos reportes de emergencia permanece inhabilitada para proteger la operatividad y recursos de los cuarteles de bomberos.
                    </p>
                </div>
            </div>
        </div>
    </c:if>

    <!-- ========================================================================= -->
    <!-- TARJETAS DE MÉTRICAS DEL PERFIL CIUDADANO (CA-01)                        -->
    <!-- ========================================================================= -->
    <div class="row g-3 mb-4">
        <!-- 1. Saldo de Puntos -->
        <div class="col-md-3">
            <div class="card shadow-sm border-0 bg-white h-100">
                <div class="card-body p-3 text-center">
                    <div class="text-muted small fw-bold text-uppercase mb-1">Puntos de Reputación</div>
                    <div class="display-5 fw-bold text-primary mb-1">
                        ${ciudadano.puntosReputacion} <span class="fs-6 text-muted">pts</span>
                    </div>
                    <small class="text-muted d-block">
                        <i class="bi bi-info-circle me-1"></i> Base 100 | +50 Veraz | -100 Falsa
                    </small>
                </div>
            </div>
        </div>

        <!-- 2. Rango / Nivel de Confiabilidad -->
        <div class="col-md-3">
            <div class="card shadow-sm border-0 bg-white h-100">
                <div class="card-body p-3 text-center">
                    <div class="text-muted small fw-bold text-uppercase mb-1">Nivel de Confiabilidad</div>
                    <div class="my-2">
                        <c:choose>
                            <c:when test="${nivelConfiabilidad.name() == 'ORO'}">
                                <span class="badge badge-oro fs-6 px-3 py-2">
                                    <i class="bi bi-star-fill text-warning me-1"></i> NIVEL ORO
                                </span>
                            </c:when>
                            <c:when test="${nivelConfiabilidad.name() == 'PLATA'}">
                                <span class="badge badge-plata fs-6 px-3 py-2">
                                    <i class="bi bi-award-fill me-1"></i> NIVEL PLATA
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-bronce fs-6 px-3 py-2">
                                    <i class="bi bi-shield-shaded me-1"></i> NIVEL BRONCE
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <small class="text-muted d-block">${nivelConfiabilidad.descripcion}</small>
                </div>
            </div>
        </div>

        <!-- 3. Contador de Falsas Alarmas -->
        <div class="col-md-3">
            <div class="card shadow-sm border-0 bg-white h-100">
                <div class="card-body p-3 text-center">
                    <div class="text-muted small fw-bold text-uppercase mb-1">Faltas Registradas</div>
                    <div class="display-5 fw-bold ${ciudadano.reportesFalsos >= 3 ? 'text-danger' : (ciudadano.reportesFalsos > 0 ? 'text-warning' : 'text-success')} mb-1">
                        ${ciudadano.reportesFalsos} <span class="fs-6 text-muted">/ 3</span>
                    </div>
                    <div class="progress" style="height: 6px;">
                        <div class="progress-bar ${ciudadano.reportesFalsos >= 3 ? 'bg-danger' : 'bg-warning'}"
                             role="progressbar"
                             style="width: ${(ciudadano.reportesFalsos * 100) / 3}%"></div>
                    </div>
                    <small class="text-muted d-block mt-1">
                        ${3 - ciudadano.reportesFalsos > 0 ? (3 - ciudadano.reportesFalsos) : 0} falta(s) para suspensión
                    </small>
                </div>
            </div>
        </div>

        <!-- 4. Estado de Cuenta -->
        <div class="col-md-3">
            <div class="card shadow-sm border-0 bg-white h-100">
                <div class="card-body p-3 text-center">
                    <div class="text-muted small fw-bold text-uppercase mb-1">Estado Operativo</div>
                    <div class="my-2">
                        <c:choose>
                            <c:when test="${ciudadano.suspendido}">
                                <span class="badge bg-danger fs-6 px-3 py-2">
                                    <i class="bi bi-slash-circle me-1"></i> CUENTA SUSPENDIDA
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-success fs-6 px-3 py-2">
                                    <i class="bi bi-check-circle me-1"></i> CUENTA HABILITADA
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <small class="text-muted d-block">
                        ${ciudadano.suspendido ? 'Emisión de reportes bloqueada' : 'Permiso activo para alertar conatos'}
                    </small>
                </div>
            </div>
        </div>
    </div>

    <!-- ========================================================================= -->
    <!-- TABLA DE HISTORIAL DE REPORTES PROPIOS (CA-02)                           -->
    <!-- ========================================================================= -->
    <div class="card shadow-sm border-0 bg-white">
        <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
            <h5 class="fw-bold mb-0 text-dark">
                <i class="bi bi-clock-history text-danger me-2"></i> Historial de Reportes Emitidos por el Ciudadano
            </h5>
            <span class="badge bg-secondary">${misReportes != null ? misReportes.size() : 0} reportes registrados</span>
        </div>
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty misReportes}">
                    <div class="text-center p-5">
                        <i class="bi bi-inbox fs-1 text-muted d-block mb-3"></i>
                        <h5 class="text-muted">Aún no ha reportado ningún conato de incendio.</h5>
                        <p class="text-muted small mb-3">Cuando observe columnas de humo o fuego en zonas forestales, regístrelo para colaborar con la comunidad.</p>
                        <c:if test="${!ciudadano.suspendido}">
                            <a href="${pageContext.request.contextPath}/reportar" class="btn btn-danger btn-sm px-3">
                                <i class="bi bi-megaphone me-1"></i> Emitir mi primer reporte
                            </a>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light">
                            <tr>
                                <th class="ps-4"># ID</th>
                                <th>Fecha y Hora</th>
                                <th>Ubicación / Sector</th>
                                <th>Descripción Observada</th>
                                <th>Estado Operativo</th>
                                <th class="text-end pe-4">Evaluación de Veracidad (HU-I1-01 / HU-I1-02)</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="rep" items="${misReportes}">
                                <tr>
                                    <td class="ps-4 fw-bold text-primary">#${rep.id}</td>
                                    <td>
                                        <div class="small fw-semibold text-dark">${rep.fechaHoraFormateada}</div>
                                    </td>
                                    <td>
                                        <div class="fw-semibold text-dark">${rep.direccionFormateada}</div>
                                    </td>
                                    <td>
                                        <small class="text-muted d-block text-truncate" style="max-width: 250px;">
                                            ${rep.descripcion}
                                        </small>
                                    </td>
                                    <td>
                                        <span class="${rep.estado.badgeClass} px-3 py-1">
                                            ${rep.estado.descripcion}
                                        </span>
                                    </td>
                                    <td class="text-end pe-4">
                                        <c:choose>
                                            <c:when test="${rep.calificadoVeraz}">
                                                <span class="badge bg-success px-3 py-2">
                                                    <i class="bi bi-check-circle-fill me-1"></i> Verídico (+50 pts)
                                                </span>
                                            </c:when>
                                            <c:when test="${rep.estado.name() == 'FALSA_ALARMA'}">
                                                <span class="badge bg-dark px-3 py-2">
                                                    <i class="bi bi-x-circle-fill text-danger me-1"></i> Falsa Alarma (-100 pts)
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-light text-muted border px-3 py-2">
                                                    <i class="bi bi-hourglass-split me-1"></i> En Verificación en Terreno
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Enlaces de navegación rápida -->
    <div class="mt-4 d-flex justify-content-between align-items-center">
        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left me-1"></i> Volver al Inicio
        </a>
        <c:choose>
            <c:when test="${!ciudadano.suspendido}">
                <a href="${pageContext.request.contextPath}/reportar" class="btn btn-danger">
                    <i class="bi bi-megaphone-fill me-1"></i> Reportar Nuevo Conato
                </a>
            </c:when>
            <c:otherwise>
                <button class="btn btn-secondary" disabled>
                    <i class="bi bi-slash-circle me-1"></i> Reportar Inhabilitado (Suspendido)
                </button>
            </c:otherwise>
        </c:choose>
    </div>

</div>

<!-- Bootstrap 5 JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>