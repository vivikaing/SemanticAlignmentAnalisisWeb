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
import semantic.aligment.model.Goal;
import semantic.aligment.model.Objective;
import semantic.aligment.model.QoLDimension;
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
					+ goalsHtml
					+"</table></body></html>");
			
		} else if (typeAnalysis.equals("objective")) {
			String objectivesHtml = new String();
			objectivesHtml = getObjectives(realContextPath);
			
			out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h3>Semantic Alignment Analysis from City Objectives</h3>"
					+ "<table><tr><th>Domains</th><th>Objectives</th><th>City Services</th></tr>"
					+ objectivesHtml
					+"</table></body></html>");
			
		} else if (typeAnalysis.equals("cityService")) {
			String cityServicesHtml = new String();
			cityServicesHtml = getCityServices(realContextPath);
			
			out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
			out.println("<body><h3>Semantic Alignment Analysis from City Services</h3>"
					+ "<table><tr><th>Domains</th><th>City Services</th><th>Quality of Life Dimensions</th>"
					+ "<th>Indicators</th><th>Current Value</th><th>Target Value</th><th>Application Services</th></tr>"
					+ cityServicesHtml
					+"</table></body></html>");
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
	
	protected String getObjectives(String path) {
		String objectivesHtml = new String();
		ReadRdf rdf = new ReadRdf(path);
		ArrayList<Objective> objectives = rdf.findAllObjectives();
		
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
	
	public String getCityServices(String path) {
		String cityServicesHtml = new String();
		ReadRdf rdf = new ReadRdf(path);
		ArrayList<CityService> cityServices = rdf.findAllCityServices();
		
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
			
			ArrayList<QoLDimension> qolDimensions = new ArrayList<QoLDimension>();
			qolDimensions = cityServices.get(j).getIndicator().getQolDimensions();
			String qoLDimensionsHtml = new String();
			for(int l=0; l < qolDimensions.size();l++) {
				if (l==0) {
					qoLDimensionsHtml = qolDimensions.get(l).getName();
				}
				else {
					qoLDimensionsHtml = qoLDimensionsHtml + ", " + qolDimensions.get(l).getName();
				}
			}
			
			String redIndicatorHtml = "</td><td>";
			if(cityServices.get(j).getIndicator().getRed_indicator() == true) {
				redIndicatorHtml = "</td><td bgcolor= \"#f55bb5\">";
			}
				
			cityServicesHtml = cityServicesHtml 
			+ "<tr><td>"
			+ domainsHtml
			+ "</td><td>"
			+ cityServices.get(j).getName() 
			+ "</td><td>"
			+ qoLDimensionsHtml
			+ "</td><td>"
			+ cityServices.get(j).getIndicator().getName()
			+ redIndicatorHtml
			+ cityServices.get(j).getIndicator().getCurrentValue()
			+ " "
			+ cityServices.get(j).getIndicator().getUnitOfMeasure()
			+ "</td><td>"
			+ cityServices.get(j).getIndicator().getOperatorText()
		    + cityServices.get(j).getIndicator().getTargetValue()
		    + " "
		    + cityServices.get(j).getIndicator().getUnitOfMeasure()
			+ "</td><td><a href=\"ApplicationServiceAlignmentServlet?idObjective="
			+ cityServices.get(j).getIdObjective()
			+ "\">View Details</a></td></tr>";	
		}	
		return cityServicesHtml;
	}
}
