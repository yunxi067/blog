<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Space - Blog System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Blog System</a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/space">My Space</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/file/upload">Upload</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/file/list">My Files</a>
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
                <h2>My Space</h2>
                <hr>
            </div>
        </div>

        <div class="row">
            <div class="col-md-8">
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">Space Information</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <p><strong>Username:</strong> ${user.username}</p>
                                <p><strong>Email:</strong> ${user.email}</p>
                            </div>
                            <div class="col-md-6">
                                <p><strong>User ID:</strong> ${user.uid}</p>
                                <p><strong>Status:</strong> <c:if test="${user.status == 1}"><span class="badge bg-success">Normal</span></c:if><c:if test="${user.status == 0}"><span class="badge bg-danger">Frozen</span></c:if></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">Storage Status</h5>
                    </div>
                    <div class="card-body">
                        <c:set var="totalSize" value="${space.ssizeTotal}"/>
                        <c:set var="usedSize" value="${space.ssizeUsed}"/>
                        <c:set var="remainSize" value="${space.remainSize}"/>
                        <c:set var="percentage" value="${(usedSize / totalSize) * 100}"/>

                        <p><strong>Total Space:</strong> <fmt:formatNumber value="${totalSize / 1024 / 1024}" maxFractionDigits="2"/> MB</p>
                        <p><strong>Used Space:</strong> <fmt:formatNumber value="${usedSize / 1024 / 1024}" maxFractionDigits="2"/> MB</p>
                        <p><strong>Remaining Space:</strong> <fmt:formatNumber value="${remainSize / 1024 / 1024}" maxFractionDigits="2"/> MB</p>
                        <p><strong>Downloads:</strong> ${space.downloadCount}</p>

                        <div class="progress mb-3">
                            <div class="progress-bar" role="progressbar" style="width: ${percentage}%" aria-valuenow="${percentage}" aria-valuemin="0" aria-valuemax="100">
                                <fmt:formatNumber value="${percentage}" maxFractionDigits="1"/>%
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/file/upload" class="btn btn-primary w-100">Upload File</a>
                            </div>
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/apply/request" class="btn btn-secondary w-100">Request Expansion</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Quick Links</h5>
                    </div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><a href="${pageContext.request.contextPath}/file/list">View All Files</a></li>
                            <li class="list-group-item"><a href="${pageContext.request.contextPath}/apply/myApplies">My Applications</a></li>
                            <li class="list-group-item"><a href="${pageContext.request.contextPath}/user/profile">Edit Profile</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
