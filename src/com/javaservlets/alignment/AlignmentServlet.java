package com.javaservlets.alignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semantic.aligment.model.Goal;
import semantic.alignment.logic.ReadRdf;

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
		
		String realContextPath = getServletContext().getRealPath("");

		if (typeAnalysis.equals("goal")) {
			
			String goalsHtml = new String();
			goalsHtml = getGoals(realContextPath);
			
			out.print("<html><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
					+ "<head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h3>Semantic Alignment Analysis from City Goals</h3>"
					+ "<table><tr><th>Goals</th><th>Objectives</th></tr>"
					//+ "<tr><td>Goal1</td><td><a href=\"ObjectiveAlignmentServlet?idGoal=1\">View Details</a></td></tr>"
					//+ "<tr><td>Goal2</td><td><a href=\"ObjectiveAlignmentServlet?idGoal=2\">View Details</a></td></tr>"
					//+ "<tr><td>Goal3</td><td><a href=\"ObjectiveAlignmentServlet?idGoal=3\">View Details</a></td></tr></table></body></html>");
					+ goalsHtml
					+"</table></body></html>");
			
		} else if (typeAnalysis.equals("objective")) {
			out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h3>Semantic Alignment Analysis from City Objectives</h3>"
					+ "<table><tr><th>Domains</th><th>Objectives</th><th>City Services</th></tr>"
					+ "<tr><td>Education</td><td>Objective1</td><td><a href=\"CityServiceAlignmetServlet?idObjective=1\">View Details</a></tr>"
					+ "<tr><td>Mobility</td><td>Objective2</td><td><a href=\"CityServiceAlignmetServlet?idObjective=2\">View Details</a></td></tr>"
					+ "<tr><td bgcolor=\"#e8899b\">Livability</td><td>Objective3</td><td><a href=\"CityServiceAlignmetServlet?idObjective=3\">View Details</a></td></tr></table></body></html>");
			
		} else if (typeAnalysis.equals("cityService")) {
			out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h3>Semantic Alignment Analysis from City Services</h3>"
					+ "<table><tr><th>Domains</th><th>City Services</th><th>Quality of Life Dimension</th>"
					+ "<th>Indicators</th><th>Target Value</th><th>Current Value</th><th>Application Services</th></tr>"
					+ "<tr><td>Livability</td><td>Waste Management City Service</td><td>Environmental Quality</td>"
					+ "<td>Number of bins not collected per neighbourhood</td><td align=\"center\">0</td><td bgcolor=\"#e8899b\" align=\"center\">5</td><td><a href=\"ApplicationServiceAlignmentServlet?idCityService=3\">View Details</a></td></tr></table></body></html>");
		}
	}
	
	protected String getGoals(String path) {
		String goalsHtml = new String();
		ReadRdf rdf = new ReadRdf(path);

		ArrayList<Goal> goals = rdf.findGoals();
		for(int i=0;i<goals.size();i++) {
			goalsHtml = goalsHtml 
			+ "<tr><td>"
			+ goals.get(i).getName() 
			+ "</td><td><a href=\"ObjectiveAlignmentServlet?idGoal="
			+ goals.get(i).getId() 
			+"\">View Details</a></td></tr>";
			
		}
		return goalsHtml;
	}
	
}
