package dd.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import dd.classes.AdvancedIpScanner;
import dd.classes.Retransmission;
import dd.classes.WirelessNetView;
import dd.classes.Wireshark;
import dd.classes.XirrusWifiInspector;

public class ImportWindow extends JFrame {

	private static final long serialVersionUID = 5351939491450310681L;
	private JFrame mainMenu;
	private JTextArea textArea; 
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel model;
	@SuppressWarnings("rawtypes")
	private JComboBox box;
	private	Connection conn;
	private Statement stmt;


	public ImportWindow(Connection c,Statement s) {
		super("Import data");
		this.conn=c;
		this.stmt=s;
		/*Fernoume to parathuro sto kentro tis othonis*/
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-400, dim.height/2-200);
		this.mainMenu = this;
		this.setLayout(null); /*Xwris etoimo layout*/
		this.setSize(800, 400); /*dinoume diastaseis sto parathuro*/
		this.setResizable(false); /*to parathuro de mporei na allaksei megethos*/
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /*otan kleisei to parathuro, teleiwnei to programma*/
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				/* sto kleisimo tou parathurou kleinoume to connection sti vasi
				   kai teleiwnei to programma 
				 */
				if (stmt!=null) {
					try {
						stmt.close();
					}
					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if (conn!=null) {
					try {
						conn.close();
					} 
					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);
			}        
		});
		this.addTitles();
		this.addBrowse();
		this.addDropDownMenu();
		this.addButtons();
		this.setVisible(true); /*kanoume to parathuro orato*/
	}

	private void addTitles() {
		/*Vazoume titlous sto parathuro*/
		JLabel title = new JLabel("Browse the file you want to insert");
		title.setSize(400, 50);
		title.setLocation(260, 10);
		this.getContentPane().add(title);
		JLabel subtitle = new JLabel("From which program did you select data?");
		subtitle.setSize(400,50);
		subtitle.setLocation(240,30);
		this.getContentPane().add(subtitle);
	}

	private void addBrowse() {
		/* 
		 Prosthetoume ena koumpi gia na kanoume browse to arxeio. Patwntas to koumpi
		 anoigei enas file chooser wste na epilegei to arxeio pou theloume na 
		 eisagoume sti vasi. To path emfanizetai se mia text area dipla sto koumpi
		 browse
		 */
		JButton browseButton = new JButton();
		browseButton.setSize(90,30);
		browseButton.setLocation(680,120);
		browseButton.setText("Browse");
		browseButton.setActionCommand("Browse");
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				int ret = fileopen.showDialog(null, "Browse");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileopen.getSelectedFile();
					textArea.setText(file.toString());
				}
			}
		});
		this.getContentPane().add(browseButton);
		JLabel browseLabel = new JLabel("Browse file:");
		browseLabel.setSize(100,20);
		browseLabel.setLocation(10,125);
		this.getContentPane().add(browseLabel);
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setLocation(130,120);
		scrollPane.setSize(535,30);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(scrollPane);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addDropDownMenu() {
		/*
		  Prosthetoume ena dropdown menu gia na epileksoume se poion pinaka tha valoume 
		  to arxeio pou kaname browse.
		 */
		JLabel DropDownLabel = new JLabel("Select Program:");
		DropDownLabel.setSize(120,20);
		DropDownLabel.setLocation(10,185);
		this.getContentPane().add(DropDownLabel);
		model = new DefaultComboBoxModel();
		box = new JComboBox(model);
		box.setBackground(Color.WHITE);
		model.addElement("Advanced Ip Scanner");
		model.addElement("Wireless NetView");
		model.addElement("Xirrus Wifi Inspector");
		model.addElement("Wireshark");
		model.addElement("Retransmission");
		box.setSelectedIndex(-1); 
		box.setMaximumRowCount(4);
		box.setEditable(false);
		box.setSize(415,30);
		box.setLocation(250,185);
		this.getContentPane().add(box);
	}

	private void addButtons() {
		/*
		 To continue odigei apeutheias sto menu twn provlimatwn.
		 To clear katharizei tis epiloges sto browse kai sto dropdown.
		 To submit kanei eisagwgei ton dedomenwn. Yparxei elegxos gia to an ta dedomena
		 einai egkura.
		 */
		JButton continueButton = new JButton();
		continueButton.setSize(120,30);
		continueButton.setLocation(650,300);
		continueButton.setText("Continue");
		continueButton.setActionCommand("Continue");
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainMenu.setVisible(false);
				new ProblemsMenu(conn, stmt);
				mainMenu.dispose();
			}
		});
		this.getContentPane().add(continueButton);
		JButton submitButton = new JButton();
		submitButton.setSize(90,30);
		submitButton.setLocation(300,300);
		submitButton.setText("Submit");
		submitButton.setActionCommand("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainMenu.setVisible(false);
				String path = textArea.getText();
				if (path.isEmpty()) {
					JOptionPane.showMessageDialog(mainMenu,"Browse file");
					mainMenu.setVisible(true);
					return;
				}
				String program = (String)box.getSelectedItem();
				if (program==null || program.isEmpty()) {
					JOptionPane.showMessageDialog(mainMenu,"Select program");
					mainMenu.setVisible(true);
					return;
				}

				if (program.equals("Advanced Ip Scanner")){
					if (path.contains(".xml")==false) {
						JOptionPane.showMessageDialog(mainMenu,"For Advanced Ip Scanner select an xml file");
						textArea.setText(new String());
						box.setSelectedIndex(-1);
						mainMenu.setVisible(true);
						return;
					}
				}
				else if (program.equals("Wireless NetView") || program.equals("Xirrus Wifi Inspector")) {
					if (path.contains(".csv")==false) {
						JOptionPane.showMessageDialog(mainMenu,"For " + program + " select a csv file");
						textArea.setText(new String());
						box.setSelectedIndex(-1);
						mainMenu.setVisible(true);
						return;
					}
				}
				int retval = insertIntoDatabase(path, program);
				/*
				 Afou patithei to submit kai ginei i eisagwgi twn dedomenwn sti vasi 
				 emfanizetai parathuro pou rwta to xristi an thelei na sunexisei tin
				 eisagwgi dedomenwn.
				 */
				if (retval==0) {
				/* An apantisei arnhtika, tote fortwnetai to menu provlimatwn */
					new ProblemsMenu(conn,stmt);
					mainMenu.dispose();
					return;
				}
				else if (retval==1 || retval==2) {
				/*An apantisei thetika, epistrefei sto menu eisagwgis kai sunexizei
				  na fortwnei fortwnei dedomena
				 */
					textArea.setText(new String());
					box.setSelectedIndex(-1); 
				}
				mainMenu.setVisible(true);
			}
		});
		this.getContentPane().add(submitButton);
		JButton clearButton = new JButton();
		clearButton.setSize(90,30);
		clearButton.setLocation(420,300);
		clearButton.setText("Clear");
		clearButton.setActionCommand("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText(new String());
				box.setSelectedIndex(-1); 
			}
		});
		this.getContentPane().add(clearButton);
	}

	private int showConfirmDialog(){
		/*
			http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanetopromptuserconfirmationademo.htm
			Emfanizetai parathuro epivevaiwsis opou o xristis apofasizei an tha eisagei
			kai alla dedomena. Epistrefei katallili apantisi analoga me tin epilogi tou
			xristi
		 */
		int response = JOptionPane.showConfirmDialog(null, "Do you want to import more data into database?", "Confirm",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.NO_OPTION)	return 0;
		else if (response == JOptionPane.YES_OPTION)	return 1;
		else if (response == JOptionPane.CLOSED_OPTION)	return 2;
		else	return 3;
	}


	private int insertIntoDatabase(String path, String program) {
	/*
	 	Analoga me ton pinaka eisagwgis, epilegoume tin katallili sunartisi gia na
	 	kanei tin eisagwgi sti vasi kai epistrefei to apotelesma tis showConfirmDialog 
	 */
		if (program.equals("Advanced Ip Scanner")) insertFromAdvancedIpScanner(path);
		else if (program.equals("Wireless NetView")) insertFromWirelessNetView(path);
		else if (program.equals("Xirrus Wifi Inspector")) insertXirrusWifiInspector(path);
		else if (program.equals("Wireshark")) insertFromWireshark(path);
		else if (program.equals("Retransmission")) insertFromRetransmission(path);
		else System.out.println("Something went wrong!(insertIntoDatabase)");
		return showConfirmDialog();
	}


	private void insertFromAdvancedIpScanner(String path) {
		/*
		  To arxeio pou periexei ta apotelesmata tou programmatos Advanced Ip Scanner
		  einai se morfi xml. Kanoume parse to arxeio grammi-grammi, tou ftiaxnoume 
		  object tis klasis AdvancedIpScanner kai mesw tis toString() ftiaxnoume kai 
		  ekteloume to insert query tis vasis.
		 */
		try {
			InputStream inputStream= new FileInputStream(path);
			Reader reader = new InputStreamReader(inputStream,"UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			NodeList nodeLst = doc.getElementsByTagName("row");
			String status,name,ip,manufacturer,mac,httpTitle,httpTitleFull;
			boolean hasHttp,isHttp8080,hasHttps,hasFtp,hasRdp;
			for (int i = 0 ; i < nodeLst.getLength() ; i++) {
				Node node = nodeLst.item(i);
				Element elem = (Element) node;
				status = elem.getAttribute("status");
				if (status.equals("alive")==false) continue;
				name = elem.getAttribute("name");
				ip = elem.getAttribute("ip");
				manufacturer = elem.getAttribute("manufacturer");
				mac = elem.getAttribute("mac");
				if (elem.getAttribute("has_http").equals("0")) hasHttp = false;
				else hasHttp=true;
				if (elem.getAttribute("is_http8080").equals("0")) isHttp8080 = false;
				else isHttp8080=true;
				if (elem.getAttribute("has_https").equals("0")) hasHttps=false;
				else hasHttps=true;
				if (elem.getAttribute("has_ftp").equals("0")) hasFtp=false;
				else hasFtp=true;
				if (elem.getAttribute("has_rdp").equals("0")) hasRdp=false;
				else hasRdp=true;
				httpTitle = elem.getAttribute("http_title");
				httpTitleFull = elem.getAttribute("http_title_full");
				AdvancedIpScanner ais = new AdvancedIpScanner(status, name, ip, manufacturer, mac, hasHttp, isHttp8080, httpTitle, httpTitleFull, hasHttps, hasFtp, hasRdp);					
				this.stmt.executeUpdate(ais.toString());										
			}
		} 
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SQLException es) {
			es.printStackTrace();
		}
	}

	private void insertFromWirelessNetView(String path) {
		/*
		  To arxeio pou periexei ta apotelesmata tou programmatos Wireless Net View
		  einai se morfi csv. Kanoume parse to arxeio grammi-grammi, tou ftiaxnoume 
		  object tis klasis WirelessNetView kai mesw tis toString() ftiaxnoume kai 
		  ekteloume to insert query tis vasis.
		 */
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = new String();
			while ((line = br.readLine()) != null) {
				String[] array = line.split(",");
				String ssid = array[0];
				String[] array2 = array[1].split("%");
				float lastSignal = Float.parseFloat(array2[0]);
				array2 = array[2].split("%");
				float averageSignal = Float.parseFloat(array2[0]);
				int detectionCounter = Integer.parseInt(array[3]);
				array2 = array[4].split("%");
				float perDetection = Float.parseFloat(array2[0]);
				boolean securityEnabled = false;
				if (array[5].equals("Yes")) securityEnabled=true;
				boolean connectable = false;
				if (array[6].equals("Yes")) connectable=true;
				String authentication=array[7];
				String cipher=array[8];
				String phyTypes = array[9];
				String firstDetectedOn = array[10];
				String lastDetectedOn = array[11];
				String MACaddress = array[12];
				int rssi = Integer.parseInt(array[13]);
				array2 = array[14].split(" ");
				float channelFrequency_ghz = Float.parseFloat(array2[0]);
				int channelNumber = Integer.parseInt(array[15]);
				String companyName = array[16];
				array2 = array[17].split(" ");
				float maximumSpeed_mbps = Float.parseFloat(array2[0]);
				String bssType = array[18];
				boolean connected=false;
				if (array[19].equals("Yes")) connected=true;
				WirelessNetView wnv = new WirelessNetView(ssid, lastSignal, averageSignal, detectionCounter, perDetection, securityEnabled, connectable, authentication, cipher, phyTypes, firstDetectedOn, lastDetectedOn, MACaddress, rssi, channelFrequency_ghz, channelNumber, companyName, maximumSpeed_mbps, bssType, connected);
				this.stmt.executeUpdate(wnv.toString());										

			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (SQLException es) {
			es.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void insertXirrusWifiInspector(String path) {
		/*
		  To arxeio pou periexei ta apotelesmata tou programmatos Xirrus Wifi Inspector
		  einai se morfi csv. Kanoume parse to arxeio grammi-grammi, tou ftiaxnoume 
		  object tis klasis XirrusWifiInspector kai mesw tis toString() ftiaxnoume kai 
		  ekteloume to insert query tis vasis.
		 */
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			boolean connected;
			String ssid,networkMode,defaultEncryption,defaultAuth,vendor,bssid,channel,networkType,adapterDescription,frequency;
			int signaldbm;
			String[] array;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) continue;
				array=line.split(",");
				if (array[0].equals("True")) connected=true;
				else connected = false;
				ssid = array[1];
				signaldbm = Integer.parseInt(array[2]);
				networkMode = array[3];
				defaultEncryption = array[4];
				defaultAuth = array[5];
				vendor = array[6];
				bssid = array[7];
				channel = array[8];
				frequency = array[9];
				networkType = array[10];
				adapterDescription = array[11];
				XirrusWifiInspector xwi = new XirrusWifiInspector(connected, ssid, signaldbm, networkMode, defaultEncryption, defaultAuth, vendor, bssid, channel, frequency, networkType, adapterDescription);
				this.stmt.executeUpdate(xwi.toString());										
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (SQLException es) {
			es.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void insertFromWireshark(String path) {
		/*
		  To arxeio pou periexei ta apotelesmata tou programmatos Wireshark einai se
		  morfi csv. Kanoume parse to arxeio grammi-grammi, tou ftiaxnoume object tis 
		  klasis Wireshark kai mesw tis toString() ftiaxnoume kai ekteloume to insert 
		  query tis vasis.
		 */
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			String[] array;
			String time,source,destination,protocol,info,protocolsInFrame;
			int srcPort,dstPort, length;
			double deltaTimeDisplayed;
			while ((line = br.readLine()) != null) {
				array = line.split("\",\"");
				time = array[0].replaceAll("\"", "").trim();
				source = array[1].replaceAll("\"", "").trim();
				array[2]=array[2].replaceAll("\"", "").trim();
				if (array[2].isEmpty()) srcPort=-1;
				else srcPort=Integer.parseInt(array[2]);
				destination=array[3].replaceAll("\"", "").trim();
				info=array[4].replaceAll("\"", "").trim();
				array[5]=array[5].replaceAll("\"", "").trim();
				if (array[5].isEmpty()) dstPort=-1;
				else dstPort=Integer.parseInt(array[5]);
				length=Integer.parseInt(array[6].replaceAll("\"", "").trim());				
				protocol = array[7].replaceAll("\"", "").trim();						
				deltaTimeDisplayed = Double.parseDouble(array[8].replaceAll("\"", "").trim());		
				protocolsInFrame = array[9].replaceAll("\"", "").trim();
				Wireshark ws = new Wireshark(time, source, srcPort, destination, dstPort, info, length, protocol, deltaTimeDisplayed, protocolsInFrame);
				this.stmt.executeUpdate(ws.toString());										
			}

		}
		catch (SQLException es) {
			es.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void insertFromRetransmission(String path) {
		/*
		  To arxeio pou periexei ta apotelesmata tou programmatos Wireshark pou afora
		  omws mono tis anametadoseis paketwnv einai se morfi csv kai tha apothikeutei
		  ston pinaka Retransmissions tis vasis. Kanoume parse to arxeio grammi-grammi, 
		  tou ftiaxnoume object tis klasis Retransmission kai mesw tis toString() 
		  ftiaxnoume kai ekteloume to insert query tis vasis.
		 */
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			String[] array;
			String time,source,destination,protocol,info,protocolsInFrame;
			int srcPort,dstPort, length;
			double deltaTimeDisplayed;
			while ((line = br.readLine()) != null) {
				array = line.split("\",\"");
				time = array[0].replaceAll("\"", "").trim();
				source = array[1].replaceAll("\"", "").trim();
				array[2]=array[2].replaceAll("\"", "").trim();
				if (array[2].isEmpty()) srcPort=-1;
				else srcPort=Integer.parseInt(array[2]);
				destination=array[3].replaceAll("\"", "").trim();
				info=array[4].replaceAll("\"", "").trim();
				array[5]=array[5].replaceAll("\"", "").trim();
				if (array[5].isEmpty()) dstPort=-1;
				else dstPort=Integer.parseInt(array[5]);
				length=Integer.parseInt(array[6].replaceAll("\"", "").trim());				
				protocol = array[7].replaceAll("\"", "").trim();						
				deltaTimeDisplayed = Double.parseDouble(array[8].replaceAll("\"", "").trim());		
				protocolsInFrame = array[9].replaceAll("\"", "").trim();
				Retransmission ret = new Retransmission(time, source, srcPort, destination, dstPort, info, length, protocol, deltaTimeDisplayed, protocolsInFrame);
				this.stmt.executeUpdate(ret.toString());										
			}

		}
		catch (SQLException es) {
			es.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}