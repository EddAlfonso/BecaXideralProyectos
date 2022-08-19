<!DOCTYPE html>
<html>

<head>
	<title>Add Empleado</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-empleado-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Mi Negocio CRUD</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Add Empleado</h3>
		
		<form action="EmpleadoControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" /></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="EmpleadoControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>











