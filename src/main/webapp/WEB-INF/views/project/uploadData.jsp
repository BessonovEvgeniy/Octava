<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Rinex file</title>

    <div class="row">
        <form method="post" action="/rinex/upload" enctype="multipart/form-data">
            <div class="file_upload">
                <input type="file" name="file">
                <button type="submit">Upload Rinex</button>
            </div>
        </form>
    </div>
</head>
<body>

</body>
</html>
