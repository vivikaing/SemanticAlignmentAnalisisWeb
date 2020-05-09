package semantic.aligment.model;

import java.util.ArrayList;

public class Goal extends Concept{
	
	private ArrayList<Objective> objectives;
	
	public Goal() {
		super();
	}

	public Goal(String id, String name) {
		super(id, name);
	}

	public ArrayList<Objective> getObjectives() {
		return objectives;
	}

	public void setObjectives(ArrayList<Objective> objectives) {
		this.objectives = objectives;
	}

}
