package dd.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class SignalAnalysis extends JFrame {

	private static final long serialVersionUID = -6609417474269470328L;
	private JFrame mainFrame;
	private JTextArea textArea;
	private	Connection conn;
	private Statement stmt;
	
	public SignalAnalysis (Connection c, Statement s){
		super("Signal Analysis");
		this.mainFrame = this;
		this.conn = c;
		this.stmt = s;
		/*Fernoume to parathuro sto kentro tis othonis*/
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-400, dim.height/2-200);
		this.setLayout(null); /*Xwris etoimo layout*/
		this.setSize(800, 400); /*dinoume diastaseis sto parathuro*/
		this.setResizable(false); /*to parathuro de mporei na allaksei megethos*/
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /*otan kleisei to parathuro, teleiwnei to programma*/
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
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
		this.addTitle();
		this.textAreaLabel();
		this.addButton();
		this.setVisible(true); /*kanoume to parathuro orato*/
		new Thread(
		/* ksekinaei to thread pou tha gemisei to parathuro me apotelesmata*/
				new Runnable() {
					public void run() {
						analyze();
					}
				}).start();
	}
	
	private void addTitle() {
		/*Vazoume titlo sto parathuro*/
		JLabel title = new JLabel("Signal Analysis");
		title.setLocation(330, 20);
		title.setSize(400, 20);
		title.setFont(new Font("Serif", Font.BOLD, 15));
		this.getContentPane().add(title);	
	}

	private void textAreaLabel() {
		/* 
		Vazoume sto frame mia text area opou tha emfanistoun ta apotelesmata
		Prosthetoume scrollbars wste na mporoume na doume ta apotelesmata	
		*/
		JLabel areaLabel = new JLabel();
		areaLabel.setLayout(new BorderLayout());
		areaLabel.setSize(600, 250);
		areaLabel.setLocation(100, 50);
		textArea = new JTextArea();
		textArea.setText("Loading....");
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		areaLabel.add(scrollPane);
		this.getContentPane().add(areaLabel);
	}
	
	private void addButton() {
		/*
		  Vazoume koumpi back gia na gurnaei sto vasiko menu provlimatwn (Problems Menu) 
		*/
		JButton backButton = new JButton();
		backButton.setSize(90,30);
		backButton.setLocation(350,330);
		backButton.setText("Back");
		backButton.setActionCommand("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				new ProblemsMenu(conn,stmt);
				mainFrame.dispose();
			}
		});
		this.getContentPane().add(backButton);
	}
	
	private void analyze() {
		/*
		 Pairnei tin isxu tou simatos pou dinetai apo to programma Xirrus kai emfanizei
		 se pososto tin poiotita tou simatos
		 */
		String onScreen = "Signal quality of access points in percentage : \n\n";
		ResultSet rs=null;
		try {
			rs = stmt.executeQuery("select ssid,signaldBm,bssid from XirrusWifiInspector");
			while (rs.next()){
				String ssid = rs.getString("ssid");
				int rssi = rs.getInt("signaldBm");
				String mac = rs.getString("bssid");
				if (rssi>-50) {
					onScreen+=ssid+" ( "+mac+" ) -----> 100% (High Quality)\n";  
				}
				else if (rssi<-100) {
					onScreen+=ssid+" ( "+mac+" ) -----> 0% (Unusable Quality)\n";  
				}
				else {
					int percentage = 2*(100+rssi);
					onScreen+=ssid+" ( "+mac+" ) -----> " + percentage + "% ";
					if (percentage>=80) onScreen+="(High Quality)\n";
					else if (percentage<80 && percentage >= 50) onScreen+="(Medium Quality)\n";
					else if (percentage<50 && percentage>=25) onScreen+="(Low Quality)\n";
					else if (percentage<25 && percentage>=10) onScreen+="(Poor Quality)\n";
					else onScreen+="(Unusable Quality)\n";
				}
			}
			rs.close();
			textArea.setText(onScreen);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}