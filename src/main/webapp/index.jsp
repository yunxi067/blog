<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Blog System - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Blog System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <c:if test="${sessionScope.login_user == null}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/register">Register</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.login_user != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/space">My Space</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/logout">Logout</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <div class="jumbotron">
                    <h1 class="display-4">Welcome to Blog System</h1>
                    <p class="lead">A Spring MVC based file management and sharing platform</p>
                    <hr class="my-4">
                    <c:if test="${sessionScope.login_user == null}">
                        <p>Get started by creating an account or logging in.</p>
                        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/user/register" role="button">Sign Up</a>
                        <a class="btn btn-secondary btn-lg" href="${pageContext.request.contextPath}/user/login" role="button">Login</a>
                    </c:if>
                    <c:if test="${sessionScope.login_user != null}">
                        <p>Welcome, ${sessionScope.login_user.username}!</p>
                        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/user/space" role="button">Go to My Space</a>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="row mt-5">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Upload Files</h5>
                        <p class="card-text">Upload and manage your files securely</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Share & Download</h5>
                        <p class="card-text">Share files and download from your space</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Follow Users</h5>
                        <p class="card-text">Follow other users and get notifications</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
