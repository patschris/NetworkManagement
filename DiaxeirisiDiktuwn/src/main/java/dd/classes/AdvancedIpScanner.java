package dd.classes;

/* In order to import into the database the data from Advanced IP Scanner */
public class AdvancedIpScanner {

	private String status;
	private String name;
	private String ip;
	private String manufacturer;
	private String mac;
	private boolean hasHttp;
	private boolean isHttp8080;
	private String httpTitle;
	private String httpTitleFull;
	private boolean hasHttps;
	private boolean hasFtp;
	private boolean hasRdp;
	
	public AdvancedIpScanner(String status, String name, String ip,
			String manufacturer, String mac, boolean hasHttp,
			boolean isHttp8080, String httpTitle, String httpTitleFull,
			boolean hasHttps, boolean hasFtp, boolean hasRdp) {
		super();
		this.status = status;
		this.name = name;
		this.ip = ip;
		this.manufacturer = manufacturer;
		this.mac = mac;
		this.hasHttp = hasHttp;
		this.isHttp8080 = isHttp8080;
		this.httpTitle = httpTitle;
		this.httpTitleFull = httpTitleFull;
		this.hasHttps = hasHttps;
		this.hasFtp = hasFtp;
		this.hasRdp = hasRdp;
	}

	@Override
	public String toString() {
		/* insert query to AdvancedIpScanner table  */
		String s= "Insert into AdvancedIpScanner (status,name,ip,manufacturer,mac,hasHttp,"
				+ "isHttp8080,httpTitle,httpTitleFull,hasHttps,hasFtp,hasRdp) values (";
		s+= "\""+status+"\",";
		s+= "\""+name+"\",";
		s+= "\""+ip+"\",";
		s+= "\""+manufacturer+"\",";
		s+= "\""+mac+"\",";
		s+= hasHttp+",";
		s+= isHttp8080+",";
		s+= "\""+httpTitle+"\",";
		s+= "\""+httpTitleFull+"\",";
		s+= hasHttps+",";
		s+= hasFtp+",";
		s+= hasRdp+");";
		return s;
	}
}