package setup.rooms;

import gui.MyPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import setup.fingerprinting.FingerPrintFile;
import setup.path.Node;
import setup.path.PathsFile;
import util.AccessPoint;
import util.Config;
import util.Wireless;

import com.vividsolutions.jts.io.ParseException;
import javax.swing.JTextArea;
import java.awt.Dimension;

public class RoomsWindow extends JFrame {	
	private PathsFile pathsFile;  
	private RoomsFile roomsFile;
	private Room room;
	
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JPanel MainPanel = null;
	private JLabel statusLabel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel4 = null;
	private JLabel jLabel1 = null;
	private JTextField pathsFileText = null;
	private JButton browsePathsFile = null;

	private JPanel jPanel3 = null;


	private JLabel jLabel = null;


	private JTextField roomsFileText = null;


	private JButton browseRoomsFile = null;


	private JLabel jLabel3 = null;


	private JPanel jPanel6 = null;


	private JTextField tagsField = null;
	private JButton jButton = null;


	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {			
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
			jPanel.add(getMainPanel(), BorderLayout.CENTER);
			jPanel.add(getJPanel1(), BorderLayout.NORTH);
		}
		return jPanel;
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
			statusLabel = new JLabel();
			statusLabel.setText("Select Paths File");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(statusLabel, gridBagConstraints1);
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
			MainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) 
				{
					try 
					{
						if (pathsFile == null)
						{
							JOptionPane.showMessageDialog(null, "Select paths file first.", "Select paths file first.", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						Point2D p = ((MyPanel)MainPanel).GetViewport().toModelPoint(e.getPoint());
						Node node = pathsFile.NearestNode(p.getX(), p.getY());
							
						room = roomsFile.GetRooms().get(node.getID());
						if (room == null)
						{
							room = new Room();
							roomsFile.GetRooms().put(node.getID(), room);
						}
												
						tagsField.setText(room.getTags());
						
						
						roomsFile.ToFile(roomsFileText.getText());
						
					}					
					catch (Exception e2) 
					{
					}
				}
			});
		}
		return MainPanel;
	}



	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.Y_AXIS));
			jPanel1.add(getJPanel4(), null);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel6(), null);
		}
		return jPanel1;
	}



	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.weightx = 1.0;
			jLabel1 = new JLabel();
			jLabel1.setText("Paths File:");
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.add(jLabel1, new GridBagConstraints());
			jPanel4.add(getPathsFileText(), gridBagConstraints7);
			jPanel4.add(getBrowsePathsFile(), new GridBagConstraints());
		}
		return jPanel4;
	}



	/**
	 * This method initializes pathsFileText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPathsFileText() {
		if (pathsFileText == null) {
			pathsFileText = new JTextField();
			pathsFileText.setEditable(false);
		}
		return pathsFileText;
	}



	/**
	 * This method initializes browsePathsFile	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBrowsePathsFile() {
		if (browsePathsFile == null) {
			browsePathsFile = new JButton();
			browsePathsFile.setText("Browse");			
			browsePathsFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try 
						{
							File selFile = fc.getSelectedFile();
							String filename = selFile.getAbsolutePath();
							pathsFileText.setText(filename);
							browsePathsFile.setEnabled(false);	
							browseRoomsFile.setEnabled(true);
							pathsFile = PathsFile.FromFile(filename);
							
							String pathsString = pathsFile.GetWKT();
							((MyPanel)MainPanel).SetForegroundShapes(pathsString);
							MainPanel.repaint();
														
							statusLabel.setText("Select Rooms File");
							
						}
						catch (Exception e1) 
						{						
						}
					}
				}
			});
		}
		return browsePathsFile;
	}



	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1.0;
			jLabel = new JLabel();
			jLabel.setText("Rooms File:");
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(jLabel, new GridBagConstraints());
			jPanel3.add(getRoomsFileText(), gridBagConstraints);
			jPanel3.add(getBrowseRoomsFile(), new GridBagConstraints());
		}
		return jPanel3;
	}



	/**
	 * This method initializes roomsFileText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRoomsFileText() {
		if (roomsFileText == null) {
			roomsFileText = new JTextField();
			roomsFileText.setEditable(false);
		}
		return roomsFileText;
	}



	/**
	 * This method initializes browseRoomsFile	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBrowseRoomsFile() {
		if (browseRoomsFile == null) {
			browseRoomsFile = new JButton();
			browseRoomsFile.setText("Browse");
			browseRoomsFile.setEnabled(false);
			browseRoomsFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try 
						{
							File selFile = fc.getSelectedFile();
							String filename = selFile.getAbsolutePath();
							roomsFileText.setText(filename);								
							browseRoomsFile.setEnabled(false);
							roomsFile = RoomsFile.FromFile(filename);
																					
							statusLabel.setText("Select Node for Room");
							
						}
						catch (Exception e1) 
						{						
						}
					}
				}
			});
		}
		return browseRoomsFile;
	}



	




	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Room Tags:");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.weightx = 1.0;
			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			jPanel6.add(jLabel3, new GridBagConstraints());
			jPanel6.add(getTagsField(), gridBagConstraints3);
			jPanel6.add(getJButton(), new GridBagConstraints());
		}
		return jPanel6;
	}



	/**
	 * This method initializes tagsField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTagsField() {
		if (tagsField == null) {
			tagsField = new JTextField();			
		}
		return tagsField;
	}



	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("OK");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					room.setTags(tagsField.getText());
					roomsFile.ToFile(roomsFileText.getText());
				}
			});
		}
		return jButton;
	}



	/**
	 * @param args
	 * @throws ParseException 
	 * @throws NoninvertibleTransformException 
	 */
	public static void main(String[] args) throws Exception 
	{			
		
		// TODO Auto-generated method stub
		RoomsWindow test = new RoomsWindow();
	    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    test.setVisible(true);
	    
	}

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public RoomsWindow() throws Exception {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception 
	{		
		
		this.setSize(339, 228);
		this.setContentPane(getJPanel());
		this.setTitle("Frame");
		
		
		String floor3RoomsFile = "/home/innominate/Documents/School/DatabaseTechnologies/proj/nav_graphs/floor3.wkt";
		byte[] buffer = new byte[(int) new File(floor3RoomsFile).length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(floor3RoomsFile));
	    f.read(buffer);
	    String floor3Rooms = new String(buffer);	    
	    ((MyPanel)MainPanel).SetBackgroundShapes(floor3Rooms);
	    
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
