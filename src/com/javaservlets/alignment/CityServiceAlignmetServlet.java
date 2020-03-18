package com.javaservlets.alignment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CityServiceAlignmetServlet
 */
@WebServlet("/CityServiceAlignmetServlet")
public class CityServiceAlignmetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CityServiceAlignmetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idObjective = request.getParameter("idObjective");
		System.out.println("The idObjective is: " + idObjective);
		PrintWriter out = response.getWriter();
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
		out.println("<html><body><h2>Semantic Alignment Analysis From City Services</h2>"
				+ "<table style=\"width:100%\" border=1><tr><th>Domains</th><th>City Services</th><th>Quality of Life Dimension</th>"
				+ "<th>Indicators</th><th>Current Value</th><th>Target Value</th><th>Application Services</th></tr>"
				+ "<tr><td>Livability</td><td>Waste Management City Service</td><td>Environmental Quality</td>"
				+ "<td>Number of bins not collected per neighbourhood</td><td>5</td><td>0</td><td><a href=\"ApplicationServiceAlignmentServlet?idCityService=3\">View Details</a></td></tr></table></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
