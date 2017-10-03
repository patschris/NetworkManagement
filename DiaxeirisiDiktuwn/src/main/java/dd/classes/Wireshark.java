package dd.classes;

/* In order to import into the database the data from Wireshark */
public class Wireshark {
	
	private String time;
	private String source;
	private int srcPort;
	private String destination;
	private int dstPort;
	private String info;
	private int length;
	private String protocol;
	private double deltaTimeDisplayed;
	private String protocolsInFrame;
	
	
	public Wireshark(String time, String source, int srcPort,
			String destination, int dstPort, String info, int length,
			String protocol, double deltaTimeDisplayed, String protocolsInFrame) {
		this.time = time;
		this.source = source;
		this.srcPort = srcPort;
		this.destination = destination;
		this.dstPort = dstPort;
		this.info = info;
		this.length = length;
		this.protocol = protocol;
		this.deltaTimeDisplayed = deltaTimeDisplayed;
		this.protocolsInFrame = protocolsInFrame;
	}

	@Override
	public String toString() {
		/* insert query to Wireshark table  */
		String s = "Insert into Wireshark (time,source,srcPort,destination,dstPort,info,length,protocol,deltaTimeDisplayed,protocolsInFrame) values (";
		s+="\""+time+"\",";
		s+="\""+source+"\",";
		s+=srcPort+",";
		s+="\""+destination+"\",";
		s+=dstPort+",";
		s+="\""+info+"\",";
		s+=length+",";
		s+="\""+protocol+"\",";
		s+=deltaTimeDisplayed+",";
		s+="\""+protocolsInFrame+"\");";
		return s;
	}
	
}