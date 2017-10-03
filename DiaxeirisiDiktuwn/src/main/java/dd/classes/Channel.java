package dd.classes;

/* For Channel Analysis graphic */
public class Channel {

	private String ssid;
	private int channel;
	
	public Channel(String ssid, int channel) {
		this.ssid = ssid;
		this.channel = channel;
	}
	
	public String getSsid() {
		return ssid;
	}
	
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	
	public int getChannel() {
		return channel;
	}
	
	public void setChannel(int channel) {
		this.channel = channel;
	}	
}