package com.javaservlets.alignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semantic.aligment.model.CityService;
import semantic.aligment.model.Domain;
import semantic.aligment.model.Objective;
import semantic.alignment.logic.ReadRdf;

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
		
		String realContextPath = getServletContext().getRealPath("");
		String objectivesHtml = new String();
		objectivesHtml = getObjectives(realContextPath, idGoal);
		
		PrintWriter out = response.getWriter();
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
		out.println("<html><body><h3>Semantic Alignment Analysis from City Objectives</h3>"
				+ "<table><tr><th>Domains</th><th>Objectives</th><th>City Services</th></tr>"
				//+ "<tr><td>Education</td><td>Objective1</td><td><a href=\"CityServiceAlignmetServlet?idObjective=1\">View Details</a></tr>"
				//+ "<tr><td>Mobility</td><td>Objective2</td><td><a href=\"CityServiceAlignmetServlet?idObjective=2\">View Details</a></td></tr>"
				//+ "<tr><td bgcolor=\"#e8899b\">Livability</td><td>Objective3</td><td><a href=\"CityServiceAlignmetServlet?idObjective=3\">View Details</a></td></tr></table></body></html>"
				+ objectivesHtml
				+ "</table></body></html>");
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

	protected String getObjectives(String path, String idGoal) {
		String objectivesHtml = new String();
		ReadRdf rdf = new ReadRdf(path);
		ArrayList<Objective> objectives = rdf.findObjectives(idGoal);
		for(int i=0;i<objectives.size();i++) {
			ArrayList<CityService> cityServices = new ArrayList<CityService>();
			cityServices = objectives.get(i).getCityServices();
			String domainsHtml = new String();
			for(int j=0; j < cityServices.size();j++) {
				ArrayList<Domain> domains = new ArrayList<Domain>();
				domains = cityServices.get(j).getDomains();
				for(int k=0; k < domains.size();k++) {
					if (k==0) {
						domainsHtml = domains.get(k).getName();
					}
					else {
						domainsHtml = domainsHtml + ", " + domains.get(k).getName();
					}
				}
			}
			
			objectivesHtml = objectivesHtml 
			+ "<tr><td>"
			+ domainsHtml
			+ "</td><td>"
			+ objectives.get(i).getName() 
			+ "</td><td><a href=\"CityServiceAlignmetServlet?idObjective="
			+ objectives.get(i).getId() 
			+ "\">View Details</a></td></tr>";
		}
		return objectivesHtml;
	}
}
