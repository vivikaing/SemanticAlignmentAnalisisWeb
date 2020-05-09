package semantic.aligment.model;

public class QoSAppService extends Concept {
	

	private String monitoredValue;
	private String targetValue;
	private String operator;
	private String unitOfMeasure;
	
	
	public QoSAppService() {
		super();
	}

	public QoSAppService(String id, String name) {
		super(id, name);
	}
	
	public String getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMonitoredValue() {
		return monitoredValue;
	}

	public void setMonitoredValue(String monitoredValue) {
		this.monitoredValue = monitoredValue;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
}
