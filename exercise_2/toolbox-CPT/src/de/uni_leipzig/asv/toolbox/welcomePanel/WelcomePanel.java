package de.uni_leipzig.asv.toolbox.welcomePanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputAdapter;

import de.uni_leipzig.asv.toolbox.toolboxGui.Toolbox;
import de.uni_leipzig.asv.toolbox.toolboxGui.ToolboxModule;
import de.uni_leipzig.asv.utils.BrowserControl;

public class WelcomePanel extends ToolboxModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Zeugs für WortschtzModul
	@Override
	public String getToolTip() {
		return "This is the Global Welcome to the Toolbox";
	}

	@Override
	public void activated() {
		repaint();
	}

	@Override
	public JPanel getModulePanel() {
		return this;
	}

	@Override
	public char getMnemonic() {
		return (char) (99);
	}

	@Override
	public String getName() {
		return "Toolbox Welcome Panel";
	}

	@Override
	public Icon getIcon() {
		return createImageIcon("./img/Welcome.jpg");
	}

	JPanel welcomePanel = new JPanel();

	JLabel nameLabel = new JLabel();

	JTextArea descriptionLabel = new JTextArea();

	private JLabel firstLink;

	private JLabel softwareLabel;

	private JLabel docuLink;

	private JLabel newTools;

	public WelcomePanel(Toolbox wTool) {
		super(wTool);

		this.welcomePanel.setLayout(null);
		this.welcomePanel.setSize(new Dimension(800, 550));

		this.nameLabel.setText("<html>Welcome to the Toolbox"
				+ "<br> Version 1.0</html>");
		this.nameLabel.setFont(new Font("Verdana", Font.BOLD
				| Font.CENTER_BASELINE, 30));
		this.nameLabel.setBounds(new Rectangle(100, 70, 500, 80));

		this.descriptionLabel
				.setText("This toolbox hosts a variety of natural language processing programs which \n"
						+ "are developed mostly by students at the");
		this.firstLink = new JLabel(
				"<html> <a href=\"http://www.asv.informatik.uni-leipzig.de\">NLP Department, University of Leipzig, Germany</a></html>");

		this.softwareLabel = new JLabel(
				"<html>This software is distributed freely and open-source and comes without warranty.\n"
						+ "<br><br>"
						+ "Conception: Chris Biemann, Stefan Bordag, Uwe Quasthoff <br>"
						+ "Toolbox Programmers: Lydia Steiner, Konstantin Sveds, Daniel Zimmermann <br>"
						+ "Tool Programmers: see Tools</html>");
		this.docuLink = new JLabel(
				"<html><a href=\"/docu/toolbox.html\">Documentation of Toolbox</a></html>");
		this.newTools = new JLabel(
				"<html><a href=\"http://asv.informatik.uni-leipzig.de\">Check for new Tools</a></html>");

		this.descriptionLabel.setEditable(false);
		this.descriptionLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.descriptionLabel.setBackground(this.welcomePanel.getBackground());
		this.descriptionLabel.setBounds(new Rectangle(100, 160, 550, 40));
		this.firstLink.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.firstLink.setBackground(this.welcomePanel.getBackground());
		this.firstLink.setBounds(new Rectangle(100, 205, 550, 20));
		this.firstLink.addMouseListener(new MouseInputAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				BrowserControl
						.launch_browser("http://www.asv.informatik.uni-leipzig.de");

			}

		});
		this.softwareLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.softwareLabel.setBackground(this.welcomePanel.getBackground());
		this.softwareLabel.setBounds(new Rectangle(100, 230, 550, 130));
		this.docuLink.addMouseListener(new MouseInputAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				BrowserControl.launch_browser("./doc/toolbox.html");

			}

		});
		this.docuLink.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.docuLink.setBackground(this.welcomePanel.getBackground());
		this.docuLink.setBounds(new Rectangle(100, 365, 550, 20));
		this.newTools.setFont(new Font("Verdana", Font.PLAIN, 12));
		this.newTools.setBackground(this.welcomePanel.getBackground());
		this.newTools.setBounds(new Rectangle(100, 390, 550, 20));
		this.newTools.addMouseListener(new MouseInputAdapter() {

			public void mouseClicked(MouseEvent arg0) {
				BrowserControl
						.launch_browser("http://www.asv.informatik.uni-leipzig.de");

			}

		});

		this.welcomePanel.add(this.nameLabel, null);
		this.welcomePanel.add(this.descriptionLabel, null);
		this.welcomePanel.add(this.firstLink, null);
		this.welcomePanel.add(this.softwareLabel, null);
		this.welcomePanel.add(this.docuLink, null);
		this.welcomePanel.add(this.newTools, null);

		this.add(this.welcomePanel, null);
	}
}
