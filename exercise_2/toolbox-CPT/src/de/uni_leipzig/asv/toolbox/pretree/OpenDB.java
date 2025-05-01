package de.uni_leipzig.asv.toolbox.pretree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class OpenDB extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JDialog dialog;

	private boolean ok = false;

	private JButton openThisDBConnection;

	private JButton cancel;

	private JLabel driverClass_jl;

	private JLabel dbURL_jl;

	private JLabel userID_jl;

	private JLabel passwd_jl;

	private JTextField driverClass_jtf;

	private JTextField dbURL_jtf;

	private JTextField userID_jtf;

	private JTextField passwd_jtf;

	private PretreePanel ptp;

	private Properties prop;

	public OpenDB(PretreePanel ptp) {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));

		this.ptp = ptp;
		this.prop = new Properties();
		try {
			// load the data of the file to the property-object
			FileInputStream fis = new FileInputStream(ptp.queryfile);
			this.prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * this.setLayout( null ); this.setBackground(Color.lightGray);
		 * this.setBounds( new Rectangle(0,0, 320, 240) );
		 */

		this.driverClass_jl = new JLabel("Driver Class");
		// driverClass_jl.setBounds( new Rectangle(5,5,75,20) );
		this.dbURL_jl = new JLabel("DB-URL");
		// dbURL_jl.setBounds( new Rectangle(5,30,75,20) );
		this.userID_jl = new JLabel("User ID");
		// userID_jl.setBounds( new Rectangle(5,55,75,20) );
		this.passwd_jl = new JLabel("Password");
		// passwd_jl.setBounds( new Rectangle(5,80,75,20) );

		this.driverClass_jtf = new JTextField();
		// driverClass_jtf.setBounds( new Rectangle(65,5,300,20) );
		this.dbURL_jtf = new JTextField();
		// dbURL_jtf.setBounds( new Rectangle(65,30,300,20) );
		this.userID_jtf = new JTextField();
		// userID_jtf.setBounds( new Rectangle(65,55,300,20) );
		this.passwd_jtf = new JTextField();
		// passwd_jtf.setBounds( new Rectangle(65,80,300,20) );

		panel.add(this.driverClass_jl);
		panel.add(this.driverClass_jtf);
		panel.add(this.dbURL_jl);
		panel.add(this.dbURL_jtf);
		panel.add(this.userID_jl);
		panel.add(this.userID_jtf);
		panel.add(this.passwd_jl);
		panel.add(this.passwd_jtf);

		this.add(panel, BorderLayout.CENTER);

		this.openThisDBConnection = new JButton("OK");
		// openThisDBConnection.setBounds( new Rectangle(70,170,80,80) );
		this.openThisDBConnection
				.addActionListener(new openThisDBConnectionButtonListener());

		this.cancel = new JButton("Cancel");
		// cancel.setBounds(new Rectangle(250, 170, 80, 80));
		this.cancel.addActionListener(new CancelButtonListener());

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.openThisDBConnection);
		buttonPanel.add(this.cancel);

		this.add(buttonPanel, BorderLayout.SOUTH);

		this.driverClass_jtf.setText(this.prop
				.getProperty("pretree.driverClass"));
		this.dbURL_jtf.setText(this.prop.getProperty("pretree.dbURL"));
		this.userID_jtf.setText(this.prop.getProperty("pretree.userID"));
		this.passwd_jtf.setText(this.prop.getProperty("pretree.passwd"));
	}

	/**
	 * This method shows, if possible, the Dialog.
	 * 
	 * @param parent
	 *            The parent where this Dialog is connected to
	 * @param title
	 *            Sets the title of the Dialog
	 * @return true if no error occured, while trying to show the Dialog
	 * @return false if an error occured
	 */
	@SuppressWarnings("deprecation")
	public boolean showDialog(Component parent, String title) {
		this.ok = false;

		// serch for the frame, that oens our dailog
		Frame owner = null;
		if (parent instanceof Frame) {
			owner = (Frame) parent;
		} else {
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class,
					parent);
		}

		// create a new dialog at the first time, or if the owner has changed
		if ((this.dialog == null) || (this.dialog.getOwner() != owner)) {
			this.dialog = new JDialog(owner, true);
			this.dialog.getContentPane().add(this);
			this.dialog.getRootPane().setDefaultButton(
					this.openThisDBConnection);
			this.dialog.setTitle(title);
			this.dialog.setLocationRelativeTo(owner);
			this.dialog.pack();
			this.dialog.setVisible(true);
		} else {
			this.dialog.show();
		}

		return this.ok;
	}

	// --------------------------------------------
	// Private classes for the private class OpenDB
	// --------------------------------------------

	private class openThisDBConnectionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent oke) {
			OpenDB.this.ptp.dbcon.setDriverClass(OpenDB.this.driverClass_jtf
					.getText());
			OpenDB.this.ptp.dbcon.setDbURL(OpenDB.this.dbURL_jtf.getText());
			OpenDB.this.ptp.dbcon.setUserID(OpenDB.this.userID_jtf.getText());
			OpenDB.this.ptp.dbcon.setPassWd(OpenDB.this.passwd_jtf.getText());

			OpenDB.this.dialog.setVisible(false);
			OpenDB.this.dialog.dispose();
		}
	}

	/**
	 * This is the Listener class for the cancel button. You need it if you want
	 * finish the add tagger dialog without saving.
	 * 
	 * @author Torsten Thalheim & Daniel Zimmermann
	 * 
	 */
	private class CancelButtonListener implements ActionListener {
		/**
		 * This is necessary for the Listener
		 */
		public void actionPerformed(ActionEvent ce) {
			OpenDB.this.ptp.dbcon = null;

			OpenDB.this.dialog.setVisible(false);
			OpenDB.this.dialog.dispose();
		}
	}
}
