package dd.classes;

/* In order to import into the database the data from Wireless NetView */
public class WirelessNetView {
	
	private String ssid;
	private float lastSignal;
	private float averageSignal;
	private int detectionCounter;
	private float perDetection;
	private boolean securityEnabled;
	private boolean connectable;
	private String authentication;
	private String cipher;
	private String phyTypes;
	private String firstDetectedOn;
	private String lastDetectedOn;
	private String MACaddress;
	private int rssi;
	private float channelFrequency_ghz;
	private int channelNumber;
	private String companyName;
	private float maximumSpeed_mbps;
	private String bssType;
	private boolean connected;
	
	public WirelessNetView(String ssid, float lastSignal, float averageSignal,
			int detectionCounter, float perDetection, boolean securityEnabled,
			boolean connectable, String authentication, String cipher,
			String phyTypes, String firstDetectedOn, String lastDetectedOn,
			String MACaddress, int rssi, float channelFrequency_ghz,
			int channelNumber, String companyName, float maximumSpeed_mbps,
			String bssType, boolean connected) {
		this.ssid = ssid;
		this.lastSignal = lastSignal;
		this.averageSignal = averageSignal;
		this.detectionCounter = detectionCounter;
		this.perDetection = perDetection;
		this.securityEnabled = securityEnabled;
		this.connectable = connectable;
		this.authentication = authentication;
		this.cipher = cipher;
		this.phyTypes = phyTypes;
		this.firstDetectedOn = firstDetectedOn;
		this.lastDetectedOn = lastDetectedOn;
		this.MACaddress = MACaddress;
		this.rssi = rssi;
		this.channelFrequency_ghz = channelFrequency_ghz;
		this.channelNumber = channelNumber;
		this.companyName = companyName;
		this.maximumSpeed_mbps = maximumSpeed_mbps;
		this.bssType = bssType;
		this.connected = connected;
	}

	@Override
	public String toString() {
		/* insert query to WirelessNetView table  */
		String s = new String();
		s+="Insert into WirelessNetView (ssid,lastSignal,averageSignal,detectionCounter,perDetection,securityEnabled,connectable,authentication,cipher,phyTypes,firstDetectedOn,lastDetectedOn,MACaddress,rssi,channelFrequencyGHz,channelNumber,companyName,maximumSpeedMbps,bssType,connected) values ";
		s+="(";
		s+="\""+ssid+"\",";
		s+=lastSignal+",";
		s+=averageSignal+",";
		s+=detectionCounter+",";
		s+=perDetection+",";
		s+=securityEnabled+",";;
		s+=connectable+",";
		s+="\""+authentication+"\",";
		s+="\""+cipher+"\",";
		s+="\""+phyTypes+"\",";
		s+="\""+firstDetectedOn+"\",";
		s+="\""+lastDetectedOn+"\",";
		s+="\""+MACaddress+"\",";
		s+=rssi+",";
		s+=channelFrequency_ghz+",";
		s+=channelNumber+",";
		s+="\""+companyName+"\",";
		s+=maximumSpeed_mbps+",";
		s+="\""+bssType+"\",";
		s+=connected;
		s+=");";
		return s;
	}		
}