package semantic.alignment.logic;

import java.util.ArrayList;

import semantic.aligment.model.CityService;
import semantic.aligment.model.Domain;

public class FormatRdf {
	
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
				+ "</td><td><a href=\"ApplicationServiceAlignmentServlet?idObjective="
				+ cityServices.get(j).getIdObjective()
				+ "\">View Details</a></td></tr>";	
			}	
		return cityServicesHtml;
	}

}
