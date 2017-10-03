package dd.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
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

import dd.classes.RouterFrequences;

public class ChannelAssignment extends JFrame {
	
	private static final long serialVersionUID = -6609417474269470328L;
	private JFrame mainFrame;
	private JTextArea textArea;
	private	Connection conn;
	private Statement stmt;
	
	public ChannelAssignment(Connection c,Statement s) {
		super("Channel Assignment");
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
		this.analyze();
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
		JLabel title = new JLabel("Channel Assignment");
		title.setLocation(300, 20);
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
	 Anoigoume to arxeio pou periexei ta access points pou exoume sxediasei kai sto sxima
	 tis parousiasis. Kathe access point thewroume oti exei 2 geitones: ton proigoumeno 
	 tou kai ton epomeno tou stin "kukliki" lista. Gia na apofugoume tis paremvoles 
	 frontizoume kathe triada geitonwn na exei ta kanalia 1, 6 kai 11.	
	  */
		try {
			String path = "src/main/java/dd/database/routers.txt";
			BufferedReader in = new BufferedReader(new FileReader(path));
			String line;
			String str ="Configuring channels ... \n\n";
			textArea.setText(str);
			int counter=0, channel=0;
			Queue<RouterFrequences> q = new LinkedList<RouterFrequences>();
			while((line = in.readLine()) != null) {
				counter++;
				String[] array = line.split(",");
				int mod=counter%3;
				if (mod==0) channel=1;
				else if (mod==1) channel=6;
				else channel=11;
				RouterFrequences rf = new RouterFrequences(array[0].trim(), Integer.parseInt(array[1].trim()), channel);
				q.add(rf);
			}
			in.close();
			String str1 = "Channel 1\n", str2 = "Channel 6\n", str3 = "Channel 11\n";
			int count1=0, count2=0, count3=0;
			while (q.isEmpty()==false) {
				RouterFrequences rf = q.remove();
				if (rf.getChannel()==1){
					count1++;
					if (count1==1) {
						str1+= rf.getName();
					}
					else {
						if (count1 % 6 ==0) str1+=", \n"+rf.getName();
						else str1+=", "+rf.getName();
					}
				}
				else if (rf.getChannel()==6){
					count2++;
					if (count2==1) {
						str2+= rf.getName();
					}
					else {
						if (count2 % 6==0) str2+=", \n"+rf.getName();
						else str2+=", "+rf.getName();
					}
				}
				else {
					count3++;
					if (count3==1) {
						str3+= rf.getName();
					}
					else {
						if (count3 % 6==0) str3+=", \n"+rf.getName();
						else str3+=", "+rf.getName();
					}
				}
			}
			str+=str1+"\n\n\n"+str2+"\n\n\n"+str3+"\n\n";
			textArea.setText(str);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}