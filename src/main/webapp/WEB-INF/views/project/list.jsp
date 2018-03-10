<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Project</title>
    <table border="1">
        <thead>
        <tr>
            <th>Name</th>
            <th>Created By</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="project" items="${projects}">
            <tr>
                <td>${project.name}</td>
                <td>${project.created}</td>
                <td>${project.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</head>
<body>

</body>
</html>
