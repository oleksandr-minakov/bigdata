<html>
<head>
<title>Books database</title>
</head> 

<body>
<div id="title" style="text-align:center;">
	<b>YOU CAN UPLOAD YOUR BOOKS HERE :)</b>
</div>
<br>

<form>
Title:   <input type="text" name="title">
<br>
Author:  <input type="text" name="author">
<br>
Genre:   <input type="text" name="genre">
<br>
</form>
<form action="">
Text:    <input type="file" />
<br>  
</form>

<div id="title" style="text-align:center;">
<form name="upload" action="........." method="post">
<input type="submit" value="Upload">
</form> 
</div>
<br>
<hr>

<div id="findTitle" style="text-align:center;">
	<b>YOU CAN FIND BOOKS HERE :)</b>
</div>
<form>
Find:   <input type="text" name="find">
	by
	<select name="Find">
	<option value="by title">Title</option>
	<option value="by author">Author</option>
	<option value="by genre">Genre</option>
	<option value="by text">Text</option>
	</select>
</form>
<div id="find" style="text-align:center;">
<form name="find" action="........." method="post">
<input type="submit" value="Search">
</form> 
</div>

</body>
</html>
