<!DOCTYPE html>
<html>

<head>
	<title>Empleado</title>

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
		<h3>Update Empleado</h3>
		
		<form action="EmpleadoControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />

			<input type="hidden" name="empleadoId" value="${THE_EMPLEADO.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" 
								   value="${THE_EMPLEADO.firstName}" /></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" 
								   value="${THE_EMPLEADO.lastName}" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" 
								   value="${THE_EMPLEADO.email}" /></td>
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











