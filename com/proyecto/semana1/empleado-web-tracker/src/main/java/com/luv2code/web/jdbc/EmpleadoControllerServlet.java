package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
//import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class EmpleadoControllerServlet
 */
@WebServlet("/EmpleadoControllerServlet")
public class EmpleadoControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmpleadoDbUtil empleadoDbUtil;
	
	//@Resource(name="jdbc/web_student_tracker2")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our empleado db util ... and pass in the conn pool / datasource
		try {
			Context ctx = new InitialContext(); //USO DE JNDI
			dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/javatechie"); //USO DE JNDI
			empleadoDbUtil = new EmpleadoDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing empleados
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listEmpleados(request, response);
				break;
				
			case "ADD":
				addEmpleado(request, response);
				break;
				
			case "LOAD":
				loadEmpleado(request, response);
				break;
				
			case "UPDATE":
				updateEmpleado(request, response);
				break;
			
			case "DELETE":
				deleteEmpleado(request, response);
				break;
				
			default:
				listEmpleados(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void deleteEmpleado(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read empleado id from form data
		String theEmpleadoId = request.getParameter("empleadoId");
		
		// delete empleado from database
		empleadoDbUtil.deleteEmpleado(theEmpleadoId);
		
		// send them back to "list empleados" page
		listEmpleados(request, response);
	}

	private void updateEmpleado(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read empleado info from form data
		int id = Integer.parseInt(request.getParameter("empleadoId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new empleado object
		Empleado theEmpleado = new Empleado(id, firstName, lastName, email);
		
		// perform update on database
		empleadoDbUtil.updateEmpleado(theEmpleado);
		
		// send them back to the "list empleados" page
		listEmpleados(request, response);
		
	}

	private void loadEmpleado(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// read empleado id from form data
		String theEmpleadoId = request.getParameter("empleadoId");
		
		// get empleado from database (db util)
		Empleado theEmpleado = empleadoDbUtil.getEmpleado(theEmpleadoId);
		
		// place empleado in the request attribute
		request.setAttribute("THE_EMPLEADO", theEmpleado);
		
		// send to jsp page: update-empleado-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-empleado-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addEmpleado(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read empleado info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");		
		
		// create a new empleado object
		Empleado theEmpleado = new Empleado(firstName, lastName, email);
		
		// add the empleado to the database
		empleadoDbUtil.addEmpleado(theEmpleado);
				
		// send back to main page (the empleado list)
		listEmpleados(request, response);
	}

	private void listEmpleados(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get empleados from db util
		List<Empleado> empleados = empleadoDbUtil.getEmpleados();
		
		// add empleados to the request
		request.setAttribute("EMPLEADO_LIST", empleados);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-empleados.jsp");
		dispatcher.forward(request, response);
	}

}













