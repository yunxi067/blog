<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload File - Blog System</title>
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
                        <h4 class="mb-0">Upload File</h4>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-info">
                            <strong>Remaining Space:</strong> <fmt:formatNumber value="${space.remainSize / 1024 / 1024}" maxFractionDigits="2"/> MB
                        </div>

                        <form id="uploadForm">
                            <div class="mb-3">
                                <label for="file" class="form-label">Select File</label>
                                <input type="file" class="form-control" id="file" name="file" required>
                            </div>
                            <div class="mb-3">
                                <label for="category" class="form-label">Category</label>
                                <select class="form-control" id="category" name="category">
                                    <option value="default">Default</option>
                                    <option value="image">Image</option>
                                    <option value="audio">Audio</option>
                                    <option value="video">Video</option>
                                    <option value="document">Document</option>
                                    <option value="other">Other</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                            </div>
                            <button type="button" class="btn btn-primary" onclick="uploadFile()">Upload</button>
                            <a href="${pageContext.request.contextPath}/user/space" class="btn btn-secondary">Cancel</a>
                        </form>
                    </div>
                </div>

                <div id="progressContainer" class="mt-3" style="display: none;">
                    <div class="progress">
                        <div id="progressBar" class="progress-bar" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script>
        function uploadFile() {
            const file = document.getElementById('file').files[0];
            const category = document.getElementById('category').value;
            const description = document.getElementById('description').value;

            if (!file) {
                alert('Please select a file');
                return;
            }

            const formData = new FormData();
            formData.append('file', file);
            formData.append('category', category);
            formData.append('description', description);

            document.getElementById('progressContainer').style.display = 'block';

            fetch('${pageContext.request.contextPath}/file/doUpload', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('File uploaded successfully');
                    window.location.href = '${pageContext.request.contextPath}/file/list';
                } else {
                    alert('Upload failed: ' + data.message);
                }
                document.getElementById('progressContainer').style.display = 'none';
            })
            .catch(error => {
                alert('Error: ' + error);
                document.getElementById('progressContainer').style.display = 'none';
            });
        }
    </script>
</body>
</html>
