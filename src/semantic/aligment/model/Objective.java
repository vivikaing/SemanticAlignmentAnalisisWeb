package semantic.aligment.model;

import java.util.ArrayList;

public class Objective extends Concept {
	
	private ArrayList<CityService> cityServices;
	private Indicator indicator;
	
	public Objective() {
		super();
	}
	
	public Objective(String id, String name) {
		super(id, name);
	}
	
	public ArrayList<CityService> getCityServices() {
		return cityServices;
	}

	public void setCityServices(ArrayList<CityService> cityServices) {
		this.cityServices = cityServices;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}
}
