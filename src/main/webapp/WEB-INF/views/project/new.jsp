<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new project</title>
</head>
<body>
<sf:form method="post" action="/project/create" modelAttribute="project">
    <div class="file_upload">
        <input type="text" name="name">
        <button type="submit">Create</button>
    </div>
</sf:form>
</body>
</html>
