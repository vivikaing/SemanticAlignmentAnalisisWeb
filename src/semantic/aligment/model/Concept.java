package semantic.aligment.model;

public abstract class Concept {
	
	private String id;
	private String name;
	
	public Concept() {
		this.id = "";
		this.name = "";
	}	
	
	public Concept(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

