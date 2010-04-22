package setup.fingerprinting;

import gui.MyPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.AccessPoint;
import util.Wireless;

import com.vividsolutions.jts.io.ParseException;

public class FingerprintingWindow extends JFrame 
{	
	private FingerPrintFile fingerPrintFile;
	
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JPanel MainPanel = null;
	private JLabel statusLabel = null;
	private JPanel jPanel1 = null;
	private JTextField fingerPrintFileText = null;
	private JButton browseFingerPrintFile = null;
	private JLabel jLabel = null;
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
			statusLabel.setText("Select Finger Print File");
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
						if (fingerPrintFile == null)
						{
							JOptionPane.showMessageDialog(null, "Select finger print file first.", "Select finger print file first.", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						Point2D p = ((MyPanel)MainPanel).GetViewport().toModelPoint(e.getPoint());
						ArrayList<AccessPoint> aps = Wireless.GetVisibleAccesspoints();
												
						FingerPrint fp = new FingerPrint(p.getX(), p.getY(), aps);						
						fingerPrintFile.GetFingerPrints().add(fp);
						fingerPrintFile.ToFile(fingerPrintFileText.getText());
						
						String fingerPrintsString = fingerPrintFile.GetPointsAsString();
						((MyPanel)MainPanel).SetForegroundShapes(fingerPrintsString);
						MainPanel.repaint();
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
			jLabel = new JLabel();
			jLabel.setText("Finger Print File:");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1.0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(jLabel, new GridBagConstraints());
			jPanel1.add(getFingerPrintFileText(), gridBagConstraints);
			jPanel1.add(getBrowseFingerPrintFile(), new GridBagConstraints());
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
	 * This method initializes browseFingerPrintFile	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBrowseFingerPrintFile() {
		if (browseFingerPrintFile == null) {
			browseFingerPrintFile = new JButton();
			browseFingerPrintFile.setText("Browse");
			browseFingerPrintFile.setMnemonic(KeyEvent.VK_UNDEFINED);
			browseFingerPrintFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					JFileChooser fc = new JFileChooser();
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try 
						{
							File selFile = fc.getSelectedFile();
							String filename = selFile.getAbsolutePath();
							fingerPrintFileText.setText(filename);
							browseFingerPrintFile.setEnabled(false);
							fingerPrintFile = FingerPrintFile.FromFile(filename);
							String fingerPrintsString = fingerPrintFile.GetPointsAsString();
							((MyPanel)MainPanel).SetForegroundShapes(fingerPrintsString);
							MainPanel.repaint();
							statusLabel.setText("Go to a location, and click it on the map.");
						}
						catch (Exception e1) 
						{						
						}
					}
				}
			});
		}
		return browseFingerPrintFile;
	}



	/**
	 * @param args
	 * @throws ParseException 
	 * @throws NoninvertibleTransformException 
	 */
	public static void main(String[] args) throws Exception 
	{	
		// TODO Auto-generated method stub
		FingerprintingWindow test = new FingerprintingWindow();
	    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    test.setVisible(true);	    
	}

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public FingerprintingWindow() throws Exception {
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
