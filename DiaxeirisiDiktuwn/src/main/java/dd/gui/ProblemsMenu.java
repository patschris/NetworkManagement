package dd.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class ProblemsMenu extends JFrame {

	private static final long serialVersionUID = -6609417474269470328L;
	private JFrame mainFrame;
	@SuppressWarnings("rawtypes")
	private JList list;
	private	Connection conn;
	private Statement stmt;
	
	public ProblemsMenu(Connection c, Statement s) {
		super("Possible problems");
		this.mainFrame=this;
		this.conn=c;
		this.stmt=s;
		/*Fernoume to parathuro sto kentro tis othonis*/
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-400, dim.height/2-200);
		this.setLayout(null); /*Xwris etoimo layout*/
		this.setSize(800, 400); /*dinoume diastaseis sto parathuro*/
		this.setResizable(false); /*to parathuro de mporei na allaksei megethos*/
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /*otan kleisei to parathuro, teleiwnei to programma*/
		this.addWindowListener(new WindowAdapter() {
			/* sto kleisimo tou parathurou kleinoume to connection sti vasi
			   kai teleiwnei to programma 
			 */
			public void windowClosing(WindowEvent windowEvent){
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
		this.addList();
		this.addButton();
		this.setVisible(true); /*kanoume to parathuro orato*/
	}
	
	private void addTitle(){
		/*Vazoume titlo sto parathuro*/
		JLabel title = new JLabel("Select your problem");
		title.setLocation(320, 20);
		title.setSize(400, 20);
		title.setFont(new Font("Serif", Font.BOLD, 15));
		this.getContentPane().add(title);	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addList() {
		/*
		 Dimiourgoume lista epilogis gia pithana provlimata. Kathe fora mporei na
		 epilegei mia mono periptwsi.
		 */
		DefaultListModel model = new DefaultListModel();
		model.addElement("Acess Point Fault");
		model.addElement("DHCP failure");
		model.addElement("Weak Signal");
		model.addElement("Signal Analysis");
		model.addElement("Connected Devices");
		model.addElement("Packet Analysis");
		model.addElement("Retransmissions/Lost Segments");
		model.addElement("Authentication");
		model.addElement("Encryption");
		model.addElement("Channel Analysis");
		model.addElement("Channel Assignment");
		model.addElement("DDos Attack");
		list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(90, 60, 620, 260);
		this.getContentPane().add(scrollPane);
	}
	
	private void addButton() {
		/*
		 	Back: epistrofi sto Problems Menu
		 	Check: gia epilogi apo to menu provlimatwn
		 */
		JButton backButton = new JButton();
		backButton.setText("Back");
		backButton.setSize(100,30);
		backButton.setLocation(250, 330);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);
				new ImportWindow(conn, stmt);
				mainFrame.dispose();
			}
		});
		this.getContentPane().add(backButton);
		JButton checkButton = new JButton();
		checkButton.setText("Check");
		checkButton.setSize(100, 30);
		checkButton.setLocation(450, 330);
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(false);	
				String choice = (String) list.getSelectedValue();
				if (choice==null) {
					JOptionPane.showMessageDialog(mainFrame,"Select an option\nThen press the button");
					mainFrame.setVisible(true);
					return;
				}
				else if (choice.equals("DHCP failure")) {
					new DHCPfailure(conn, stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Acess Point Fault")){
					new AccessPointFault(conn,stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Weak Signal")){
					new WeakSignal(conn, stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Signal Analysis")) {
					new SignalAnalysis(conn, stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Connected Devices")) {
					new ConnectedDevices(conn, stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Retransmissions/Lost Segments")) {
					new Retransmission(conn,stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Packet Analysis")){	
					new PacketAnalysis(conn,stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Authentication")){
					new Authentication(conn,stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Encryption")) {
					new Encryption(conn,stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Channel Analysis")) {
					new ChannelAnalysis(conn, stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("Channel Assignment")) {
					new ChannelAssignment(conn, stmt);
					mainFrame.dispose();
				}
				else if (choice.equals("DDos Attack")) {
					new DDosAttack(conn, stmt);
					mainFrame.dispose();
				}
				else {
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
			}
		});
		this.getContentPane().add(checkButton);
	}
}