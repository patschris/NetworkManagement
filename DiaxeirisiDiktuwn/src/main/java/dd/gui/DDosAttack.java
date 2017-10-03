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
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import dd.classes.DDos;

public class DDosAttack extends JFrame {
	
	private static final long serialVersionUID = -6609417474269470328L;
	private JFrame mainFrame;
	private JTextArea textArea;
	private	Connection conn;
	private Statement stmt;
	
	public DDosAttack(Connection c,Statement s) {
		super("DDos Attack");
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
		JLabel title = new JLabel("DDos Attack");
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
				/*
				 Patwntas to koumpi, to parathuro auto katastrefetai kai ksanadimiourgeitai
				 to Problems Menu 
				*/
				mainFrame.setVisible(false);
				new ProblemsMenu(conn,stmt);
				mainFrame.dispose();
			}
		});
		this.getContentPane().add(backButton);
	}
	
	private void analyze() {
		/*
		 Ta dedomena exoun sullexthei se diaforetikes xronikes stigmes kai se diaforetika
		 diktua. Opote, ston algorithmo auto thewroume oti exoume DDos attack an exoume
		 80 sunexomena paketa me idia ip kai idio port proorismou kai to paketo einai 
		 idiou prwtokollou
		 */
		Queue<DDos> q = new LinkedList<DDos>();
		ResultSet rs = null;		
		try {
			rs = stmt.executeQuery("select id,destination,dstPort,protocol from Wireshark");
			int id,port;
			String dest,protocol;
			while (rs.next()) {
				id = rs.getInt("id");
				dest = rs.getString("destination");
				port = rs.getInt("dstPort");
				protocol = rs.getString("protocol");
				DDos ddos = new DDos(id,dest,port,protocol);
				q.add(ddos);
			}
			int counter=0;
			id=0;
			dest=new String();
			port=0;
			String str = new String();
			protocol=new String();
			boolean flag=false;
			while (q.isEmpty()==false) {
				DDos dd = q.remove();
				if (counter==0) {
					counter++;
					dest= dd.getDestination();
					port=dd.getPort();
					protocol=dd.getProtocol();
				}
				else {
					if (dd.getDestination().equals(dest) && dd.getPort()==port && dd.getProtocol().equals(protocol)) {
						counter++;
						if (counter==80) {
							flag=true;
							str+="Possible DDos attack on "+dd.getDestination()+" using " + dd.getProtocol() + " packets\n";
						}
					}
					else {
						counter=1;
						dest = dd.getDestination();
						port = dd.getPort();
						protocol=dd.getProtocol();
					}
				}
			}
			if (flag==false) {
				str="No DDos attack detected\n";
			}
			textArea.setText(str);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}