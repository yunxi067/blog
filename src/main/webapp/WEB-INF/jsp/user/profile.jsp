<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile - Blog System</title>
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
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">Edit Profile</h4>
                    </div>
                    <div class="card-body">
                        <form id="profileForm">
                            <div class="mb-3">
                                <label for="uid" class="form-label">User ID</label>
                                <input type="text" class="form-control" id="uid" name="uid" value="${user.uid}" disabled>
                            </div>
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" name="username" value="${user.username}" disabled>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" value="${user.email}">
                            </div>
                            <div class="mb-3">
                                <button type="button" class="btn btn-primary" onclick="updateProfile()">Update Profile</button>
                                <a href="${pageContext.request.contextPath}/user/space" class="btn btn-secondary">Back</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script>
        function updateProfile() {
            const email = document.getElementById('email').value;
            fetch('${pageContext.request.contextPath}/user/updateProfile', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: email
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Profile updated successfully');
                } else {
                    alert('Update failed: ' + data.message);
                }
            });
        }
    </script>
</body>
</html>
