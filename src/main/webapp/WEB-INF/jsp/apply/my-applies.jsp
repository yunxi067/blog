<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Applications - Blog System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Blog System</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/space">My Space</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <h2>My Applications</h2>
                <hr>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/apply/request" class="btn btn-primary">New Application</a>
            </div>
        </div>

        <c:if test="${applies == null || applies.size() == 0}">
            <div class="alert alert-info">No applications yet</div>
        </c:if>

        <c:if test="${applies != null && applies.size() > 0}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Apply ID</th>
                            <th>Size (MB)</th>
                            <th>Status</th>
                            <th>Reason</th>
                            <th>Apply Date</th>
                            <th>Update Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${applies}" var="apply">
                            <tr>
                                <td>${apply.applyId}</td>
                                <td><fmt:formatNumber value="${apply.applySize / 1024 / 1024}" maxFractionDigits="2"/></td>
                                <td>
                                    <c:if test="${apply.status == 0}">
                                        <span class="badge bg-warning">Pending</span>
                                    </c:if>
                                    <c:if test="${apply.status == 1}">
                                        <span class="badge bg-success">Approved</span>
                                    </c:if>
                                    <c:if test="${apply.status == 2}">
                                        <span class="badge bg-danger">Rejected</span>
                                    </c:if>
                                </td>
                                <td>${apply.reason}</td>
                                <td><fmt:formatDate value="${apply.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate value="${apply.updateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
