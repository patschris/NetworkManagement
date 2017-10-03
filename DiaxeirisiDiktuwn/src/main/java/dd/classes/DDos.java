package dd.classes;

/* For DDosAttack GUI */
public class DDos {
	
	private int id;
	private String destination;
	private int port;
	private String protocol;
	
	public DDos(int id, String destination, int port, String protocol) {
		this.id = id;
		this.destination = destination;
		this.port = port;
		this.protocol = protocol;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestrination(String destination) {
		this.destination = destination;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}