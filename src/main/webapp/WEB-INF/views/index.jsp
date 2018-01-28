<html>
<body>
<h2>Download yours Rinex File</h2>

<form method="get" action="/user/new">
    <button class="btn" type="submit">Create User</button>
</form>
<form method="get" action="/project/new">
    <button class="btn" type="submit">Create Project</button>
</form>
<form method="get" action="/company/new">
    <button class="btn" type="submit">Create Company</button>
</form>
<form method="get" action="/department/new">
    <button class="btn" type="submit">Create Department</button>
</form>
<form method="get" action="/tariff/new">
    <button class="btn" type="submit">Create Tariff</button>
</form>

<div class="row">
    <form method="post" action="/rinex/upload" enctype="multipart/form-data">
        <div class="file_upload">
            <input type="file" name="file">
            <button type="submit">Upload Rinex</button>
        </div>
    </form>
</div>
</body>
</html>
