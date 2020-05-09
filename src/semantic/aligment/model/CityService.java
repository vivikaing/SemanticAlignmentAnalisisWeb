package semantic.aligment.model;

import java.util.ArrayList;

public class CityService extends Concept {
	
	private ArrayList<Domain> domains;

	private ArrayList<AppService> appServices;
	
	private Indicator indicator;
	
	public CityService() {
		super();
	}
	
	public CityService(String id, String name) {
		super(id, name);
	}

	public ArrayList<Domain> getDomains() {
		return domains;
	}

	public void setDomains(ArrayList<Domain> domains) {
		this.domains = domains;
	}

	public ArrayList<AppService> getAppServices() {
		return appServices;
	}

	public void setAppServices(ArrayList<AppService> appServices) {
		this.appServices = appServices;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
}

