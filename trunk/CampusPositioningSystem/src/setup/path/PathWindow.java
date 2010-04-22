package setup.path;

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
import util.AccessPoint;
import util.Config;
import util.Wireless;

import com.vividsolutions.jts.io.ParseException;

public class PathWindow extends JFrame {	
	private PathsFile pathsFile;  
	
	private Node nodeFrom = null;
	private int makeEdge = 0; //1=select node one, 2=select node 2, 0=not makeEdge mode
	
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JPanel MainPanel = null;
	private JLabel statusLabel = null;
	private JPanel jPanel1 = null;
	private JButton newBranchButton = null;
	private JPanel jPanel4 = null;
	private JLabel jLabel1 = null;
	private JTextField pathsFileText = null;
	private JButton browsePathsFile = null;

	private JPanel jPanel3 = null;

	private JButton addEdgeButton = null;
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
									
						boolean addNewNode = true;
						
						if (makeEdge == 2)
						{
							makeEdge = 0;
							Node nodeTo = pathsFile.NearestNode(p.getX(), p.getY());
							if (nodeFrom != nodeTo)
							{
								Edge newEdge = new Edge(nodeFrom, nodeTo);
								pathsFile.GetEdges().add(newEdge);
							}
							nodeFrom = nodeTo;
							addNewNode = false;
						}
						if (nodeFrom == null)
						{
							if (pathsFile.GetNodes().size() != 0)
							{
								if (makeEdge == 1)
								{
									makeEdge = 2;
									statusLabel.setText("Select Node 2 for Edge");
								}								
								addNewNode = false;
								nodeFrom = pathsFile.NearestNode(p.getX(), p.getY());								
							}
						}						
						if (addNewNode)
						{
							Node newNode = new Node(p.getX(), p.getY());
							pathsFile.GetNodes().add(newNode);
							
							if (nodeFrom != null)
							{
								Edge newEdge = new Edge(nodeFrom, newNode);
								pathsFile.GetEdges().add(newEdge);
							}
							
							nodeFrom = newNode;							
						}

						
						pathsFile.ToFile(pathsFileText.getText());
						
						String pathsString = pathsFile.GetWKT();
						((MyPanel)MainPanel).SetForegroundShapes(pathsString);
						MainPanel.repaint();
						
						if (makeEdge == 0)
						{
							statusLabel.setText("Select next node on path, or New Branch");
						}
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
		}
		return jPanel1;
	}



	/**
	 * This method initializes newBranchButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNewBranchButton() {
		if (newBranchButton == null) {
			newBranchButton = new JButton();
			newBranchButton.setText("New Branch");
			newBranchButton.setEnabled(false);
			newBranchButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					nodeFrom = null;
					statusLabel.setText("Select Node to Branch From");
				}
			});			
		}
		return newBranchButton;
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
							newBranchButton.setEnabled(true);
							addEdgeButton.setEnabled(true);
							pathsFile = PathsFile.FromFile(filename);
							
							String pathsString = pathsFile.GetWKT();
							((MyPanel)MainPanel).SetForegroundShapes(pathsString);
							MainPanel.repaint();
							
							if (pathsFile.GetNodes().size() == 0)
							{
								statusLabel.setText("Select First Point");
							}
							else
							{
								statusLabel.setText("Select New Branch");						
							}
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
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(getNewBranchButton(), new GridBagConstraints());
			jPanel3.add(getAddEdgeButton(), new GridBagConstraints());
		}
		return jPanel3;
	}



	/**
	 * This method initializes addEdgeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddEdgeButton() {
		if (addEdgeButton == null) {
			addEdgeButton = new JButton();
			addEdgeButton.setText("Add Edge");
			addEdgeButton.setEnabled(false);
			addEdgeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					makeEdge=1;
					nodeFrom = null;
					statusLabel.setText("Select Node 1 for Edge");
				}
			});
		}
		return addEdgeButton;
	}



	/**
	 * @param args
	 * @throws ParseException 
	 * @throws NoninvertibleTransformException 
	 */
	public static void main(String[] args) throws Exception 
	{			
		
		// TODO Auto-generated method stub
		PathWindow test = new PathWindow();
	    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    test.setVisible(true);
	    
	}

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public PathWindow() throws Exception {
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
