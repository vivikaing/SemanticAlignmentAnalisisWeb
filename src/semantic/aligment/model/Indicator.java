package semantic.aligment.model;

import java.util.ArrayList;

public class Indicator extends Concept{

	private String currentValue;
	private String targetValue;
	private String operator;
	private String operatorText;
	private String unitOfMeasure;
	private Boolean red_indicator;

	private ArrayList<QoLDimension> qolDimensions;
	
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

	public ArrayList<QoLDimension> getQolDimensions() {
		return qolDimensions;
	}

	public void setQolDimensions(ArrayList<QoLDimension> qolDimensions) {
		this.qolDimensions = qolDimensions;
	}

	public Boolean getRed_indicator() {
		return red_indicator;
	}

	public void setRed_indicator(Boolean red_indicator) {
		this.red_indicator = red_indicator;
	}

	public String getOperatorText() {
		return operatorText;
	}

	public void setOperatorText(String operatorText) {
		this.operatorText = operatorText;
	}

}
