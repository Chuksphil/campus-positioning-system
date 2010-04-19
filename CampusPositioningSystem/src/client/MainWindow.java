package client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Shape;
import java.awt.geom.NoninvertibleTransformException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

import message.Response;

public class MainWindow extends JFrame {
	
	private Client client;  //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTextArea jTextArea = null;
	private JButton jButton1 = null;
	private JPanel jPanel2 = null;
	private JPanel MainPanel = null;
	private JLabel jLabel1 = null;
	private JButton jButton2 = null;
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {			
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), BorderLayout.NORTH);
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
			jPanel.add(getMainPanel(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJTextArea(), gridBagConstraints);
			jPanel1.add(getJButton1(), new GridBagConstraints());
			jPanel1.add(getJButton2(), new GridBagConstraints());
		}
		return jPanel1;
	}



	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}



	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("GO");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					try {
						String room = jTextArea.getText();
						Response resp;					
						resp = client.QueryRoom(room);					
						String message = resp.getMessage();					
						((MyPanel)MainPanel).SetForegroundShapes(message);
						MainPanel.repaint();
					} catch (Exception e1) {
						System.out.print(e1);
					}
				}
			});
		}
		return jButton1;
	}



	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("Enter Destination");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(jLabel1, gridBagConstraints1);
		}
		return jPanel2;
	}



	/**
	 * This method initializes MainPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainPanel() {
		if (MainPanel == null) {
			MainPanel = new MyPanel();
			MainPanel.setLayout(new GridBagLayout());
		}
		return MainPanel;
	}



	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("APs");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					String aps = client.GetAccessPoints();					
					String newAps = JOptionPane.showInputDialog("Enter Access Point Data", aps);
					client.SetAccessPoints(newAps);
					
				}
			});
		}
		return jButton2;
	}



	/**
	 * @param args
	 * @throws ParseException 
	 * @throws NoninvertibleTransformException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		MainWindow test = new MainWindow();
	    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    test.setVisible(true);
	    
	}

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public MainWindow() throws Exception {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		this.setSize(339, 228);
		this.setContentPane(getJPanel());
		this.setTitle("Frame");
		
		
		client = new Client(); 
		
		String floor3RoomsFile = "/home/innominate/Documents/School/DatabaseTechnologies/proj/nav_graphs/floor3.wkt";
		byte[] buffer = new byte[(int) new File(floor3RoomsFile).length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(floor3RoomsFile));
	    f.read(buffer);
	    String floor3Rooms = new String(buffer);

		

	    
	   // String testString = "LINESTRING (2226934.578197653 1373908.059633656, 2226936.629745924 1373907.6409503352, 2226936.8679826306 1373909.3086072817, 2226937.286283396 1373912.1530524876, 2226937.9087939844 1373916.3861244887, 2226938.741443068 1373922.8391548835, 2226939.6255619843 1373929.5584586505, 2226939.7822544216 1373930.7493211736, 2226940.411284227 1373934.6283383064, 2226948.5250697965 1373933.2472684223)";
	    
	    
	    ((MyPanel)MainPanel).SetBackgroundShapes(floor3Rooms);
	    //((MyPanel)MainPanel).SetForegroundShapes(testString);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
