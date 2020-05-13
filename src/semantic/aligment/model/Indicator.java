package semantic.aligment.model;

public class Indicator extends Concept{

	private String currentValue;
	private String targetValue;
	private String operator;
	private String unitOfMeasure;
	private String qolDimension;
	
	public Indicator() {
		super();
	}
	
	public Indicator(String id, String name) {
		super(id, name);
	}
	
	public String getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
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

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getQolDimension() {
		return qolDimension;
	}

	public void setQolDimension(String qolDimension) {
		this.qolDimension = qolDimension;
	}

}
