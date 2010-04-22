package setup.dbpopulate;

import gui.MyPanel;

import java.awt.Frame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.geom.NoninvertibleTransformException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;

import com.vividsolutions.jts.io.ParseException;

import setup.fingerprinting.FingerPrintFile;
import setup.path.PathWindow;
import setup.path.PathsFile;
import setup.rooms.RoomsFile;
import util.Config;

public class DbPopulateWindow extends JFrame {

	private FingerPrintFile fingerPrintFile;
	private PathsFile pathsFile;
	private RoomsFile roomsFile;
	private static Config config;
	
	
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JTextField fingerPrintFileText = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel1 = null;
	private JTextField pathsFileText = null;
	private JButton jButton = null;
	private JPanel jPanel3 = null;
	private JLabel jLabel2 = null;
	private JTextField roomsFileText = null;
	private JButton jButton1 = null;
	private JPanel jPanel4 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	/**
	 * This is the default constructor
	 */
	public DbPopulateWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Frame");

		this.add(getJPanel(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel4(), null);
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
			gridBagConstraints.weightx = 1.0;
			jLabel = new JLabel();
			jLabel.setText("Finger Print File:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(jLabel, new GridBagConstraints());
			jPanel1.add(getFingerPrintFileText(), gridBagConstraints);
			jPanel1.add(getJButton3(), new GridBagConstraints());
		}
		return jPanel1;
	}

	/**
	 * This method initializes fingerPrintFileText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFingerPrintFileText() {
		if (fingerPrintFileText == null) {
			fingerPrintFileText = new JTextField();
			fingerPrintFileText.setEditable(false);
		}
		return fingerPrintFileText;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weightx = 1.0;
			jLabel1 = new JLabel();
			jLabel1.setText("Paths File:");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(jLabel1, new GridBagConstraints());
			jPanel2.add(getPathsFileText(), gridBagConstraints1);
			jPanel2.add(getJButton(), new GridBagConstraints());
		}
		return jPanel2;
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
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Browse");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try 
						{
							File selFile = fc.getSelectedFile();
							String filename = selFile.getAbsolutePath();
							pathsFileText.setText(filename);
							
							pathsFile = PathsFile.FromFile(filename);							
						}
						catch (Exception e1) 
						{						
						}
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.weightx = 1.0;
			jLabel2 = new JLabel();
			jLabel2.setText("Rooms File:");
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(jLabel2, new GridBagConstraints());
			jPanel3.add(getRoomsFileText(), gridBagConstraints2);
			jPanel3.add(getJButton1(), new GridBagConstraints());
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
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Browse");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try 
						{
							File selFile = fc.getSelectedFile();
							String filename = selFile.getAbsolutePath();
							roomsFileText.setText(filename);
							
							roomsFile = RoomsFile.FromFile(filename);							
						}
						catch (Exception e1) 
						{						
						}
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.add(getJButton2(), gridBagConstraints3);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Populate Database");
			jButton2.addActionListener(new java.awt.event.ActionListener() {				
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						Connection conn = config.getConnection();					
						PopulateDatabase.Populate(conn, fingerPrintFile, pathsFile, roomsFile);
					} catch (SQLException e1) {
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Browse");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try 
						{
							File selFile = fc.getSelectedFile();
							String filename = selFile.getAbsolutePath();
							fingerPrintFileText.setText(filename);
							
							fingerPrintFile = FingerPrintFile.FromFile(filename);							
						}
						catch (Exception e1) 
						{						
						}
					}
				}
			});
		}
		return jButton3;
	}
	
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws NoninvertibleTransformException 
	 */
	public static void main(String[] args) throws Exception 
	{			
		String configFile = "config.xml";
		if (args.length > 0)
		{
			configFile = args[0];
		}		
		config = Config.FromFile(configFile);
		
		// TODO Auto-generated method stub
		DbPopulateWindow popWin = new DbPopulateWindow();		
		popWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popWin.setVisible(true);
	    
	}

}
