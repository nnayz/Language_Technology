package de.uni_leipzig.asv.toolbox.pretree;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;

import de.uni_leipzig.asv.toolbox.toolboxGui.Toolbox;
import de.uni_leipzig.asv.toolbox.toolboxGui.ToolboxModule;
import de.uni_leipzig.asv.util.VectorNameTable;
import de.uni_leipzig.asv.util.commonDB.CommonDB;
import de.uni_leipzig.asv.util.commonDB.DBConnection;
import de.uni_leipzig.asv.util.commonFileChooser.CommonFileChooser;
import de.uni_leipzig.asv.util.commonTable.CommonTable;
import de.uni_leipzig.asv.utils.BrowserControl;
import de.uni_leipzig.asv.utils.Pretree;

public class PretreePanel extends ToolboxModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean d = false; // Anzeigen ein/aus

	// Data

	Pretree pretree = null;

	// NameTable trainSetNameTable = null;
	VectorNameTable trainSetVectorNameTable = null;

	// NameTable testSetNameTable = null;
	VectorNameTable testSetVectorNameTable = null;

	// NameTable testClassifiedNameTable = null;
	VectorNameTable testClassifiedVectorNameTable = null;

	// private File _aktDir;

	CommonFileChooser cfc;

	private static String _tab = "\t";

	// Zeugs für WortschtzModul
	@Override
	public String getToolTip() {
		return "Train and Test Prefix Compression Tries for String Classification";
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
		return "Pretree Tool";
	}

	@Override
	public Icon getIcon() {
		return createImageIcon("./img/Pre.jpg");
	}

	// Oberfläche
	JLabel trainStatusLabel = new JLabel();

	JLabel testStatusLabel = new JLabel();

	JPanel pretreeFramePanel = new JPanel();

	JTabbedPane pretreeTabbedPane = new JTabbedPane();

	JPanel welcomePanel = new JPanel();

	JPanel trainPanel = new JPanel();

	JPanel testPanel = new JPanel();

	JLabel docuLabel = new JLabel();

	JLabel nameLabel = new JLabel();

	JLabel refsLabel = new JLabel();

	JScrollPane trainSetScrollPane = new JScrollPane();

	JTable trainSetTable = new JTable();

	CommonTable trainSetCT = new CommonTable();

	JButton trainClearTrainButton = new JButton();

	JButton trainAddFileButton = new JButton();

	JButton trainSaveFileButton = new JButton();

	// JTextField trainAddFileTextField = new JTextField();
	// JTextField trainSaveFileTextField = new JTextField();
	JLabel trainSetLabel = new JLabel();

	JButton trainingAddLineButton = new JButton();

	JTextField trainingAddWordField = new JTextField();

	JTextField trainingAddClassField = new JTextField();

	JButton trainingSetDeleteButton = new JButton();

	JLabel trainTreeLabel = new JLabel();

	JButton trainButton = new JButton();

	JButton trainFromFileButton = new JButton();

	JButton trainFromDBButton = new JButton(); // <--------------

	public DBConnection dbcon = new DBConnection();

	public String queryfile = new String();

	// private OpenDB openDB;
	public Properties prop = new Properties();

	private JPanel dbpanel = new JPanel();

	private JTextField mInputDriver, mInputProtokol, mInputUrl, mInputPort,
			mInputUser, mInputDb;

	private JPasswordField mInputPass;

	private JComboBox mInputTable, mInputWordColumn, mInputClassColumn,
			mInputIdColumn;

	private JSpinner mInputWord_ID, mInputWordOffset;

	private HashMap<String, Vector<String>> databaseinfo = new HashMap<String, Vector<String>>();

	// JTextField trainFromFilePane = new JTextField();
	JProgressBar trainProgressBar = new JProgressBar(0, 100);

	JButton trainPruneButton = new JButton();

	JButton trainSaveTreeButton = new JButton();

	// JTextField trainSaveTreePane = new JTextField();
	JLabel trainParamaterLabel = new JLabel();

	// JLabel trainPruneLabel = new JLabel();

	// JTextField trainPruneTextField = new JTextField();

	JCheckBox trainReverseCheckBox = new JCheckBox();

	JCheckBox trainICCheckbox = new JCheckBox();

	JLabel testTreeStatusLabel = new JLabel();

	JCheckBox testReverseCheckbox = new JCheckBox();

	JCheckBox testICCheckbox = new JCheckBox();

	JButton testTreeLoadButton = new JButton();

	JProgressBar testProgressBar = new JProgressBar(0, 100);

	JLabel trainPruneLabel = new JLabel();

	JTextField trainPruneTextField = new JTextField(10);

	// JTextField testLoadTextField = new JTextField();
	// JLabel testCharacteristicsLabel = new JLabel();
	JLabel testTreeLoadedLabel = new JLabel();

	JLabel testIsIgnoreCaseLabel = new JLabel();

	JLabel testIsReverseLabel = new JLabel();

	JLabel testNrClassesLabel = new JLabel();

	JLabel testNrNodesLabel = new JLabel();

	JScrollPane testTextScrollPane = new JScrollPane();

	JTextArea testTextArea = new JTextArea();

	JScrollPane testWordScrollPane = new JScrollPane();

	JTable testClassifiedJTable = new JTable();

	CommonTable testClassifiedCT = new CommonTable();

	JButton testFileButton = new JButton();

	// JTextField testFileTextField = new JTextField();
	JButton testWordButton = new JButton();

	JTextField testWordField = new JTextField();

	JButton classifyFile = new JButton("classify file");

	JCheckBox classifyToFile = new JCheckBox("file output");

	CommonTable ct = new CommonTable();

	JButton delSelected = new JButton();

	private String outputfile;

	public PretreePanel(Toolbox wTool) {
		super(wTool);
		// _aktDir = null;

		// init surface
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.pretreeFramePanel.setLayout(null);
		// this.setResizable(false);
		// this.setTitle("Pretree");
		// this.getContentPane().setLayout(null);
		this.pretreeFramePanel.setBounds(ToolboxModule.moduleBounds);
		this.pretreeFramePanel.setLayout(null);
		this.pretreeTabbedPane.setBounds(ToolboxModule.moduleBounds);
		this.pretreeTabbedPane.setMinimumSize(ToolboxModule.moduleSize);
		this.pretreeTabbedPane.setPreferredSize(ToolboxModule.moduleSize);
		// this.getContentPane().setLayout(null);
		this.setSize(ToolboxModule.moduleSize);
		this.welcomePanel.setLayout(new GridLayout(3, 1));
		this.trainPanel.setLayout(null);
		this.testPanel.setLayout(null);
		this.dbpanel.setLayout(new SpringLayout());
		this.trainStatusLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.trainStatusLabel.setText("initializing...");
		this.trainStatusLabel.setBounds(10, 465, 670, 20);
		this.testStatusLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.testStatusLabel.setText("initializing...");
		this.testStatusLabel.setBounds(10, 465, 670, 20);

		// pre-init queryfile
		this.queryfile = "./config/pretree/pretree_queryfile.query";

		// prepare dbcon
		this.dbcon = new DBConnection();

		// get queryfile as propertyfile
		this.prop = new Properties();
		try {
			// load the data of the file to the property-object
			FileInputStream fis = new FileInputStream(this.queryfile);
			this.prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// init dbcon from queryfile
		this.dbcon.setDriverClass(this.prop.getProperty("pretree.driverClass"));
		this.dbcon.setDbURL(this.prop.getProperty("pretree.dbURL") + ":"
				+ this.prop.getProperty("prtree.dbPort") + "/"
				+ this.prop.getProperty("pretree.dbDatabase"));
		this.dbcon.setUserID(this.prop.getProperty("pretree.userID"));
		this.dbcon.setPassWd(this.prop.getProperty("pretree.passwd"));

		// CommonFileChooser
		this.cfc = new CommonFileChooser(null, null);

		// welcome panel

		this.nameLabel
				.setText("<html><h1 align=\"center\">Pretree Tool</h1>"
						+ "<p align=\"center\">Welcome to the Pretree Building and Classifying Tool</p>"
						+ "<p align=\"center\">The Train panel allows You to train and prune pretrees, both from given training sets</p>"
						+ "<p align=\"center\">in files and with new sets you can manage and save.</p>"
						+ "<p align=\"center\">The Classify panel provides classifying single words and to compare the classes of</p>"
						+ "<p align=\"center\">classified word sets with the classifying of the active tree. This you get</p>"
						+ "<p align=\"center\">precision and recall values for.</p>" +
								"<p align=\"center\">Version 1.0</p>"
						+ "</html>");
		this.nameLabel.setHorizontalAlignment(JLabel.CENTER);
		this.nameLabel.setVerticalAlignment(JLabel.BOTTOM);

		// nameLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		// nameLabel.setBackground(new Color(238, 238, 238));
		// nameLabel.setBounds(new Rectangle(100, 70, 500, 40));

		this.docuLabel
				.setText("<html><p align=\"center\"><a href=\"./doc/Pretree.html\">Documentation of Pretree Tool</a></p></html>");
		this.docuLabel.setHorizontalAlignment(JLabel.CENTER);
		this.docuLabel.setVerticalAlignment(JLabel.CENTER);

		this.docuLabel.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				BrowserControl.launch_browser("./doc/Pretree.html");
			}
		});
		// descriptionLabel.setEditable(false);
		// descriptionLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		// descriptionLabel.setBackground(new Color(213, 213, 213));
		// descriptionLabel.setBackground(welcomePanel.getBackground());
		// descriptionLabel.setBounds(new Rectangle(100, 120, 550, 300));

		this.refsLabel
				.setText("<html><p align=\"center\">ASV Toolbox 2005</p>"
						+ "<p align=\"center\">Authors:</p>"
						+ "<p align=\"center\">Christian Bieman, Florian Holz</p></html>");
		this.refsLabel.setHorizontalAlignment(JLabel.CENTER);
		this.refsLabel.setVerticalAlignment(JLabel.TOP);

		// refsLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		// refsLabel.setBounds(new Rectangle(100, 420, 550, 30));

		// train panel

		this.trainSetLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.trainSetLabel.setText("Training Set:");
		this.trainSetLabel.setBounds(new Rectangle(35, 25, 110, 20));
		// trainClearTrainButton.setActionCommand("Clear");
		this.trainClearTrainButton.setMargin(new Insets(0, 0, 0, 0));
		this.trainClearTrainButton.setText("Clear All");
		this.trainClearTrainButton.setBounds(new Rectangle(320, 25, 60, 20));
		this.trainSetScrollPane.setBounds(new Rectangle(30, 60, 350, 310));
		this.trainSetCT.setBounds(new Rectangle(30, 60, 350, 310));
		this.trainingAddLineButton.setText("Add");
		this.trainingAddLineButton.setBounds(new Rectangle(30, 380, 70, 20));
		this.trainingAddWordField.setBounds(new Rectangle(105, 380, 150, 20));
		this.trainingAddClassField.setBounds(new Rectangle(260, 380, 120, 20));
		this.trainingSetDeleteButton.setText("Delete Selected");
		this.trainingSetDeleteButton.setBounds(new Rectangle(30, 405, 135, 20));

		this.trainAddFileButton.setMargin(new Insets(0, 0, 0, 0));
		this.trainAddFileButton.setText("Add from File");
		this.trainAddFileButton.setBounds(new Rectangle(30, 430, 170, 20));
		this.trainSaveFileButton.setText("Save table to File");
		this.trainSaveFileButton.setBounds(new Rectangle(210, 430, 170, 20));
		/*
		 * trainAddFileTextField.setText("enter filename here");
		 * trainAddFileTextField.setBounds(new Rectangle(235, 50, 120, 20));
		 * trainSaveFileTextField.setText("enter filename here");
		 * trainSaveFileTextField.setBounds(new Rectangle(235, 75, 120, 20));
		 */

		this.trainParamaterLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.trainParamaterLabel.setText("Training Parameters: ");
		this.trainParamaterLabel.setBounds(new Rectangle(425, 60, 140, 30));
		this.trainReverseCheckBox.setText("Reverse");
		this.trainReverseCheckBox.setBounds(new Rectangle(575, 65, 80, 20));
		this.trainICCheckbox.setText("Ignore Case");
		this.trainICCheckbox.setBounds(new Rectangle(660, 65, 100, 20));

		this.trainTreeLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.trainTreeLabel.setText("Tree Functions: ");
		this.trainTreeLabel.setBounds(new Rectangle(425, 110, 130, 25));
		this.trainButton.setText("Train");
		this.trainButton.setBounds(new Rectangle(425, 140, 80, 60));
		this.trainFromFileButton.setText("Train from File");
		this.trainFromFileButton.setBounds(new Rectangle(505, 140, 150, 60));
		this.trainFromDBButton.setText("Train from DB"); // <-------------
		this.trainFromDBButton.setBounds(new Rectangle(655, 140, 110, 60));
		// trainFromFilePane.setText("enter filename here");
		// trainFromFilePane.setBounds(new Rectangle(600, 430, 180, 20));
		this.trainSaveTreeButton.setMargin(new Insets(0, 0, 0, 0));
		this.trainSaveTreeButton.setText("Save Tree to File");
		this.trainSaveTreeButton.setBounds(new Rectangle(655, 315, 110, 60));
		this.trainProgressBar.setBounds(new Rectangle(425, 210, 340, 25));
		this.trainPruneButton.setText("Prune");
		this.trainPruneButton.setBounds(new Rectangle(655, 245, 110, 60));
		this.trainPruneLabel.setText("Classify Threshold: ");
		this.trainPruneLabel.setBounds(new Rectangle(350, 80, 130, 20));

		this.trainPruneTextField.setText("0.0");
		this.trainPruneTextField.setBounds(new Rectangle(480, 80, 70, 20));
		// trainSaveTreePane.setText("enter filename here");
		// trainSaveTreePane.setBounds(new Rectangle(530, 360, 180, 20));

		this.trainAddFileButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							trainAddFileButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}
					}
				});
		this.trainSaveFileButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							trainSaveFileButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}
					}
				});

		this.trainingSetDeleteButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							trainingSetDeleteButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}
					}
				});
		this.trainClearTrainButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						trainClearTrainButton_actionPerformed(e);
					}
				});

		this.trainingAddLineButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							trainingAddLineButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}

					}
				});
		this.trainingAddWordField
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							trainingAddLineButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}

					}
				});
		this.trainingAddClassField
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							trainingAddLineButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}

					}
				});
		this.trainButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trainButton_actionPerformed(e);
			}
		});

		this.trainFromFileButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						trainFromFileButton_actionPerformed(e);
					}
				});

		this.trainFromDBButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						trainFromDBButton_actionPerformed(e);
					}
				});

		this.trainPruneButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						trainPruneButton_actionPerformed(e);
					}
				});
		this.trainSaveTreeButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							trainSaveTreeButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}

					}
				});

		String[] ct_header = { "item", "class" };
		this.trainSetCT.createTable(ct_header, null);

		// test panel

		this.testTreeStatusLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.testTreeStatusLabel.setText("Tree Status");
		this.testTreeStatusLabel.setBounds(new Rectangle(35, 25, 240, 20));
		this.testTreeLoadedLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.testTreeLoadedLabel.setText("No Tree loaded");
		this.testTreeLoadedLabel.setBounds(new Rectangle(35, 55, 750, 20));
		this.testIsIgnoreCaseLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.testIsIgnoreCaseLabel.setText("Ignore Case: ");
		this.testIsIgnoreCaseLabel.setBounds(new Rectangle(35, 75, 350, 20));
		this.testIsReverseLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		this.testIsReverseLabel.setText("Reverse: ");
		this.testIsReverseLabel.setBounds(new Rectangle(35, 95, 350, 20));
		/*
		 * testReverseCheckbox.setText("Reverse");
		 * testReverseCheckbox.setBounds(new Rectangle(15, 70, 90, 20));
		 * testICCheckbox.setText("Ignore Case"); testICCheckbox.setBounds(new
		 * Rectangle(15, 100, 90, 20));
		 */
		this.testTreeLoadButton.setMargin(new Insets(0, 0, 0, 0));
		this.testTreeLoadButton.setText("Load Tree from File");
		this.testTreeLoadButton.setBounds(new Rectangle(185, 25, 130, 20));
		// testLoadTextField.setText("enter filename here");
		// testLoadTextField.setBounds(new Rectangle(170, 45, 148, 20));
		// testCharacteristicsLabel.setFont(new java.awt.Font("Dialog", 1, 12));
		// testCharacteristicsLabel.setText("Tree Characteristics");
		// testCharacteristicsLabel.setBounds(new Rectangle(350, 20, 320, 20));
		this.testNrClassesLabel.setText("Classes: ");
		this.testNrClassesLabel.setBounds(new Rectangle(200, 75, 200, 20));
		this.testNrNodesLabel.setText("Nodes: ");
		this.testNrNodesLabel.setBounds(new Rectangle(200, 95, 200, 20));

		this.testWordButton.setMargin(new Insets(0, 0, 0, 0));
		this.testWordButton.setText("Classify");
		this.testWordButton.setBounds(new Rectangle(35, 120, 75, 20));
		this.testWordField.setText("enter word here");
		this.testWordField.setBounds(new Rectangle(115, 120, 225, 20));
		// testWordScrollPane.setBounds(new Rectangle(35, 145, 305, 300));
		this.testClassifiedCT.setBounds(new Rectangle(35, 145, 305, 300));
		this.testFileButton.setText("Evaluate Tree with File");
		this.testFileButton.setBounds(new Rectangle(350, 120, 175, 20));
		this.testTextScrollPane.setBounds(new Rectangle(350, 145, 400, 300));
		this.testProgressBar.setBounds(new Rectangle(550, 120, 150, 20));
		// testFileTextField.setText("enter testfile name here");
		// testFileTextField.setBounds(new Rectangle(170, 400, 189, 20));
		this.classifyFile.setBounds(new Rectangle(35, 450, 150, 20));
		this.classifyToFile.setBounds(new Rectangle(190, 450, 75, 20));

		this.classifyFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CommonFileChooser cfc = new CommonFileChooser(null, null);
				String inputFile = cfc.showDialogAndReturnFilename(
						PretreePanel.this, "choose input file");

				if (outputfile != null) {
					classifyFileToFile(inputFile);
				} else {
					classifyFile(inputFile);
				}

			}

		});

		this.classifyToFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (classifyToFile.isSelected()) {
					CommonFileChooser cfc = new CommonFileChooser(null, null);
					outputfile = cfc.showDialogAndReturnFilename(
							PretreePanel.this, "choose output file");
					if (outputfile == null || outputfile.equals("")) {
						classifyToFile.setSelected(false);
						outputfile = null;
						return;
					}

				} else {
					outputfile = null;
				}

			}

		});

		this.testTreeLoadButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							testTreeLoadButton_actionPerformed(e);
						} catch (IOException f) {
							PretreePanel.this.testStatusLabel
									.setText("Exception while loading File");
							System.out.println(f.getMessage());
						} catch (ClassNotFoundException f) {
							PretreePanel.this.testStatusLabel
									.setText("Exception while loading File");
							System.out.println(f.getMessage());
						}
					}
				});

		this.testWordButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {

						testWordButton_actionPerformed(e);

					}
				});
		this.testWordField
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {

						testWordButton_actionPerformed(e);

					}
				});
		this.testFileButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							testFileButton_actionPerformed(e);
						} catch (IOException f) {
							System.out.println(f.getMessage());
						}

					}
				});
		this.testClassifiedCT.createTable(ct_header, null);

		// DB Panel
		SpringLayout Layout = (SpringLayout) this.dbpanel.getLayout();
		Dimension DimInput = new Dimension(150, 20);
		Dimension DimLabel = new Dimension(80, 20);
		final int SpaceBetween = 5;
		int DistanceFromTop = 25;
		JComponent RefComponent = this.dbpanel;

		// Create field and label for DRIVER input
		//

		JLabel LabelDriver = new JLabel("DriverClass");

		LabelDriver.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelDriver.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelDriver, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelDriver, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputDriver = new JTextField();
		this.mInputDriver.setPreferredSize(DimInput);
		this.mInputDriver.setText(this.prop.getProperty("pretree.driverClass"));

		Layout.putConstraint(SpringLayout.WEST, this.mInputDriver,
				SpaceBetween, SpringLayout.EAST, LabelDriver);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputDriver,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelDriver);
		this.dbpanel.add(this.mInputDriver);

		// Create field and label for PROTOKOL input
		//

		DistanceFromTop += 25;

		JLabel LabelProtokol = new JLabel("Protokol");

		LabelProtokol.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelProtokol.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelProtokol, SpaceBetween,
				SpringLayout.WEST, this.dbpanel);
		Layout.putConstraint(SpringLayout.NORTH, LabelProtokol,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.mInputProtokol = new JTextField();
		this.mInputProtokol.setPreferredSize(DimInput);
		this.mInputProtokol.setText(this.prop.getProperty("pretree.protokol"));
		Layout.putConstraint(SpringLayout.WEST, this.mInputProtokol,
				SpaceBetween, SpringLayout.EAST, LabelProtokol);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputProtokol,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelProtokol);
		this.dbpanel.add(this.mInputProtokol);

		// Create field and label for URL input
		//

		DistanceFromTop += 25;

		JLabel LabelUrl = new JLabel("Url");

		LabelUrl.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelUrl.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelUrl, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelUrl, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputUrl = new JTextField();
		this.mInputUrl.setPreferredSize(DimInput);
		this.mInputUrl.setText(this.prop.getProperty("pretree.dbURL"));
		Layout.putConstraint(SpringLayout.WEST, this.mInputUrl, SpaceBetween,
				SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputUrl,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelUrl);
		this.dbpanel.add(this.mInputUrl);

		// Create field and label for PORT input
		//

		DistanceFromTop += 25;

		JLabel LabelPort = new JLabel("Port");

		LabelPort.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelPort.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelPort, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelPort, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputPort = new JTextField();
		this.mInputPort.setPreferredSize(DimInput);
		this.mInputPort.setText(this.prop.getProperty("pretree.dbPort"));
		Layout.putConstraint(SpringLayout.WEST, this.mInputPort, SpaceBetween,
				SpringLayout.EAST, LabelPort);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputPort,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelPort);
		this.dbpanel.add(this.mInputPort);

		// Create field and label for USER input
		//

		DistanceFromTop += 25;

		JLabel LabelUser = new JLabel("User");

		LabelUser.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelUser.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelUser, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelUser, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputUser = new JTextField();
		this.mInputUser.setPreferredSize(DimInput);
		this.mInputUser.setText(this.prop.getProperty("pretree.userID"));
		Layout.putConstraint(SpringLayout.WEST, this.mInputUser, SpaceBetween,
				SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputUser,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelUser);
		this.dbpanel.add(this.mInputUser);

		// Create field and label for PASSWORD input
		//

		DistanceFromTop += 25;

		JLabel LabelPass = new JLabel("Password");

		LabelPass.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelPass.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelPass, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelPass, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputPass = new JPasswordField();
		this.mInputPass.setPreferredSize(DimInput);
		this.mInputPass.setText(this.prop.getProperty("pretree.passwd"));
		Layout.putConstraint(SpringLayout.WEST, this.mInputPass, SpaceBetween,
				SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputPass,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelPass);
		this.dbpanel.add(this.mInputPass);

		// Create field and label for DATABASE input
		//

		DistanceFromTop += 25;

		JLabel LabelDb = new JLabel("Database");

		LabelDb.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelDb.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelDb, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelDb, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputDb = new JTextField();
		this.mInputDb.setPreferredSize(DimInput);
		this.mInputDb.setText(this.prop.getProperty("pretree.dbDatabase"));
		Layout.putConstraint(SpringLayout.WEST, this.mInputDb, SpaceBetween,
				SpringLayout.EAST, LabelDb);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputDb,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelDb);
		this.dbpanel.add(this.mInputDb);

		// connect button to database
		JButton connect = new JButton("connect to Database");
		connect.setToolTipText("Connect to Database to choose tables");
		connect.setPreferredSize(DimInput);
		connect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				connect();

			}

		});
		Layout.putConstraint(SpringLayout.NORTH, connect, 0,
				SpringLayout.NORTH, this.mInputDb);
		Layout.putConstraint(SpringLayout.WEST, connect, 20, SpringLayout.EAST,
				this.mInputDb);

		this.dbpanel.add(connect);

		// Create field and label for TABLE input
		//

		DistanceFromTop += 25;

		JLabel LabelTable = new JLabel("Table");

		LabelTable.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelTable.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelTable, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelTable, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputTable = new JComboBox();
		this.mInputTable.setPreferredSize(DimInput);
		this.mInputTable.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				PretreePanel.this.mInputWordColumn.removeAllItems();
				PretreePanel.this.mInputClassColumn.removeAllItems();
				PretreePanel.this.mInputIdColumn.removeAllItems();
				Vector<String> cols = PretreePanel.this.databaseinfo
						.get(PretreePanel.this.mInputTable.getSelectedItem());
				if (cols == null)
					return;
				for (int i = 0; i < cols.size(); i++) {
					PretreePanel.this.mInputWordColumn.addItem(cols.get(i));
					PretreePanel.this.mInputClassColumn.addItem(cols.get(i));
					PretreePanel.this.mInputIdColumn.addItem(cols.get(i));
				}
			}
		});
		Layout.putConstraint(SpringLayout.WEST, this.mInputTable, SpaceBetween,
				SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputTable,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelTable);
		this.dbpanel.add(this.mInputTable);

		// Create field and label for COLUMN input
		//

		DistanceFromTop += 25;

		JLabel LabelColumn = new JLabel("Word Column");

		LabelColumn.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelColumn.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelColumn, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelColumn, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		this.mInputWordColumn = new JComboBox();
		this.mInputWordColumn.setPreferredSize(DimInput);

		Layout.putConstraint(SpringLayout.WEST, this.mInputWordColumn,
				SpaceBetween, SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputWordColumn,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelColumn);
		this.dbpanel.add(this.mInputWordColumn);

		// Create field and label for COLUMN input
		//

		DistanceFromTop += 25;

		JLabel LabelClassColumn = new JLabel("Class Column");

		LabelClassColumn.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelClassColumn.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelClassColumn, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelClassColumn,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.mInputClassColumn = new JComboBox();
		this.mInputClassColumn.setPreferredSize(DimInput);

		Layout.putConstraint(SpringLayout.WEST, this.mInputClassColumn,
				SpaceBetween, SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputClassColumn,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelClassColumn);
		this.dbpanel.add(this.mInputClassColumn);

		// Create field and label for IDCOLUMN input
		//
		DistanceFromTop += 25;

		JLabel LabelIDColumn = new JLabel("Column word ID");

		LabelIDColumn.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelIDColumn.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelIDColumn, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelIDColumn,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.mInputIdColumn = new JComboBox();
		this.mInputIdColumn.setPreferredSize(DimInput);

		Layout.putConstraint(SpringLayout.WEST, this.mInputIdColumn,
				SpaceBetween, SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputIdColumn,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelIDColumn);
		this.dbpanel.add(this.mInputIdColumn);

		connect();
		// tables and columns
		this.mInputTable.setSelectedItem(this.prop.getProperty(
				"pretree.tableName_getTable", ""));
		this.mInputWordColumn.setSelectedItem(this.prop.getProperty(
				"pretree.wordcolumn", ""));
		this.mInputClassColumn.setSelectedItem(this.prop.getProperty(
				"pretree.classcolumn", ""));
		this.mInputIdColumn.setSelectedItem(this.prop.getProperty(
				"pretree.idcolumn", ""));
		// Create field and label for MINIMUM WORD ID input
		//

		DistanceFromTop += 25;

		JLabel LabelWordID = new JLabel("Minimum Word id");

		LabelWordID.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelWordID.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelWordID, SpaceBetween,
				SpringLayout.WEST, RefComponent);
		Layout.putConstraint(SpringLayout.NORTH, LabelWordID, DistanceFromTop,
				SpringLayout.NORTH, RefComponent);

		SpinnerNumberModel wordIDModel = new SpinnerNumberModel();
		// wordIDModel.setValue(new
		// Integer(prop.getProperty("pretree.startatid")));
		wordIDModel.setStepSize(new Integer(1));
		wordIDModel.setMinimum(new Integer(0));
		this.mInputWord_ID = new JSpinner(wordIDModel);
		this.mInputWord_ID.setPreferredSize(DimInput);

		Layout.putConstraint(SpringLayout.WEST, this.mInputWord_ID,
				SpaceBetween, SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputWord_ID,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelWordID);
		this.dbpanel.add(this.mInputWord_ID);

		// Create field and label for NUMBER OF WORD input
		//

		DistanceFromTop += 25;

		JLabel LabelWordOffset = new JLabel("Numbers of Words");

		LabelWordOffset.setHorizontalAlignment(SwingConstants.RIGHT);
		LabelWordOffset.setPreferredSize(DimLabel);

		Layout.putConstraint(SpringLayout.WEST, LabelWordOffset, SpaceBetween,
				SpringLayout.WEST, this.dbpanel);
		Layout.putConstraint(SpringLayout.NORTH, LabelWordOffset,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		SpinnerNumberModel wordOffsetModel = new SpinnerNumberModel();
		wordOffsetModel.setValue(new Integer(this.prop
				.getProperty("pretree.numberofwords")));
		wordOffsetModel.setStepSize(new Integer(1));
		wordOffsetModel.setMinimum(new Integer(0));
		this.mInputWordOffset = new JSpinner(wordOffsetModel);
		this.mInputWordOffset.setPreferredSize(DimInput);

		Layout.putConstraint(SpringLayout.WEST, this.mInputWordOffset,
				SpaceBetween, SpringLayout.EAST, LabelUrl);
		Layout.putConstraint(SpringLayout.NORTH, this.mInputWordOffset,
				DistanceFromTop, SpringLayout.NORTH, RefComponent);

		this.dbpanel.add(LabelWordOffset);
		this.dbpanel.add(this.mInputWordOffset);
		/**/
		// dbPanel end
		// adds
		// this.getContentPane().add(pretreeFramePanel, null);
		this.add(this.pretreeFramePanel, null);
		this.pretreeFramePanel.add(this.pretreeTabbedPane, null);
		this.pretreeTabbedPane.add(this.welcomePanel, "Welcome");
		this.welcomePanel.add(this.nameLabel, null);
		this.welcomePanel.add(this.docuLabel, null);
		this.welcomePanel.add(this.refsLabel, null);
		this.pretreeTabbedPane.add(this.trainPanel, "Train");
		this.trainPanel.add(this.trainStatusLabel, null);
		// trainPanel.add(trainSetScrollPane, null);
		this.trainPanel.add(this.trainSetCT, null);
		this.trainPanel.add(this.trainClearTrainButton, null);
		this.trainPanel.add(this.trainSaveFileButton, null);
		this.trainPanel.add(this.trainAddFileButton, null);
		this.trainPanel.add(this.trainSetLabel, null);
		this.trainPanel.add(this.trainingAddLineButton, null);
		this.trainPanel.add(this.trainingAddWordField, null);
		this.trainPanel.add(this.trainingAddClassField, null);
		this.trainPanel.add(this.trainingSetDeleteButton, null);
		// trainPanel.add(trainAddFileTextField, null);
		// trainPanel.add(trainSaveFileTextField, null);
		this.trainPanel.add(this.trainSaveTreeButton, null);
		// trainPanel.add(trainSaveTreePane, null);
		this.trainPanel.add(this.trainPruneButton, null);
		this.trainPanel.add(this.trainButton, null);
		this.trainPanel.add(this.trainFromFileButton, null);
		this.trainPanel.add(this.trainFromDBButton, null); // <------------------
		// trainPanel.add(trainFromFilePane, null);
		this.trainPanel.add(this.trainProgressBar, null);
		this.trainPanel.add(this.trainTreeLabel, null);
		// trainPanel.add(trainPruneLabel, null);
		// trainPanel.add(trainPruneTextField, null);
		this.trainPanel.add(this.trainReverseCheckBox, null);
		this.trainPanel.add(this.trainICCheckbox, null);
		this.trainPanel.add(this.trainParamaterLabel, null);

		this.pretreeTabbedPane.add(this.testPanel, "Classify");
		this.testPanel.add(this.classifyFile);
		this.testPanel.add(this.classifyToFile);
		this.testPanel.add(this.testStatusLabel, null);
		this.testPanel.add(this.testTreeStatusLabel, null);
		// testPanel.add(testReverseCheckbox, null);
		// testPanel.add(testICCheckbox, null);
		this.testPanel.add(this.testTreeLoadButton, null);
		// testPanel.add(testLoadTextField, null);
		// testPanel.add(testCharacteristicsLabel, null);
		this.testPanel.add(this.testTreeLoadedLabel, null);
		this.testPanel.add(this.testIsIgnoreCaseLabel, null);
		this.testPanel.add(this.testNrClassesLabel, null);
		this.testPanel.add(this.testNrNodesLabel, null);
		this.testPanel.add(this.testIsReverseLabel, null);
		this.testPanel.add(this.testTextScrollPane, null);
		this.testTextScrollPane.getViewport().add(this.testTextArea, null);
		this.testPanel.add(this.testWordScrollPane, null);
		this.testPanel.add(this.testFileButton, null);
		this.testPanel.add(this.testProgressBar, null);
		// testPanel.add(testFileTextField, null);
		this.testPanel.add(this.testWordButton, null);
		this.testPanel.add(this.testWordField, null);
		this.testPanel.add(this.trainPruneLabel);
		this.testPanel.add(this.trainPruneTextField);
		this.testPanel.add(this.testClassifiedCT, null);

		this.testWordScrollPane.getViewport().add(this.testClassifiedJTable,
				null);
		this.trainSetScrollPane.getViewport().add(this.trainSetTable, null);

		this.trainStatusLabel.setText("pretree tool initialized");
		this.testStatusLabel.setText("pretree tool initialized");

		// dbpanel.add(applyChangesToFile, null);
		// dbpanel.add(ct, null);
		// dbpanel.add(delSelected, null);
		this.pretreeTabbedPane.add(this.dbpanel, "DB Connection");
	} // end JBinit

	protected void classifyFileToFile(String inputFile) {
		if (pretree != null) {
			double thresh = (new Double(this.trainPruneTextField.getText()))
					.doubleValue();
			this.pretree.setThresh(thresh);
			try {
				Scanner fileScanner = new Scanner(new File(inputFile));
				PrintWriter pw = new PrintWriter(new File(outputfile));
				String word, wclass;
				while (fileScanner.hasNextLine()) {
					word = fileScanner.nextLine();
					wclass = pretree.classify(word);
					pw.println(word + "\t" + wclass);
				}
				pw.close();
				fileScanner.close();
				this.testStatusLabel.setText("finished writing output to file");
				this.repaint();
			} catch (FileNotFoundException e) {
				this.testStatusLabel.setText("input file does not exists");
				this.repaint();
				return;
			}

		} else {
			this.testStatusLabel
					.setText("You need to train or load a tree first");
			this.repaint();

		}
	}

	@SuppressWarnings("unchecked")
	protected void classifyFile(String inputFile) {
		if (pretree != null) {
			if (this.testClassifiedVectorNameTable == null) {
				this.testClassifiedVectorNameTable = new VectorNameTable();
			}
			double thresh = (new Double(this.trainPruneTextField.getText()))
					.doubleValue();
			this.pretree.setThresh(thresh);
			try {
				Scanner fileScanner = new Scanner(new File(inputFile));
				String word, wclass;
				while (fileScanner.hasNextLine()) {
					word = fileScanner.nextLine();
					wclass = pretree.classify(word);
					VectorNameTable adder = new VectorNameTable();
					// System.out.println(word+" classified as "+wclass);
					String[] toAdd = { word, wclass };
					adder.add(toAdd);
					this.testClassifiedVectorNameTable.insert(adder);

					this.testClassifiedCT
							.addData(this.testClassifiedVectorNameTable);
					setVisible(true);
					this.repaint();
				}
				fileScanner.close();
				this.testStatusLabel.setText("finished classify file");
				this.repaint();

			} catch (FileNotFoundException e) {
				this.testStatusLabel.setText("input file does not exists");
				this.repaint();
				return;
			}

		} else {
			this.testStatusLabel
					.setText("You need to train or load a tree first");
			this.repaint();

		}
	}

	// Überschreiben, damit das Programm bei Herunterfahren des Systems beendet
	// werden kann
	// protected void processWindowEvent(WindowEvent e) {
	// super.processWindowEvent(e);
	// if(e.getID() == WindowEvent.WINDOW_CLOSING) {
	// System.exit(0);
	// }
	// }

	// functions
	/*
	 * not in use anymore (because of the use of the VectorNameTable)
	 * 
	 * private static Vector nameTable2Vector(NameTable nt) { Vector result =
	 * new Vector();
	 * 
	 * if ( nt.isEmpty() ) return null; if ( nt == null ) return null;
	 * 
	 * Enumeration keys_enum = nt.keys(); String[] keys = new String[ nt.size() ];
	 * int i = 0;
	 * 
	 * while ( keys_enum.hasMoreElements() ) { keys[i] = (String)
	 * keys_enum.nextElement(); String[] row = { keys[i], (String)
	 * nt.get(keys[i]) }; result.add( row ); i += 1; }
	 * 
	 * return result; }
	 * 
	 * private static NameTable vector2NameTable(Vector v) { NameTable result =
	 * new NameTable();
	 * 
	 * Iterator it = v.iterator(); String key = ""; String value = "";
	 * 
	 * while ( it.hasNext() ) { String[] values = (String[]) it.next();
	 * 
	 * key = values[0]; value = values[1];
	 * 
	 * result.put( key, value ); }
	 * 
	 * return result; }
	 * 
	 * private static JTable nameTable2jTable(NameTable source) {
	 * 
	 * String actItem; String actClass;
	 * 
	 * String columns[] = { "Item", "Class" }; String rows[][] = new
	 * String[source.size()][2];
	 * 
	 * int i = 0; for (Enumeration e = source.keys(); e.hasMoreElements();) {
	 * actItem = (String) e.nextElement(); actClass = (String)
	 * source.get(actItem); rows[i][0] = actItem; rows[i][1] = actClass; i++; } //
	 * rof enum e
	 * 
	 * DefaultTableModel model = new DefaultTableModel(rows, columns);
	 * TableSorter sorter = new TableSorter(model); JTable returnTable = new
	 * JTable(sorter); sorter.setTableHeader(returnTable.getTableHeader());
	 * returnTable.getColumnModel().getColumn(0).setWidth(50); return
	 * returnTable; }
	 * 
	 * private void writeJTableToFile(JTable table, File file) {
	 * 
	 * try { TableModel m = table.getModel(); BufferedWriter bw = new
	 * BufferedWriter(new FileWriter(file)); for (int i = 0; i <
	 * m.getRowCount(); i++) { bw.write((String) m.getValueAt(i, 0));
	 * bw.write(_tab); bw.write((String) m.getValueAt(i, 1)); bw.newLine(); }
	 * bw.close(); } catch (Exception e) { System.err.println(e.getMessage());
	 * e.printStackTrace(); } }
	 */

	protected void connect() {
		if (!PretreePanel.this.mInputDriver.getText().equals("")
				&& !PretreePanel.this.mInputProtokol.getText().equals("")
				&& !PretreePanel.this.mInputUrl.getText().equals("")
				&& !PretreePanel.this.mInputPort.getText().equals("")
				&& !PretreePanel.this.mInputUser.getText().equals("")
				&& !PretreePanel.this.mInputDb.getText().equals("")) {
			//hashmap new
			this.databaseinfo = new HashMap<String, Vector<String>>();
			// test ob db existiert
			DBConnection dbcon = new DBConnection();
			dbcon.setDriverClass(PretreePanel.this.mInputDriver.getText());
			dbcon.setUserID(PretreePanel.this.mInputUser.getText());
			dbcon.setPassWd(new String(PretreePanel.this.mInputPass
					.getPassword()));
			dbcon.setDbURL("jdbc:" + PretreePanel.this.mInputProtokol.getText()
					+ "://" + PretreePanel.this.mInputUrl.getText() + ":"
					+ PretreePanel.this.mInputPort.getText() + "/");
			// show tables und dann show colums from tables und in hashmap tun
			CommonDB cdb = new CommonDB(dbcon, PretreePanel.this.queryfile,
					false);
			boolean isdatabase = false;
			try {
				Vector res = cdb.executeQueryWithResults("SHOW DATABASES;",
						null);
				isdatabase = false;
				for (int i = 0; i < res.size(); i++) {
					if (((String[]) res.get(i))[0].equals(this.mInputDb
							.getText()))
						isdatabase = true;
				}
				if (!isdatabase) {
					// System.out.println("NO DATABASE!");
					this.mInputDb.setText("");
					this.databaseinfo = new HashMap<String, Vector<String>>();
					PretreePanel.this.mInputTable.removeAllItems();

				}
			} catch(NullPointerException e0){
			
			}catch (SQLException e1) {

				e1.printStackTrace();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			if (!isdatabase)
				return;
			// stell verbindung zur datenbank her
			dbcon = new DBConnection();
			dbcon.setDriverClass(PretreePanel.this.mInputDriver.getText());
			dbcon.setUserID(PretreePanel.this.mInputUser.getText());
			dbcon.setPassWd(new String(PretreePanel.this.mInputPass
					.getPassword()));
			dbcon.setDbURL("jdbc:" + PretreePanel.this.mInputProtokol.getText()
					+ "://" + PretreePanel.this.mInputUrl.getText() + ":"
					+ PretreePanel.this.mInputPort.getText() + "/"
					+ PretreePanel.this.mInputDb.getText());
			// show tables und dann show colums from tables und in hashmap tun
			cdb = new CommonDB(dbcon, PretreePanel.this.queryfile, false);
			try {
				Vector<String[]> res = cdb.executeQueryWithResults(
						"SHOW tables;", null);
				for (int i = 0; i < res.size(); i++) {
					Vector<String[]> resTables = cdb.executeQueryWithResults(
							"SHOW columns FROM " + res.get(i)[0], null);
					Vector<String> col = new Vector<String>();
					for (int j = 0; j < resTables.size(); j++) {
						col.add(resTables.get(j)[0]);
					}
					PretreePanel.this.databaseinfo.put(res.get(i)[0], col);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// in combobox strings setzen
			Iterator it = PretreePanel.this.databaseinfo.keySet().iterator();
			PretreePanel.this.mInputTable.removeAllItems();
			while (it.hasNext()) {
				PretreePanel.this.mInputTable.addItem(it.next());
			}
			this.mInputTable.setSelectedItem(this.prop.getProperty(
					"pretree.tableName_getTable", ""));
			PretreePanel.this.mInputWordColumn.removeAllItems();
			PretreePanel.this.mInputClassColumn.removeAllItems();
			PretreePanel.this.mInputIdColumn.removeAllItems();
			Vector<String> cols = PretreePanel.this.databaseinfo
					.get(PretreePanel.this.mInputTable.getSelectedItem());
			if (cols == null)
				return;
			for (int i = 0; i < cols.size(); i++) {
				PretreePanel.this.mInputWordColumn.addItem(cols.get(i));
				PretreePanel.this.mInputClassColumn.addItem(cols.get(i));
				PretreePanel.this.mInputIdColumn.addItem(cols.get(i));
			}
			this.mInputWordColumn.setSelectedItem(this.prop.getProperty(
					"pretree.wordcolumn", ""));
			this.mInputClassColumn.setSelectedItem(this.prop.getProperty(
					"pretree.classcolumn", ""));
			this.mInputIdColumn.setSelectedItem(this.prop.getProperty(
					"pretree.idcolumn", ""));
			// mInputTable.getActionListeners()[0].actionPerformed(null);

		}

	}

	private void writeVectorToFile(Vector<Object> v, File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			Iterator<Object> it = v.iterator();

			while (it.hasNext()) {
				String[] row = (String[]) it.next();
				bw.write(row[0]);
				bw.write(PretreePanel._tab);
				bw.write(row[1]);
				bw.newLine();
			}

			bw.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// Button Listener

	public void delSelected_actionPerformed(ActionEvent e) {
		this.ct.deleteSelected();

		// Vector test = ct.getTableData();
		// Iterator it = test.iterator();
		// while ( it.hasNext() )
		// {
		// String[] array = (String[])it.next();
		// int l = array.length;
		// for ( int i = 0; i < l; i++ )
		// System.out.println( "value["+i+"] = " + array[i] );
		// System.out.println( "\n" );
		// }
	}

	// not up to date!!!
	/*
	 * public void applyChanges_actionPerformed(ActionEvent e) {
	 * dbcon.setDriverClass(driverClass_jtf.getText());
	 * dbcon.setDbURL(dbURL_jtf.getText());
	 * dbcon.setUserID(userID_jtf.getText()); char[] pw =
	 * passwd_jtf.getPassword(); String passwd = ""; for (char element : pw) {
	 * passwd = passwd + element; } dbcon.setPassWd(passwd); }
	 * 
	 * public void applyChangesToFile_actionPerformed(ActionEvent e) {
	 * dbcon.setDriverClass(driverClass_jtf.getText());
	 * dbcon.setDbURL(dbURL_jtf.getText() + ":" + dbPort_jtf.getText() + "/" +
	 * dbDatabase_jtf.getText()); dbcon.setUserID(userID_jtf.getText()); char[]
	 * pw = passwd_jtf.getPassword(); String passwd = ""; for (char element :
	 * pw) { passwd = passwd + element; } dbcon.setPassWd(passwd);
	 * 
	 * prop.setProperty("pretree.driverClass", dbcon.getDriverClass());
	 * prop.setProperty("pretree.dbURL", dbURL_jtf.getText());
	 * prop.setProperty("pretree.userID", dbcon.getUserID());
	 * prop.setProperty("pretree.passwd", dbcon.getPassWd());
	 * prop.setProperty("pretree.dbPort", dbPort_jtf.getText());
	 * prop.setProperty("pretree.dbDatabase", dbDatabase_jtf.getText());
	 * 
	 * FileOutputStream fos; try { fos = new FileOutputStream(queryfile);
	 * prop.store(fos, null); } catch (FileNotFoundException e1) {
	 * 
	 * e1.printStackTrace(); } catch (IOException ioe) {
	 * 
	 * ioe.printStackTrace(); }
	 *  }
	 */

	public void trainFromDBButton_actionPerformed(ActionEvent e) {
		// dbcon = null;

		/*
		 * dbcon.setDriverClass("com.mysql.jdbc.Driver");
		 * dbcon.setDbURL("jdbc:mysql://localhost:3306/db_test");
		 * dbcon.setUserID("root"); dbcon.setPassWd("");
		 */
		/*
		 * if ( openDB == null ) openDB = new OpenDB( this );
		 * 
		 * openDB.showDialog( this, "DB Connection" );
		 */
		System.out.println(((String) this.mInputTable.getSelectedItem()));
		if (this.mInputDriver.getText().equals("")
				|| this.mInputProtokol.getText().equals("")
				|| this.mInputUrl.getText().equals("")
				|| this.mInputUser.getText().equals("")
				|| new String(this.mInputPass.getPassword()).equals("")
				|| this.mInputPort.getText().equals("")
				|| this.mInputDb.getText().equals("")
				|| (((String) this.mInputTable.getSelectedItem()) == null)
				|| (((String) this.mInputWordColumn.getSelectedItem()) == null)
				|| (((String) this.mInputClassColumn.getSelectedItem()) == null)
				|| (((String) this.mInputIdColumn.getSelectedItem()) == null)) {
			this.trainStatusLabel.setText("DB Connection is not set");
			return;
		}
		String query = "SELECT "
				+ ((String) this.mInputWordColumn.getSelectedItem()) + ","
				+ ((String) this.mInputClassColumn.getSelectedItem())
				+ " FROM " + ((String) this.mInputTable.getSelectedItem())
				+ " WHERE " + ((String) this.mInputIdColumn.getSelectedItem())
				+ ">=" + ((Integer) this.mInputWord_ID.getValue()).intValue()
				+ " LIMIT "
				+ ((Integer) this.mInputWordOffset.getValue()).intValue() + ";";
		// Speichern der dbcon daten in der propertiefile
		this.prop.setProperty("pretree.query_getTable", query);
		this.prop.setProperty("pretree.driverClass", this.mInputDriver
				.getText());
		this.prop
				.setProperty("pretree.protokol", this.mInputProtokol.getText());
		this.prop.setProperty("pretree.dbURL", this.mInputUrl.getText());
		this.prop.setProperty("pretree.userID", this.mInputUser.getText());
		this.prop.setProperty("pretree.passwd", new String(this.mInputPass
				.getPassword()));
		this.prop.setProperty("pretree.dbPort", this.mInputPort.getText());
		this.prop.setProperty("pretree.dbDatabase", this.mInputDb.getText());
		this.prop.setProperty("pretree.tableName_getTable",
				((String) this.mInputTable.getSelectedItem()));
		this.prop.setProperty("pretree.wordcolumn",
				((String) this.mInputWordColumn.getSelectedItem()));
		this.prop.setProperty("pretree.classcolumn",
				((String) this.mInputClassColumn.getSelectedItem()));
		this.prop.setProperty("pretree.idcolumn", ((String) this.mInputIdColumn
				.getSelectedItem()));
		this.prop
				.setProperty("pretree.startatid", ((Integer) this.mInputWord_ID
						.getModel().getValue()).toString());
		this.prop.setProperty("pretree.numberofwords",
				((Integer) this.mInputWordOffset.getModel().getValue())
						.toString());
		try {
			FileOutputStream fis = new FileOutputStream(this.queryfile);
			this.prop.store(fis, null);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		if (this.dbcon != null) {
			this.dbcon.setDriverClass(this.mInputDriver.getText());
			this.dbcon.setUserID(this.mInputUser.getText());
			this.dbcon.setPassWd(new String(this.mInputPass.getPassword()));
			this.dbcon
					.setDbURL("jdbc:" + this.mInputProtokol.getText() + "://"
							+ this.mInputUrl.getText() + ":"
							+ this.mInputPort.getText() + "/"
							+ this.mInputDb.getText());

			final JPanel parent = this;

			Thread t = new Thread() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					VectorNameTable loader = new VectorNameTable();
					try {
						loader.loadDB(PretreePanel.this.dbcon,
								PretreePanel.this.queryfile);
					} catch (Exception exc) {
						exc.printStackTrace();
					}
					/*
					 * trainSetNameTable.insert(loader);
					 * trainSetTable=nameTable2jTable(trainSetNameTable);
					 * trainSetScrollPane.getViewport().add(trainSetTable,
					 * null); this.setVisible(true);
					 */

					// String aktword = "";
					// String wclass = "";
					boolean reverse = PretreePanel.this.trainReverseCheckBox
							.isSelected();
					boolean ignoreCase = PretreePanel.this.trainICCheckbox
							.isSelected();
					PretreePanel.this.pretree = new Pretree();
					PretreePanel.this.pretree.setIgnoreCase(ignoreCase);
					PretreePanel.this.pretree.setReverse(reverse);

					int progress = 0;
					int percentage = 0;
					int maxprogress = loader.size();
					int progresssteps = (int) (maxprogress / 100.0);
					if (progresssteps == 0) {
						progresssteps = 1;
					}

					PretreePanel.this.trainProgressBar.setValue(0);
					PretreePanel.this.trainProgressBar.setStringPainted(true);

					Iterator it = loader.iterator();
					while (it.hasNext()) {
						String[] toClassify = (String[]) it.next();
						PretreePanel.this.pretree.train(toClassify[0],
								toClassify[1]);

						progress++;
						if ((progress % progresssteps) == 0) { // set progress
							// bar
							percentage = (int) ((double) (progress) * 100 / (maxprogress));
							PretreePanel.this.trainProgressBar
									.setValue(percentage);
							// System.out.println(percentage+"% done");
						}
					}

					// for (Enumeration f = loader.keys(); f.hasMoreElements();)
					// {
					// aktword = (String) f.nextElement();
					// wclass = (String) loader.get(aktword);
					// pretree.train(aktword, wclass);
					// // if (d) System.out.println("training: "+aktword+" ->
					// // "+wclass);
					// progress++;
					// if ((progress % progresssteps) == 0) { // set progress
					// bar
					// percentage = (int) ((double) (progress) * 100 / (double)
					// (maxprogress));
					// trainProgressBar.setValue(percentage);
					// // System.out.println(percentage+"% done");
					// }
					// } // rof Enum e
					PretreePanel.this.trainProgressBar.setValue(100);
					// buttons enablen true
					PretreePanel.this.trainSetTable.setEnabled(true);
					PretreePanel.this.trainSetCT.setEnabled(true);
					PretreePanel.this.trainingAddWordField.setEnabled(true);
					PretreePanel.this.trainingAddClassField.setEnabled(true);
					PretreePanel.this.trainReverseCheckBox.setEnabled(true);
					PretreePanel.this.trainICCheckbox.setEnabled(true);
					PretreePanel.this.trainClearTrainButton.setEnabled(true);
					PretreePanel.this.trainAddFileButton.setEnabled(true);
					PretreePanel.this.trainSaveFileButton.setEnabled(true);
					PretreePanel.this.trainingAddLineButton.setEnabled(true);
					PretreePanel.this.trainingSetDeleteButton.setEnabled(true);
					PretreePanel.this.trainButton.setEnabled(true);
					PretreePanel.this.trainFromFileButton.setEnabled(true);
					PretreePanel.this.trainFromDBButton.setEnabled(true);
					PretreePanel.this.trainPruneButton.setEnabled(true);
					PretreePanel.this.trainSaveTreeButton.setEnabled(true);
					// buttons enable true finished
					// System.out.println("training done.");
					PretreePanel.this.testTreeLoadedLabel
							.setText("Trained Tree active");
					if (ignoreCase) {
						PretreePanel.this.testIsIgnoreCaseLabel
								.setText("Ignore Case: yes");
					} else {
						PretreePanel.this.testIsIgnoreCaseLabel
								.setText("Ignore Case: no ");
					}
					if (reverse) {
						PretreePanel.this.testIsReverseLabel
								.setText("Reverse: yes");
					} else {
						PretreePanel.this.testIsReverseLabel
								.setText("Reverse: no ");
					}
					PretreePanel.this.testNrClassesLabel.setText("Classes: "
							+ PretreePanel.this.pretree.getNrOfClasses());
					PretreePanel.this.testNrNodesLabel.setText("Nodes: "
							+ PretreePanel.this.pretree.getNrOfNodes());
					PretreePanel.this.trainStatusLabel.setText("tree trained");
					PretreePanel.this.testStatusLabel.setText("tree trained");
					if (PretreePanel.this.testClassifiedVectorNameTable != null) {
						PretreePanel.this.testClassifiedVectorNameTable = new VectorNameTable();
						// testClassifiedJTable =
						// nameTable2jTable(testClassifiedNameTable);
						PretreePanel.this.testClassifiedCT
								.addData(PretreePanel.this.testClassifiedVectorNameTable);
						PretreePanel.this.testWordScrollPane.getViewport().add(
								PretreePanel.this.testClassifiedJTable, null);
					}
					parent.repaint();
				}
			};
			t.start();
			// buttons enable false
			this.trainSetTable.setEnabled(false);
			this.trainSetCT.setEnabled(false);
			this.trainingAddWordField.setEnabled(false);
			this.trainingAddClassField.setEnabled(false);
			this.trainReverseCheckBox.setEnabled(false);
			this.trainICCheckbox.setEnabled(false);
			this.trainClearTrainButton.setEnabled(false);
			this.trainAddFileButton.setEnabled(false);
			this.trainSaveFileButton.setEnabled(false);
			this.trainingAddLineButton.setEnabled(false);
			this.trainingSetDeleteButton.setEnabled(false);
			this.trainButton.setEnabled(false);
			this.trainFromFileButton.setEnabled(false);
			this.trainFromDBButton.setEnabled(false);
			this.trainPruneButton.setEnabled(false);
			this.trainSaveTreeButton.setEnabled(false);
			// buttons enable false finished

		}
	}

	@SuppressWarnings("unchecked")
	void trainClearTrainButton_actionPerformed(ActionEvent e) {

		if (this.trainSetVectorNameTable != null) {
			if (this.d) {
				System.out.println("Clear!");
			}
			this.trainSetVectorNameTable = new VectorNameTable();
			// trainSetTable = nameTable2jTable(trainSetNameTable);
			this.trainSetCT.addData(this.trainSetVectorNameTable);
			// trainSetScrollPane.getViewport().add(trainSetTable, null);
			this.trainSetVectorNameTable = null;
			this.trainStatusLabel.setText("training set cleared");

			this.trainProgressBar.setValue(0);
			this.trainProgressBar.setStringPainted(true);

			setVisible(true);
			this.repaint();
		}
	}

	@SuppressWarnings("unchecked")
	void trainAddFileButton_actionPerformed(ActionEvent e) throws IOException {

		String filename = this.cfc.showDialogAndReturnFilename(this, "Open");

		if (filename != null) {

			// NameTable loader = new NameTable();
			// loader.loadFile(filename);

			VectorNameTable loader = new VectorNameTable();
			loader.loadFile(filename);
			this.trainSetVectorNameTable = new VectorNameTable();
			this.trainSetVectorNameTable.insert(loader);
			boolean hasEmptyFields = false;
			for (int i = 0; i < loader.size(); i++) {
				String[] row = (String[]) loader.get(i);
				for (int j = 0; j < row.length; j++) {
					if ((row[j] == null) || row[j].equals("")) {
						hasEmptyFields = true;
					}
				}
			}
			if (hasEmptyFields) {
				this.trainStatusLabel
						.setText("loaded File has incorrect Format");
				return;
			}
			// trainSetCT.addData( nameTable2Vector( loader ) );
			this.trainSetCT.addData(loader);

			/*
			 * trainSetTable = nameTable2jTable(trainSetNameTable);
			 * trainSetScrollPane.getViewport().add(trainSetTable, null);
			 */

			this.trainStatusLabel.setText("added " + filename);

			this.trainProgressBar.setValue(0);
			this.trainProgressBar.setStringPainted(true);

			setVisible(true);
		}

		// JFileChooser loadChooser;
		// if( _aktDir!=null ){
		// loadChooser = new JFileChooser(_aktDir);
		// }else{
		// loadChooser = new JFileChooser();
		// }
		// if( loadChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
		// ){
		// File file = loadChooser.getSelectedFile();
		// String filename = file.getPath();;
		// NameTable loader=new NameTable();
		// loader.loadFile(filename);
		// trainSetNameTable.insert(loader);
		// trainSetTable=nameTable2jTable(trainSetNameTable);
		// trainSetScrollPane.getViewport().add(trainSetTable, null);
		// trainStatusLabel.setText("added "+filename);
		//
		// trainProgressBar.setValue(0);
		// trainProgressBar.setStringPainted(true);
		//
		// this.setVisible(true);
		// }
	}

	@SuppressWarnings("unchecked")
	void trainingSetDeleteButton_actionPerformed(ActionEvent e)
			throws IOException {

		this.trainSetCT.deleteSelected();

		// trainSetNameTable = vector2NameTable( trainSetCT.getTableData() );
		// trainSetVectorNameTable = (VectorNameTable)
		// trainSetCT.getTableData();

		this.trainSetVectorNameTable = new VectorNameTable();

		Iterator<Object> it = this.trainSetCT.getTableData().iterator();

		while (it.hasNext()) {
			this.trainSetVectorNameTable.add(it.next());
		}

		this.trainProgressBar.setValue(0);
		this.trainProgressBar.setStringPainted(true);

		setVisible(true);
		this.repaint();

		/*
		 * if (trainSetNameTable != null) { String delItem = ""; int rowCount =
		 * trainSetTable.getSelectedRowCount(); int[] selectedRows =
		 * trainSetTable.getSelectedRows(); for (int i = 0; i < rowCount; i++) {
		 * delItem = (String) trainSetTable.getValueAt(selectedRows[i], 0); if
		 * (d) System.out.println("Deleting: " + delItem);
		 * trainSetNameTable.remove(delItem); } // rof trainSetTable =
		 * nameTable2jTable(trainSetNameTable);
		 * trainSetScrollPane.getViewport().add(trainSetTable, null);
		 * 
		 * trainProgressBar.setValue(0);
		 * trainProgressBar.setStringPainted(true);
		 * 
		 * this.setVisible(true); this.repaint(); }
		 */
	}

	@SuppressWarnings("unchecked")
	void trainingAddLineButton_actionPerformed(ActionEvent e)
			throws IOException {

		if (this.trainSetVectorNameTable == null) {
			this.trainSetVectorNameTable = new VectorNameTable();
		}

		String newItem = this.trainingAddWordField.getText();
		String newClass = this.trainingAddClassField.getText();
		if (newItem.equals("")) {
			this.trainStatusLabel.setText("empty Items could not be added");
			return;
		}
		if (newClass.equals("")) {
			this.trainStatusLabel
					.setText("Items with empty Class could not be added");
			return;
		}

		String[] row = { newItem, newClass };
		this.trainSetCT.addRow(row);

		// trainSetNameTable = vector2NameTable( trainSetCT.getTableData() );
		// trainSetVectorNameTable = (VectorNameTable)
		// trainSetCT.getTableData();

		Iterator<Object> it = this.trainSetCT.getTableData().iterator();

		while (it.hasNext()) {
			this.trainSetVectorNameTable.add(it.next());
		}

		/*
		 * NameTable adder = new NameTable(); adder.put(newItem, newClass);
		 * trainSetNameTable.insert(adder); trainSetTable =
		 * nameTable2jTable(trainSetNameTable);
		 * trainSetScrollPane.getViewport().add(trainSetTable, null);
		 */

		this.trainProgressBar.setValue(0);
		this.trainProgressBar.setStringPainted(true);

		setVisible(true);
		this.repaint();
	}

	@SuppressWarnings("unchecked")
	void trainSaveFileButton_actionPerformed(ActionEvent e) throws IOException {

		if (this.trainSetVectorNameTable != null) {

			String filename = this.cfc
					.showDialogAndReturnFilename(this, "Save");

			if (filename != null) {
				// writeJTableToFile(trainSetTable, new File(filename));
				writeVectorToFile(this.trainSetCT.getTableData(), new File(
						filename));
				this.trainStatusLabel.setText("file saved: " + filename);
				this.repaint();
			} else {
				this.trainStatusLabel
						.setText("You need to add or load a training data first");
				this.repaint();
			}
		}
		// JFileChooser loadChooser;
		// if( _aktDir!=null ){
		// loadChooser = new JFileChooser(_aktDir);
		// }else{
		// loadChooser = new JFileChooser();
		// }
		// if( loadChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION
		// ){
		// File file = loadChooser.getSelectedFile();
		// String filename = file.getPath();
		// writeJTableToFile(trainSetTable,file);
		// trainStatusLabel.setText("file saved: "+filename);
		// this.repaint();
		// //System.out.println("File saved: "+filename);
		// }
		// }else{
		// trainStatusLabel.setText("You need to add or load a training data
		// first");
		// this.repaint();
		// }
	}

	@SuppressWarnings("unchecked")
	void trainPruneButton_actionPerformed(ActionEvent e) {
		if (this.pretree != null) {
			// double thresh = 0.51;
			// pretree.setThresh(thresh);
			this.pretree.prune();
			this.testTreeLoadedLabel.setText("Pruned Trained Tree active");
			boolean reverse = this.pretree.getReverse();
			boolean ignoreCase = this.pretree.getIgnoreCase();
			if (ignoreCase) {
				this.testIsIgnoreCaseLabel.setText("Ignore Case: yes");
			} else {
				this.testIsIgnoreCaseLabel.setText("Ignore Case: no ");
			}
			if (reverse) {
				this.testIsReverseLabel.setText("Reverse: yes");
			} else {
				this.testIsReverseLabel.setText("Reverse: no ");
			}
			this.testNrClassesLabel.setText("Classes: "
					+ this.pretree.getNrOfClasses());
			this.testNrNodesLabel.setText("Nodes: "
					+ this.pretree.getNrOfNodes());
			this.trainStatusLabel.setText("tree pruned");
			this.testStatusLabel.setText("tree pruned");
			if (this.testClassifiedVectorNameTable != null) {
				this.testClassifiedVectorNameTable = new VectorNameTable();
				// testClassifiedJTable =
				// nameTable2jTable(testClassifiedNameTable);
				this.testClassifiedCT
						.addData(this.testClassifiedVectorNameTable);
				this.testWordScrollPane.getViewport().add(
						this.testClassifiedJTable, null);
			}
			this.repaint();
		} else {
			this.trainStatusLabel
					.setText("You need to train or load a tree first");
			this.repaint();
		}
	}

	void trainSaveTreeButton_actionPerformed(ActionEvent e) throws IOException {
		if (this.pretree != null) {

			String filename = this.cfc
					.showDialogAndReturnFilename(this, "Save");

			if (filename != null) {
				this.pretree.save(filename);
				this.trainStatusLabel.setText("file saved: " + filename);
				this.repaint();
			} else {
				this.trainStatusLabel
						.setText("You need to train or load a tree first");
				this.repaint();
			}
		}
		// JFileChooser loadChooser;
		// if( _aktDir!=null ){
		// loadChooser = new JFileChooser(_aktDir);
		// }else{
		// loadChooser = new JFileChooser();
		// }
		// if( loadChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION
		// ){
		// File file = loadChooser.getSelectedFile();
		// String filename = file.getPath();;
		// pretree.save(filename);
		// trainStatusLabel.setText("file saved: "+filename);
		// this.repaint();
		// }
		// }else{
		// trainStatusLabel.setText("You need to train or load a tree first");
		// this.repaint();
		// }
	}

	// testpanel buttons
	@SuppressWarnings("unchecked")
	void testTreeLoadButton_actionPerformed(ActionEvent e) throws IOException,
			ClassNotFoundException {

		// Todo del erg-list

		String filename = this.cfc.showDialogAndReturnFilename(this, "Open");

		if (filename != null) {
			this.pretree = new Pretree();
			this.pretree.load(filename);
			boolean reverse = this.pretree.getReverse();
			boolean ignoreCase = this.pretree.getIgnoreCase();
			if (ignoreCase) {
				this.testIsIgnoreCaseLabel.setText("Ignore Case: yes");
			} else {
				this.testIsIgnoreCaseLabel.setText("Ignore Case: no ");
			}
			if (reverse) {
				this.testIsReverseLabel.setText("Reverse: yes");
			} else {
				this.testIsReverseLabel.setText("Reverse: no ");
			}
			this.testNrClassesLabel.setText("Classes: "
					+ this.pretree.getNrOfClasses());
			this.testNrNodesLabel.setText("Nodes: "
					+ this.pretree.getNrOfNodes());
			this.testTreeLoadedLabel.setText("Tree " + filename + " loaded");
			if (this.testClassifiedVectorNameTable != null) {
				this.testClassifiedVectorNameTable = new VectorNameTable();
				// testClassifiedJTable =
				// nameTable2jTable(testClassifiedNameTable);
				this.testClassifiedCT
						.addData(this.testClassifiedVectorNameTable);
				this.testWordScrollPane.getViewport().add(
						this.testClassifiedJTable, null);
				this.repaint();
			}
		}

		// JFileChooser loadChooser;
		// if( _aktDir!=null ){
		// loadChooser = new JFileChooser(_aktDir);
		// }else{
		// loadChooser = new JFileChooser();
		// }
		//
		// if( loadChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
		// ){
		// File file = loadChooser.getSelectedFile();
		// _aktDir = file.getParentFile();
		// pretree = new Pretree();
		// pretree.load(file.getPath());
		// boolean reverse=pretree.getReverse();
		// boolean ignoreCase=pretree.getIgnoreCase();
		// if( ignoreCase ){
		// testIsIgnoreCaseLabel.setText("Ignore Case: yes");
		// }else{
		// testIsIgnoreCaseLabel.setText("Ignore Case: no ");
		// }
		// if( reverse ){
		// testIsReverseLabel.setText("Reverse: yes");
		// }else{
		// testIsReverseLabel.setText("Reverse: no ");
		// }
		// testNrClassesLabel.setText("Classes: "+pretree.getNrOfClasses());
		// testNrNodesLabel.setText("Nodes: "+pretree.getNrOfNodes());
		// testTreeLoadedLabel.setText("Tree "+file.getPath()+" loaded");
		// if( testClassifiedNameTable != null ){
		// testClassifiedNameTable = new NameTable();
		// testClassifiedJTable=nameTable2jTable(testClassifiedNameTable);
		// testWordScrollPane.getViewport().add(testClassifiedJTable, null);
		// }
		// }
		// this.repaint();
	}

	@SuppressWarnings("unchecked")
	void testWordButton_actionPerformed(ActionEvent e) {

		if (this.pretree != null) {
			if (this.testClassifiedVectorNameTable == null) {
				this.testClassifiedVectorNameTable = new VectorNameTable();
			}
			double thresh = (new Double(this.trainPruneTextField.getText()))
					.doubleValue();
			this.pretree.setThresh(thresh);
			String word = this.testWordField.getText();
			String wclass = this.pretree.classify(word);
			// String reason=pretree.giveReason(word);
			// String printtext="\n"+reason+"\n'"+word+"' classified as
			// '"+wclass+"'\n";
			// String printtext="'"+word+"' classified as '"+wclass+"'\n";
			// if (d) System.out.println(printtext);
			// testClassifiedJTable.setText(testClassifiedJTable.getText()+"\n"+reason+"\n'"+word+"'
			// classified as '"+wclass+"'\n");

			VectorNameTable adder = new VectorNameTable();
			// System.out.println(word+" classified as "+wclass);
			String[] toAdd = { word, wclass };
			adder.add(toAdd);
			this.testClassifiedVectorNameTable.insert(adder);

			this.testClassifiedCT.addData(this.testClassifiedVectorNameTable);

			// testClassifiedJTable = nameTable2jTable(testClassifiedNameTable);
			// testWordScrollPane.getViewport().add(testClassifiedJTable, null);
			setVisible(true);
			this.repaint();
		} else {
			this.testStatusLabel
					.setText("You need to train or load a tree first");
			this.repaint();
		}
	}

	void testFileButton_actionPerformed(ActionEvent e) throws IOException {

		if (this.pretree != null) {

			final String filename = this.cfc.showDialogAndReturnFilename(this,
					"Open");

			if (filename != null) {
				Thread t = new Thread() {
					public void run() {
						PretreePanel.this.testSetVectorNameTable = new VectorNameTable();
						System.out.println("Loading " + filename + " ...");
						PretreePanel.this.testTextArea.repaint();
						try {
							PretreePanel.this.testSetVectorNameTable
									.loadFile(filename);
							boolean hasEmptyFields = false;
							for (int i = 0; i < PretreePanel.this.testSetVectorNameTable
									.size(); i++) {
								String[] row = (String[]) PretreePanel.this.testSetVectorNameTable
										.get(i);
								for (int j = 0; j < row.length; j++) {
									if ((row[j] == null) || row[j].equals("")) {
										hasEmptyFields = true;
									}
								}
							}
							if (hasEmptyFields) {
								PretreePanel.this.testStatusLabel
										.setText("loaded File has incorrect Format");
								return;
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("Classifying ...");
						PretreePanel.this.testTextArea.repaint();
						String word = "";
						String wclass = "";
						String pclass = "";
						String reasonStr = "";
						int right = 0, wrong = 0, undecided = 0;

						int progress = 0;
						int percentage = 0;
						int maxprogress = PretreePanel.this.testSetVectorNameTable
								.size();
						int progresssteps = (int) (maxprogress / 100.0);
						if (progresssteps == 0) {
							progresssteps = 1;
						}

						PretreePanel.this.testProgressBar.setValue(0);
						PretreePanel.this.testProgressBar
								.setStringPainted(true);

						Iterator it = PretreePanel.this.testSetVectorNameTable
								.iterator();
						while (it.hasNext()) {
							String[] toTest = (String[]) it.next();
							word = toTest[0];
							wclass = toTest[1];
							pclass = PretreePanel.this.pretree.classify(word);

							if (wclass.equals(pclass)) {
								right++;
							} else if (pclass.equals("undecided")) {
								undecided++;
								reasonStr += "\n'" + word + "' classified as '"
										+ pclass + "' instead of '" + wclass;
							} else {
								wrong++;
								reasonStr += "\n'" + word + "' classified as '"
										+ pclass + "' instead of '" + wclass;
							}
							progress++;
							if ((progress % progresssteps) == 0) { // set
																	// progress
								// bar
								percentage = (int) ((double) (progress) * 100 / (maxprogress));
								PretreePanel.this.testProgressBar
										.setValue(percentage);
								// System.out.println(percentage+"% done");
							}
						}
						PretreePanel.this.testProgressBar.setValue(100);

						// for (Enumeration f = testSetNameTable.keys(); f
						// .hasMoreElements();) {
						// word = (String) f.nextElement();
						// wclass = (String) testSetNameTable.get(word);
						// pclass = pretree.classify(word);
						// if (wclass.equals(pclass)) {
						// right++;
						// } else if (pclass.equals("undecided")) {
						// undecided++;
						// reasonStr += "\n'" + word + "' classified as '"
						// + pclass + "' instead of '" + wclass;
						// } else {
						// wrong++;
						// reasonStr += "\n'" + word + "' classified as '"
						// + pclass + "' instead of '" + wclass;
						// } // esle fi esle
						// } // for enum f
						double prec = right / ((double) right + (double) wrong);
						double recall = right
								/ ((double) right + (double) wrong + undecided);
						reasonStr += "\nEvaluation:\n Precision:\t" + prec
								+ "\n Recall:\t" + recall;

						PretreePanel.this.testTextArea.setText(reasonStr);
						PretreePanel.this.testTextArea.repaint();
					}
				};
				t.start();
			}

		} else {
			this.testStatusLabel
					.setText("You need to train or load a tree first");
			this.repaint();
		}

		// JFileChooser loadChooser;
		// if( _aktDir!=null ){
		// loadChooser = new JFileChooser(_aktDir);
		// }else{
		// loadChooser = new JFileChooser();
		// }
		// if( loadChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
		// ){
		// File file = loadChooser.getSelectedFile();
		//
		// testSetNameTable= new NameTable();
		// String filename=file.getPath();
		// System.out.println("Loading "+filename+" ...");
		// testTextArea.repaint();
		// testSetNameTable.loadFile(filename);
		// System.out.println("Classifying ...");
		// testTextArea.repaint();
		// String word="";
		// String wclass="";
		// String pclass="";
		// String reasonStr="";
		// int right=0, wrong=0, undecided=0;
		// for (Enumeration f=testSetNameTable.keys();f.hasMoreElements();) {
		// word=(String)f.nextElement();
		// wclass=(String)testSetNameTable.get(word);
		// pclass=pretree.classify(word);
		// if (wclass.equals(pclass)) {right++;}
		// else if (pclass.equals("undecided")) {
		// undecided++;
		// reasonStr+="\n'"+word+"' classified as '"+pclass+"' instead of
		// '"+wclass;
		// }
		// else {
		// wrong++;
		// reasonStr+="\n'"+word+"' classified as '"+pclass+"' instead of
		// '"+wclass;
		// } // esle fi esle
		// } // for enum f
		// double prec=(double)right/((double)right+(double)wrong);
		// double
		// recall=(double)right/((double)right+(double)wrong+(double)undecided);
		// reasonStr+="\nEvaluation:\n Precision:\t"+prec+"\n Recall:\t"+recall;
		//
		// testTextArea.setText(reasonStr);
		// }
		// testTextArea.repaint();
		// }else{
		// testStatusLabel.setText("You need to train or load a tree first");
		// this.repaint();
		// }
	}

	void trainButton_actionPerformed(ActionEvent e) {

		if (this.trainSetVectorNameTable != null) {

			final JPanel parent = this;

			Thread t = new Thread() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					// String aktword = "";
					// String wclass = "";
					boolean reverse = PretreePanel.this.trainReverseCheckBox
							.isSelected();
					boolean ignoreCase = PretreePanel.this.trainICCheckbox
							.isSelected();
					PretreePanel.this.pretree = new Pretree();
					PretreePanel.this.pretree.setIgnoreCase(ignoreCase);
					PretreePanel.this.pretree.setReverse(reverse);

					int progress = 0;
					int percentage = 0;
					int maxprogress = PretreePanel.this.trainSetVectorNameTable
							.size();
					int progresssteps = (int) (maxprogress / 100.0);
					if (progresssteps == 0) {
						progresssteps = 1;
					}

					PretreePanel.this.trainProgressBar.setValue(0);
					PretreePanel.this.trainProgressBar.setStringPainted(true);

					Iterator<Object> it = PretreePanel.this.trainSetVectorNameTable
							.iterator();
					while (it.hasNext()) {
						String[] toTrain = (String[]) it.next();
						PretreePanel.this.pretree.train(toTrain[0], toTrain[1]);

						progress++;
						if ((progress % progresssteps) == 0) { // set progress
							// bar
							percentage = (int) ((double) (progress) * 100 / (maxprogress));
							PretreePanel.this.trainProgressBar
									.setValue(percentage);
						}
					}

					// for (Enumeration f = trainSetNameTable.keys();
					// f.hasMoreElements();) {
					// aktword = (String) f.nextElement();
					// wclass = (String) trainSetNameTable.get(aktword);
					// pretree.train(aktword, wclass);
					// // if (d) System.out.println("training: "+aktword+" ->
					// // "+wclass);
					// progress++;
					// if ((progress % progresssteps) == 0) { // set progress
					// bar
					// percentage = (int) ((double) (progress) * 100 / (double)
					// (maxprogress));
					// trainProgressBar.setValue(percentage);
					// // System.out.println(percentage+"% done");
					// }
					// } // rof Enum e
					// System.out.println("training done.");
					PretreePanel.this.trainProgressBar.setValue(100);
					// buttons and ... enablen true
					PretreePanel.this.trainSetTable.setEnabled(true);
					PretreePanel.this.trainSetCT.setEnabled(true);
					PretreePanel.this.trainingAddWordField.setEnabled(true);
					PretreePanel.this.trainingAddClassField.setEnabled(true);
					PretreePanel.this.trainReverseCheckBox.setEnabled(true);
					PretreePanel.this.trainICCheckbox.setEnabled(true);
					PretreePanel.this.trainClearTrainButton.setEnabled(true);
					PretreePanel.this.trainAddFileButton.setEnabled(true);
					PretreePanel.this.trainSaveFileButton.setEnabled(true);
					PretreePanel.this.trainingAddLineButton.setEnabled(true);
					PretreePanel.this.trainingSetDeleteButton.setEnabled(true);
					PretreePanel.this.trainButton.setEnabled(true);
					PretreePanel.this.trainFromFileButton.setEnabled(true);
					PretreePanel.this.trainFromDBButton.setEnabled(true);
					PretreePanel.this.trainPruneButton.setEnabled(true);
					PretreePanel.this.trainSaveTreeButton.setEnabled(true);
					// buttons enable true finished
					PretreePanel.this.testTreeLoadedLabel
							.setText("Trained Tree active");
					if (ignoreCase) {
						PretreePanel.this.testIsIgnoreCaseLabel
								.setText("Ignore Case: yes");
					} else {
						PretreePanel.this.testIsIgnoreCaseLabel
								.setText("Ignore Case: no ");
					}
					if (reverse) {
						PretreePanel.this.testIsReverseLabel
								.setText("Reverse: yes");
					} else {
						PretreePanel.this.testIsReverseLabel
								.setText("Reverse: no ");
					}
					PretreePanel.this.testNrClassesLabel.setText("Classes: "
							+ PretreePanel.this.pretree.getNrOfClasses());
					PretreePanel.this.testNrNodesLabel.setText("Nodes: "
							+ PretreePanel.this.pretree.getNrOfNodes());
					PretreePanel.this.trainStatusLabel.setText("tree trained");
					PretreePanel.this.testStatusLabel.setText("tree trained");
					if (PretreePanel.this.testClassifiedVectorNameTable != null) {
						PretreePanel.this.testClassifiedVectorNameTable = new VectorNameTable();
						// testClassifiedJTable =
						// nameTable2jTable(testClassifiedNameTable);
						PretreePanel.this.testClassifiedCT
								.addData(PretreePanel.this.testClassifiedVectorNameTable);
						PretreePanel.this.testWordScrollPane.getViewport().add(
								PretreePanel.this.testClassifiedJTable, null);
					}
					parent.repaint();
				}
			};
			t.start();
			// buttons enable false
			this.trainSetTable.setEnabled(false);
			this.trainSetCT.setEnabled(false);
			this.trainingAddWordField.setEnabled(false);
			this.trainingAddClassField.setEnabled(false);
			this.trainReverseCheckBox.setEnabled(false);
			this.trainICCheckbox.setEnabled(false);
			this.trainClearTrainButton.setEnabled(false);
			this.trainAddFileButton.setEnabled(false);
			this.trainSaveFileButton.setEnabled(false);
			this.trainingAddLineButton.setEnabled(false);
			this.trainingSetDeleteButton.setEnabled(false);
			this.trainButton.setEnabled(false);
			this.trainFromFileButton.setEnabled(false);
			this.trainFromDBButton.setEnabled(false);
			this.trainPruneButton.setEnabled(false);
			this.trainSaveTreeButton.setEnabled(false);
			// buttons enable false finished
		} else {
			this.trainStatusLabel
					.setText("You need to add or load training data first");
			this.repaint();
		}
	}

	void trainFromFileButton_actionPerformed(ActionEvent e) {

		String filename = this.cfc.showDialogAndReturnFilename(this, "Open");

		if (filename != null) {

			final JPanel parent = this;
			final String fn = filename;

			Thread t = new Thread() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					VectorNameTable loader = new VectorNameTable();
					try {
						loader.loadFile(fn);
						boolean hasEmptyFields = false;
						for (int i = 0; i < loader.size(); i++) {
							String[] row = (String[]) loader.get(i);
							for (int j = 0; j < row.length; j++) {
								if ((row[j] == null) || row[j].equals("")) {
									hasEmptyFields = true;
								}
							}
						}
						if (hasEmptyFields) {
							PretreePanel.this.trainStatusLabel
									.setText("loaded File has incorrect Format");
							// buttons enablen true
							PretreePanel.this.trainSetTable.setEnabled(true);
							PretreePanel.this.trainSetCT.setEnabled(true);
							PretreePanel.this.trainingAddWordField
									.setEnabled(true);
							PretreePanel.this.trainingAddClassField
									.setEnabled(true);
							PretreePanel.this.trainReverseCheckBox
									.setEnabled(true);
							PretreePanel.this.trainICCheckbox.setEnabled(true);
							PretreePanel.this.trainClearTrainButton
									.setEnabled(true);
							PretreePanel.this.trainAddFileButton
									.setEnabled(true);
							PretreePanel.this.trainSaveFileButton
									.setEnabled(true);
							PretreePanel.this.trainingAddLineButton
									.setEnabled(true);
							PretreePanel.this.trainingSetDeleteButton
									.setEnabled(true);
							PretreePanel.this.trainButton.setEnabled(true);
							PretreePanel.this.trainFromFileButton
									.setEnabled(true);
							PretreePanel.this.trainFromDBButton
									.setEnabled(true);
							PretreePanel.this.trainPruneButton.setEnabled(true);
							PretreePanel.this.trainSaveTreeButton
									.setEnabled(true);
							// buttons enable true finished

							return;
						}

					} catch (Exception exc) {
						exc.printStackTrace();
					}
					/*
					 * trainSetNameTable.insert(loader);
					 * trainSetTable=nameTable2jTable(trainSetNameTable);
					 * trainSetScrollPane.getViewport().add(trainSetTable,
					 * null); this.setVisible(true);
					 */

					// String aktword = "";
					// String wclass = "";
					boolean reverse = PretreePanel.this.trainReverseCheckBox
							.isSelected();
					boolean ignoreCase = PretreePanel.this.trainICCheckbox
							.isSelected();
					PretreePanel.this.pretree = new Pretree();
					PretreePanel.this.pretree.setIgnoreCase(ignoreCase);
					PretreePanel.this.pretree.setReverse(reverse);

					int progress = 0;
					int percentage = 0;
					int maxprogress = loader.size();
					int progresssteps = (int) (maxprogress / 100.0);
					if (progresssteps == 0) {
						progresssteps = 1;
					}

					PretreePanel.this.trainProgressBar.setValue(0);
					PretreePanel.this.trainProgressBar.setStringPainted(true);

					Iterator it = loader.iterator();
					while (it.hasNext()) {
						String[] toTrain = (String[]) it.next();
						PretreePanel.this.pretree.train(toTrain[0], toTrain[1]);

						progress++;
						if ((progress % progresssteps) == 0) { // set progress
							// bar
							percentage = (int) ((double) (progress) * 100 / (maxprogress));
							PretreePanel.this.trainProgressBar
									.setValue(percentage);
						}
					}

					// for (Enumeration f = loader.keys(); f.hasMoreElements();)
					// {
					// aktword = (String) f.nextElement();
					// wclass = (String) loader.get(aktword);
					// pretree.train(aktword, wclass);
					// // if (d) System.out.println("training: "+aktword+" ->
					// // "+wclass);
					// progress++;
					// if ((progress % progresssteps) == 0) { // set progress
					// bar
					// percentage = (int) ((double) (progress) * 100 / (double)
					// (maxprogress));
					// trainProgressBar.setValue(percentage);
					// // System.out.println(percentage+"% done");
					// }
					// } // rof Enum e
					PretreePanel.this.trainProgressBar.setValue(100);
					// buttons enablen true
					PretreePanel.this.trainSetTable.setEnabled(true);
					PretreePanel.this.trainSetCT.setEnabled(true);
					PretreePanel.this.trainingAddWordField.setEnabled(true);
					PretreePanel.this.trainingAddClassField.setEnabled(true);
					PretreePanel.this.trainReverseCheckBox.setEnabled(true);
					PretreePanel.this.trainICCheckbox.setEnabled(true);
					PretreePanel.this.trainClearTrainButton.setEnabled(true);
					PretreePanel.this.trainAddFileButton.setEnabled(true);
					PretreePanel.this.trainSaveFileButton.setEnabled(true);
					PretreePanel.this.trainingAddLineButton.setEnabled(true);
					PretreePanel.this.trainingSetDeleteButton.setEnabled(true);
					PretreePanel.this.trainButton.setEnabled(true);
					PretreePanel.this.trainFromFileButton.setEnabled(true);
					PretreePanel.this.trainFromDBButton.setEnabled(true);
					PretreePanel.this.trainPruneButton.setEnabled(true);
					PretreePanel.this.trainSaveTreeButton.setEnabled(true);
					// buttons enable true finished
					// System.out.println("training done.");
					PretreePanel.this.testTreeLoadedLabel
							.setText("Trained Tree active");
					if (ignoreCase) {
						PretreePanel.this.testIsIgnoreCaseLabel
								.setText("Ignore Case: yes");
					} else {
						PretreePanel.this.testIsIgnoreCaseLabel
								.setText("Ignore Case: no ");
					}
					if (reverse) {
						PretreePanel.this.testIsReverseLabel
								.setText("Reverse: yes");
					} else {
						PretreePanel.this.testIsReverseLabel
								.setText("Reverse: no ");
					}
					PretreePanel.this.testNrClassesLabel.setText("Classes: "
							+ PretreePanel.this.pretree.getNrOfClasses());
					PretreePanel.this.testNrNodesLabel.setText("Nodes: "
							+ PretreePanel.this.pretree.getNrOfNodes());
					PretreePanel.this.trainStatusLabel.setText("tree trained");
					PretreePanel.this.testStatusLabel.setText("tree trained");
					if (PretreePanel.this.testClassifiedVectorNameTable != null) {
						PretreePanel.this.testClassifiedVectorNameTable = new VectorNameTable();
						// testClassifiedJTable =
						// nameTable2jTable(testClassifiedNameTable);
						PretreePanel.this.testClassifiedCT
								.addData(PretreePanel.this.testClassifiedVectorNameTable);
						PretreePanel.this.testWordScrollPane.getViewport().add(
								PretreePanel.this.testClassifiedJTable, null);
					}
					parent.repaint();
				}
			};
			t.start();
			// buttons enable false
			this.trainSetTable.setEnabled(false);
			this.trainSetCT.setEnabled(false);
			this.trainingAddWordField.setEnabled(false);
			this.trainingAddClassField.setEnabled(false);
			this.trainReverseCheckBox.setEnabled(false);
			this.trainICCheckbox.setEnabled(false);
			this.trainClearTrainButton.setEnabled(false);
			this.trainAddFileButton.setEnabled(false);
			this.trainSaveFileButton.setEnabled(false);
			this.trainingAddLineButton.setEnabled(false);
			this.trainingSetDeleteButton.setEnabled(false);
			this.trainButton.setEnabled(false);
			this.trainFromFileButton.setEnabled(false);
			this.trainFromDBButton.setEnabled(false);
			this.trainPruneButton.setEnabled(false);
			this.trainSaveTreeButton.setEnabled(false);
			// buttons enable false finished
		}

		// JFileChooser loadChooser;
		// if( _aktDir!=null ){
		// loadChooser = new JFileChooser(_aktDir);
		// }else{
		// loadChooser = new JFileChooser();
		// }
		// if( loadChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
		// ){
		// File file = loadChooser.getSelectedFile();
		// String filename=file.getPath();
		//
		// NameTable loader=new NameTable();
		// try{
		// loader.loadFile(filename);
		// }catch(Exception exc){
		// exc.printStackTrace();
		// }
		// /*
		// trainSetNameTable.insert(loader);
		// trainSetTable=nameTable2jTable(trainSetNameTable);
		// trainSetScrollPane.getViewport().add(trainSetTable, null);
		// this.setVisible(true);
		// */
		//
		// String aktword="";
		// String wclass="";
		// boolean reverse=trainReverseCheckBox.isSelected();
		// boolean ignoreCase=trainICCheckbox.isSelected();
		// pretree=new Pretree();
		// pretree.setIgnoreCase(ignoreCase);
		// pretree.setReverse(reverse);
		//
		// int progress=0;
		// int percentage=0;
		// int maxprogress=loader.size();
		// int progresssteps=(int)((double)maxprogress/100.0);
		// if (progresssteps==0) progresssteps=1;
		//
		// trainProgressBar.setValue(0);
		// trainProgressBar.setStringPainted(true);
		//
		// for(Enumeration f=loader.keys();f.hasMoreElements();) {
		// aktword=(String)f.nextElement();
		// wclass=(String)loader.get(aktword);
		// pretree.train(aktword,wclass);
		// //if (d) System.out.println("training: "+aktword+" -> "+wclass);
		// progress++;
		// if ((progress % progresssteps)==0) { // set progress bar
		// percentage= (int)((double)(progress)*100/(double)(maxprogress));
		// trainProgressBar.setValue(percentage);
		// // System.out.println(percentage+"% done");
		// }
		// } // rof Enum e
		// // System.out.println("training done.");
		// testTreeLoadedLabel.setText("Trained Tree active");
		// if( ignoreCase ){
		// testIsIgnoreCaseLabel.setText("Ignore Case: yes");
		// }else{
		// testIsIgnoreCaseLabel.setText("Ignore Case: no ");
		// }
		// if( reverse ){
		// testIsReverseLabel.setText("Reverse: yes");
		// }else{
		// testIsReverseLabel.setText("Reverse: no ");
		// }
		// testNrClassesLabel.setText("Classes: "+pretree.getNrOfClasses());
		// testNrNodesLabel.setText("Nodes: "+pretree.getNrOfNodes());
		// trainStatusLabel.setText("tree trained");
		// testStatusLabel.setText("tree trained");
		// if( testClassifiedNameTable != null ){
		// testClassifiedNameTable = new NameTable();
		// testClassifiedJTable=nameTable2jTable(testClassifiedNameTable);
		// testWordScrollPane.getViewport().add(testClassifiedJTable, null);
		// }
		// this.repaint();
		// }
	}// end trainFromFile

	public static void main(String[] args) {

		System.out.println("Noch nüscht.");

	}// end main

} // end class PendelPanel
