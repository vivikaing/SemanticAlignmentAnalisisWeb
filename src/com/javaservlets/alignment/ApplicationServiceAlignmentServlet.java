package com.javaservlets.alignment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import semantic.aligment.model.AppService;
import semantic.aligment.model.CityService;
import semantic.aligment.model.Domain;
import semantic.aligment.model.QoSAppService;
import semantic.alignment.logic.ReadRdf;

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
		String idObjective = request.getParameter("idObjective");
		System.out.println("The idObjective is: " + idObjective);
		String appServicesHtml = new String();
		String cityServicesHtml = new String();
		
		String realContextPath = getServletContext().getRealPath("");
		ArrayList<String> answerHtml = new ArrayList<String>();
		answerHtml = getCityServices(realContextPath, idObjective);
		
		if (answerHtml.size() == 1) {
			cityServicesHtml = answerHtml.get(0);
		}
		else if (answerHtml.size() == 2) {
			appServicesHtml = answerHtml.get(0);
			cityServicesHtml = answerHtml.get(1);
		}
		
		PrintWriter out = response.getWriter();
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
		out.println("<body><h3>Semantic Alignment Analysis from City Services</h3>"
				+ "<table><tr><th>Domains</th><th>City Services</th><th>Quality of Life Dimension</th>"
				+ "<th>Indicators</th><th>Target Value</th><th>Current Value</th></tr>"
				+ cityServicesHtml
				+ "</table></body></html>");
				//+ "<tr><td>Livability</td><td>Waste Management City Service</td><td>Environmental Quality</td>"
				//+ "<td>Number of bins not collected per neighbourhood</td><td align=\"center\">0</td><td bgcolor=\"#e8899b\" align=\"center\">5</td></tr></table></body></html>");
				
		
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\" /> \r\n" + "</head>");
		out.println("<body><h3>Application Services</h3>"
				+ "<table><tr><th>Application Services</th><th>QoS</th><th>Target Value</th>"
				+ "<th>Current Value</th><th>Related Applications</th></tr>"
				+ appServicesHtml
				+ "</table></body></html>");
				//+ "<tr><td>Get Bins Fills-Level</td><td>Accuracy</td><td align=\"center\">95%</td>"
				//+ "<td bgcolor=\"#e8899b\" align=\"center\">70%</td><td>Sensor Bin Web Service</td></tr>"
				//+ "<tr><td>Get Road Network</td><td>Availability</td><td align=\"center\">98%</td>"
				//+ "<td align=\"center\">98%</td><td>Geographic Information System</td></tr></table></body></html>");
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
	
	protected ArrayList<String> getCityServices(String path, String idObjective) {
		ArrayList<String> answerHtml = new ArrayList<String>();
		String cityServicesHtml = new String();
		
		ReadRdf rdf = new ReadRdf(path);
		ArrayList<CityService> cityServices = rdf.findCityServices(idObjective);
		
		String domainsHtml = new String();
			for(int j=0; j < cityServices.size();j++) {
				ArrayList<Domain> domains = new ArrayList<Domain>();
				String appServicesHtml = new String();
				domains = cityServices.get(j).getDomains();
				for(int k=0; k < domains.size();k++) {
					if (k==0) {
						domainsHtml = domains.get(k).getName();
					}
					else {
						domainsHtml = domainsHtml + ", " + domains.get(k).getName();
					}
				}
								
				cityServicesHtml = cityServicesHtml 
				+ "<tr><td>"
				+ domainsHtml
				+ "</td><td>"
				+ cityServices.get(j).getName() 
				+ "</td><td>"
				+ cityServices.get(j).getIndicator().getQolDimension()
				+ "</td><td>"
				+ cityServices.get(j).getIndicator().getName()
				+ "</td><td>"
				+ cityServices.get(j).getIndicator().getCurrentValue()
				+ " "
				+ cityServices.get(j).getIndicator().getUnitOfMeasure()
				+ "</td><td>"
				+ cityServices.get(j).getIndicator().getTargetValue()
				+ " "
			    + cityServices.get(j).getIndicator().getUnitOfMeasure()
				+ "</td></tr>";
				
				//+ "<tr><td>Livability</td><td>Waste Management City Service</td><td>Environmental Quality</td>"
				//+ "<td>Number of bins not collected per neighbourhood</td><td align=\"center\">0</td><td bgcolor=\"#e8899b\" align=\"center\">5</td></tr></table></body></html>");
				
				ArrayList<AppService> appServices = cityServices.get(j).getAppServices();
				for(int m=0; m < appServices.size();m++) {
					ArrayList<QoSAppService> qosAppService = appServices.get(m).getQosAppService();
					for(int n=0; n < qosAppService.size();n++) {
						appServicesHtml = appServicesHtml
						+ "<tr><td>"
						+ appServices.get(m).getName()
						+ "</td><td>"
						+ qosAppService.get(n).getName()
						+ "</td><td>"
						+ qosAppService.get(n).getTargetValue()
						+ " "
						+ qosAppService.get(n).getUnitOfMeasure()
						+ "</td><td>"
						+ qosAppService.get(n).getMonitoredValue()
						+ " "
						+ qosAppService.get(n).getUnitOfMeasure()
						+ "</td><td>"
						+ "Related App"
						+ "</td></tr>";	
					}
					
					
					//+ "<tr><td>Get Bins Fills-Level</td><td>Accuracy</td><td align=\"center\">95%</td>"
					//+ "<td bgcolor=\"#e8899b\" align=\"center\">70%</td><td>Sensor Bin Web Service</td></tr>"
					//+ "<tr><td>Get Road Network</td><td>Availability</td><td align=\"center\">98%</td>"
					//+ "<td align=\"center\">98%</td><td>Geographic Information System</td></tr></table></body></html>");
				}
				answerHtml.add(appServicesHtml);
 				answerHtml.add(cityServicesHtml);
			}	
		return answerHtml;
	}


}
