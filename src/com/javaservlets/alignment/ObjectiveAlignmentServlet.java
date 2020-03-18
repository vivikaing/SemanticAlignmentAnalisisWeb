package com.javaservlets.alignment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ObjectiveAlignmentServlet
 */
@WebServlet("/ObjectiveAlignmentServlet")
public class ObjectiveAlignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ObjectiveAlignmentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idGoal = request.getParameter("idGoal");
		System.out.println("The idGoal is: " + idGoal);
		PrintWriter out = response.getWriter();
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
		out.println("<html><body><h2>Semantic Alignment Analysis From City Objectives</h2>"
				+ "<table><tr><th>Domains</th><th>Objectives</th><th>City Services</th></tr>"
				+ "<tr><td>Education</td><td>Objective1</td><td><a href=\"CityServiceAlignmetServlet?idObjective=1\">View Details</a></tr>"
				+ "<tr><td>Mobility</td><td>Objective2</td><td><a href=\"CityServiceAlignmetServlet?idObjective=2\">View Details</a></td></tr>"
				+ "<tr><td>Livability</td><td>Objective3</td><td><a href=\"CityServiceAlignmetServlet?idObjective=3\">View Details</a></td></tr></table></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
