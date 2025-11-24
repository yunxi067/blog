<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Request Space Expansion - Blog System</title>
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
                        <h4 class="mb-0">Request Space Expansion</h4>
                    </div>
                    <div class="card-body">
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-body">
                                        <h6 class="card-title">Current Space</h6>
                                        <p class="card-text">
                                            Total: <fmt:formatNumber value="${space.ssizeTotal / 1024 / 1024}" maxFractionDigits="2"/> MB<br>
                                            Used: <fmt:formatNumber value="${space.ssizeUsed / 1024 / 1024}" maxFractionDigits="2"/> MB<br>
                                            Remaining: <fmt:formatNumber value="${space.remainSize / 1024 / 1024}" maxFractionDigits="2"/> MB
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-body">
                                        <h6 class="card-title">Statistics</h6>
                                        <p class="card-text">
                                            Total Downloads: ${space.downloadCount}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <c:if test="${!canApply}">
                            <div class="alert alert-warning">
                                Your download count must be at least 50 to request expansion. Current: ${space.downloadCount}
                            </div>
                        </c:if>

                        <form id="applyForm">
                            <div class="mb-3">
                                <label for="applySize" class="form-label">Request Size (MB)</label>
                                <input type="number" class="form-control" id="applySize" name="applySize" min="100" required ${!canApply ? 'disabled' : ''}>
                            </div>
                            <div class="mb-3">
                                <label for="reason" class="form-label">Reason for Expansion</label>
                                <textarea class="form-control" id="reason" name="reason" rows="4" ${!canApply ? 'disabled' : ''}></textarea>
                            </div>
                            <button type="button" class="btn btn-primary" onclick="submitApplication()" ${!canApply ? 'disabled' : ''}>Submit Application</button>
                            <a href="${pageContext.request.contextPath}/user/space" class="btn btn-secondary">Back</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
    <script>
        function submitApplication() {
            const applySize = parseInt(document.getElementById('applySize').value);
            const reason = document.getElementById('reason').value;

            if (!applySize || applySize < 100) {
                alert('Please enter a valid size (minimum 100 MB)');
                return;
            }

            fetch('${pageContext.request.contextPath}/apply/doRequest', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'applySize=' + applySize + '&reason=' + encodeURIComponent(reason)
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Application submitted successfully');
                    window.location.href = '${pageContext.request.contextPath}/apply/myApplies';
                } else {
                    alert('Submit failed: ' + data.message);
                }
            });
        }
    </script>
</body>
</html>
