package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class EmpleadoDbUtil {

	private DataSource dataSource;

	public EmpleadoDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Empleado> getEmpleados() throws Exception {
		
		List<Empleado> empleados = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select * from empleado order by last_name";
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// create new empleado object
				Empleado tempEmpleado = new Empleado(id, firstName, lastName, email);
				
				// add it to the list of empleados
				empleados.add(tempEmpleado);				
			}
			
			return empleados;		
		}
		finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addEmpleado(Empleado theEmpleado) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into empleado "
					   + "(first_name, last_name, email) "
					   + "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the empleado
			myStmt.setString(1, theEmpleado.getFirstName());
			myStmt.setString(2, theEmpleado.getLastName());
			myStmt.setString(3, theEmpleado.getEmail());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Empleado getEmpleado(String theEmpleadoId) throws Exception {

		Empleado theEmpleado = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int empleadoId;
		
		try {
			// convert empleado id to int
			empleadoId = Integer.parseInt(theEmpleadoId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected empleado
			String sql = "select * from empleado where id=?";
			
			// create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, empleadoId);
			
			// execute statement
			myRs = myStmt.executeQuery();
			
			// retrieve data from result set row
			if (myRs.next()) {
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// use the empleadoId during construction
				theEmpleado = new Empleado(empleadoId, firstName, lastName, email);
			}
			else {
				throw new Exception("Could not find empleado id: " + empleadoId);
			}				
			
			return theEmpleado;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void updateEmpleado(Empleado theEmpleado) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update empleado "
						+ "set first_name=?, last_name=?, email=? "
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theEmpleado.getFirstName());
			myStmt.setString(2, theEmpleado.getLastName());
			myStmt.setString(3, theEmpleado.getEmail());
			myStmt.setInt(4, theEmpleado.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteEmpleado(String theEmpleadoId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert empleado id to int
			int empleadoId = Integer.parseInt(theEmpleadoId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete empleado
			String sql = "delete from empleado where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, empleadoId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}
}















