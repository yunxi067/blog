<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 Server Error</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body text-center">
                        <h1 class="display-4">500</h1>
                        <p class="lead">Server Error</p>
                        <p>Something went wrong on the server.</p>
                        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
