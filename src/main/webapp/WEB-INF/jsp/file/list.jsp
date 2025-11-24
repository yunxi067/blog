<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Files - Blog System</title>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/file/upload">Upload</a>
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
                <h2>My Files</h2>
                <hr>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/file/upload" class="btn btn-primary">Upload New File</a>
            </div>
        </div>

        <c:if test="${files == null || files.size() == 0}">
            <div class="alert alert-info">No files uploaded yet</div>
        </c:if>

        <c:if test="${files != null && files.size() > 0}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>File Name</th>
                            <th>Size</th>
                            <th>Category</th>
                            <th>Downloads</th>
                            <th>Status</th>
                            <th>Upload Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${files}" var="file">
                            <tr>
                                <td>${file.originalName}</td>
                                <td><fmt:formatNumber value="${file.fileSize / 1024}" maxFractionDigits="2"/> KB</td>
                                <td>${file.category}</td>
                                <td>${file.downloadCount}</td>
                                <td>
                                    <c:if test="${file.status == 1}">
                                        <span class="badge bg-success">Normal</span>
                                    </c:if>
                                    <c:if test="${file.status == 0}">
                                        <span class="badge bg-warning">Frozen</span>
                                    </c:if>
                                </td>
                                <td><fmt:formatDate value="${file.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/file/download/${file.fid}" class="btn btn-sm btn-primary">Download</a>
                                    <button onclick="deleteFile(${file.fid})" class="btn btn-sm btn-danger">Delete</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script>
        function deleteFile(fid) {
            if (confirm('Are you sure you want to delete this file?')) {
                fetch('${pageContext.request.contextPath}/file/delete/' + fid, {
                    method: 'POST'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('File deleted successfully');
                        location.reload();
                    } else {
                        alert('Delete failed: ' + data.message);
                    }
                });
            }
        }
    </script>
</body>
</html>
