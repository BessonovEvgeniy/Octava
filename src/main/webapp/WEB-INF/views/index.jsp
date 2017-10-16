<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Download yours Rinex File</h2>

<div class="row">
    <form method="post" action="<c:url value="/rinex/upload"/>" enctype="multipart/form-data">
        <div class="file_upload">
            <input type="file" name="file">
            <button type="submit">Upload Rinex</button>
        </div>
    </form>
</div>
</body>
</html>
