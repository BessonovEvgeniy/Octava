<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<body>
<h2>Download yours Rinex File</h2>

<div class="row">
    <form method="post" action="/readRinex" enctype="multipart/form-data">
        <div class="file_upload">
            <input type="file" name="Observation data">
        </div>
    </form>
</div>


</body>
</html>
