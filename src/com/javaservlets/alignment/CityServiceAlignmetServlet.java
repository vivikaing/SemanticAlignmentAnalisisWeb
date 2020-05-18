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
import semantic.alignment.logic.ReadRdf;

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
		
		String realContextPath = getServletContext().getRealPath("");
		String cityServicesHtml = new String();
		cityServicesHtml = getCityServices(realContextPath, idObjective);
		
		
		PrintWriter out = response.getWriter();
		out.print("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"./css/tableStyle.css\"/> \r\n" + "</head>");
		out.println("<html><body><h3>Semantic Alignment Analysis from City Services</h3>"
				+ "<table><tr><th>Domains</th><th>City Services</th><th>Quality of Life Dimension</th>"
				+ "<th>Indicators</th><th>Target Value</th><th>Current Value</th><th>Application Services</th></tr>"
				//+ "<tr><td>Livability</td><td>Waste Management City Service</td><td>Environmental Quality</td>"
				//+ "<td>Number of bins not collected per neighbourhood</td><td align=\"center\">0</td><td bgcolor=\"#e8899b\" align=\"center\">5</td><td><a href=\"ApplicationServiceAlignmentServlet?idCityService=3\">View Details</a></td></tr></table></body></html>");
				+ cityServicesHtml
				+ "</table></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String getCityServices(String path, String idObjective) {
		String cityServicesHtml = new String();
		ReadRdf rdf = new ReadRdf(path);
		ArrayList<CityService> cityServices = rdf.findCityServices(idObjective);
				
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
				+ cityServices.get(j).getIndicator().getTargetValue()
				+ " "
				+ cityServices.get(j).getIndicator().getUnitOfMeasure()
				+ "</td><td>"
			    + cityServices.get(j).getIndicator().getCurrentValue()
			    + " "
			    + cityServices.get(j).getIndicator().getUnitOfMeasure()
				+ "</td><td><a href=\"ApplicationServiceAlignmentServlet?idObjective="
				+ idObjective
				+ "\">View Details</a></td></tr>";	
			}	
		return cityServicesHtml;
	}

	
}
