package dd.classes;

/* In order to import into the database the data from Xirrus Wifi Inspector */
public class XirrusWifiInspector {

		private boolean connected;
		private String ssid;
		private int signaldbm;
		private String networkMode;
		private String defaultEncryption;
		private String defaultAuth;
		private String vendor;
		private String bssid;
		private String channel;
		private String frequency;
		private String networkType;
		private String adapterDescription;
		

		public XirrusWifiInspector(boolean connected, String ssid,
				int signaldbm, String networkMode, String defaultEncryption,
				String defaultAuth, String vendor, String bssid,
				String channel, String frequency, String networkType,
				String adapterDescription) {
			super();
			this.connected = connected;
			this.ssid = ssid;
			this.signaldbm = signaldbm;
			this.networkMode = networkMode;
			this.defaultEncryption = defaultEncryption;
			this.defaultAuth = defaultAuth;
			this.vendor = vendor;
			this.bssid = bssid;
			this.channel = channel;
			this.frequency = frequency;
			this.networkType = networkType;
			this.adapterDescription = adapterDescription;
		}

		@Override
		public String toString() {
			/* insert query to XirrusWifiInspector table  */
			String s = "Insert into XirrusWifiInspector (connected,ssid,signaldBm,networkMode,defaultEncryption,defaultAuth,vendor,bssid,channel,frequency,networkType,adapterDescription) values (";
			s+=connected+",";
			s+="\""+ssid+"\",";
			s+=signaldbm+",";
			s+="\""+networkMode+"\",";
			s+="\""+defaultEncryption+"\",";
			s+="\""+defaultAuth+"\",";
			s+="\""+vendor+"\",";
			s+="\""+bssid+"\",";
			s+="\""+frequency+"\",";
			s+="\""+channel+"\",";			s+="\""+networkType+"\",";
			s+="\""+adapterDescription+"\"";
			s+=");";
			return s;
		}		
}