package semantic.aligment.model;

import java.util.ArrayList;

public class AppService extends Concept{
	
	public AppService() {
		super();
	}

	public AppService(String id, String name) {
		super(id, name);
	}
	
	public ArrayList<QoSAppService> getQosAppService() {
		return qosAppService;
	}

	public void setQosAppService(ArrayList<QoSAppService> qosAppService) {
		this.qosAppService = qosAppService;
	}

	private ArrayList<QoSAppService> qosAppService;

}

