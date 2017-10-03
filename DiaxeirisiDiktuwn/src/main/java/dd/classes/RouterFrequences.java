package dd.classes;

/* For Channel Assignments GUI*/
public class RouterFrequences {

	String name;
	int ssid;
	int channel;
	
	public RouterFrequences(String name, int ssid, int channel) {
		this.name = name;
		this.ssid = ssid;
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}
}