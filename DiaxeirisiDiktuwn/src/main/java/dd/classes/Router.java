package dd.classes;

/* For Connected Devices GUI*/
public class Router {

	private int id;
	private String name;
	
	public Router(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Router [id=" + id + ", name=" + name + "]";
	}	
}