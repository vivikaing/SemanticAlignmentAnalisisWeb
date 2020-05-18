package semantic.aligment.model;

import java.util.ArrayList;

public class AppService extends Concept{
	
	private WebService webService;
	
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

	public WebService getWebService() {
		return webService;
	}

	public void setWebService(WebService webService) {
		this.webService = webService;
	}

	private ArrayList<QoSAppService> qosAppService;

}

