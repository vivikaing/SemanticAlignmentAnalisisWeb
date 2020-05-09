package com.javaservlets.alignment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApplicationServiceAlignmentServlet
 */
@WebServlet("/ApplicationServiceAlignmentServlet")
public class ApplicationServiceAlignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApplicationServiceAlignmentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idCityService = request.getParameter("idCityService");
		System.out.println("The idCityService is: " + idCityService);
		PrintWriter out = response.getWriter();
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
		out.println("<body><h3>Semantic Alignment Analysis from City Services</h3>"
				+ "<table><tr><th>Domains</th><th>City Services</th><th>Quality of Life Dimension</th>"
				+ "<th>Indicators</th><th>Target Value</th><th>Current Value</th></tr>"
				+ "<tr><td>Livability</td><td>Waste Management City Service</td><td>Environmental Quality</td>"
				+ "<td>Number of bins not collected per neighbourhood</td><td align=\"center\">0</td><td bgcolor=\"#e8899b\" align=\"center\">5</td></tr></table></body></html>");
		
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
		out.println("<body><h3>Application Services</h3>"
				+ "<table><tr><th>Application Services</th><th>QoS</th><th>Target Value</th>"
				+ "<th>Current Value</th><th>Related Applications</th></tr>"
				+ "<tr><td>Get Bins Fills-Level</td><td>Accuracy</td><td align=\"center\">95%</td>"
				+ "<td bgcolor=\"#e8899b\" align=\"center\">70%</td><td>Sensor Bin Web Service</td></tr>"
				+ "<tr><td>Get Road Network</td><td>Availability</td><td align=\"center\">98%</td>"
				+ "<td align=\"center\">98%</td><td>Geographic Information System</td></tr></table></body></html>");
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
