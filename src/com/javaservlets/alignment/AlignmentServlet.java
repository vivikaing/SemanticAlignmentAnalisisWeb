package com.javaservlets.alignment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

/**
 * Servlet implementation class AlignmentServlet
 */
@WebServlet("/AlignmentServlet")
public class AlignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AlignmentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Here");
		String accesskey = request.getParameter("access");
		System.out.println("The access key is: " + accesskey);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String typeAnalysis = request.getParameter("typeAnalysis");
		System.out.println("Type analysis is: " + typeAnalysis);
		PrintWriter out = response.getWriter();

		if (typeAnalysis.equals("goal")) {
			out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h2>Semantic Alignment Analysis From City Goals</h2>"
					+ "<table><tr><th>Goals</th><th>Objectives</th></tr>"
					+ "<tr><td>Goal1</td><td><a href=\"ObjectiveAlignmentServlet?idGoal=1\">View Details</a></td></tr>"
					+ "<tr><td>Goal2</td><td><a href=\"ObjectiveAlignmentServlet?idGoal=2\">View Details</a></td></tr>"
					+ "<tr><td>Goal3</td><td><a href=\"ObjectiveAlignmentServlet?idGoal=3\">View Details</a></td></tr></table></body></html>");
		} else if (typeAnalysis.equals("objective")) {
			out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h2>Semantic Alignment Analysis From City Objectives</h2>"
					+ "<table style=\"width:100%\" border=1><tr><th>Domains</th><th>Objectives</th><th>City Services</th></tr>"
					+ "<tr><td>Education</td><td>Objective1</td><td><a href=\"CityServiceAlignmetServlet?idObjective=1\">View Details</a></tr>"
					+ "<tr><td>Mobility</td><td>Objective2</td><td><a href=\"CityServiceAlignmetServlet?idObjective=2\">View Details</a></td></tr>"
					+ "<tr><td>Livability</td><td>Objective3</td><td><a href=\"CityServiceAlignmetServlet?idObjective=3\">View Details</a></td></tr></table></body></html>");
			
		} else if (typeAnalysis.equals("cityService")) {
			out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h2>Semantic Alignment Analysis From City Services</h2>"
					+ "<table style=\"width:100%\" border=1><tr><th>Domains</th><th>City Services</th><th>Quality of Life Dimension</th>"
					+ "<th>Indicators</th><th>Current Value</th><th>Target Value</th><th>Application Services</th></tr>"
					+ "<tr><td>Livability</td><td>Waste Management City Service</td><td>Environmental Quality</td>"
					+ "<td>Number of bins not collected per neighbourhood</td><td>5</td><td>0</td><td><a href=\"ApplicationServiceAlignmentServlet?idCityService=3\">View Details</a></td></tr></table></body></html>");
		}
	}
}
