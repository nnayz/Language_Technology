package de.uni_leipzig.asv.toolbox.toolboxGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * Wortschatz Tool - eine Sammlung, computerlinguistischer Werkzeuge
 * 
 * @author Stefan Bordag, Chris Biemann and a lot of students
 * @version 0.8
 */

public class Toolbox extends JPanel {
	// private static final String[] modules = {
	// "de.uni_leipzig.asv.toolbox.levenshtein.LevenshteinModul",
	// //"de.uni_leipzig.asv.toolbox.jlani.JLanI",
	// "de.uni_leipzig.asv.toolbox.baseforms.Baseforms",
	//
	// "de.uni_leipzig.asv.toolbox.te.TEPanel",
	// "de.uni_leipzig.asv.toolbox.pretree.PretreePanel",
	// "de.uni_leipzig.asv.toolbox.asvPosTagger.wortschatzPanel",
	//
	// "de.uni_leipzig.asv.toolbox.pendel.PendelPanel",
	// //"de.uni_leipzig.asv.toolbox.ChineseWhispers.WTool",
	//
	// "de.uni_leipzig.asv.toolbox.namerec.gui.RecognizerPanel",
	//
	// //"de.uni_leipzig.asv.toolbox.wtool.WTool"
	// };

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String[] modules;

	private java.util.List<ToolboxModule> modulesVector = null;

	private static JFrame frame = null;

	private JWindow splashScreen = null;

	private ToggleButtonToolBar mainToolBar = null;

	private ButtonGroup chooserButtonGroup = null;

	private JPanel modulePanel = null;

	protected final static Dimension preferredSize = new Dimension(808, 627);

	protected final static Dimension moduleSize = new Dimension(800, 550);

	protected final static Rectangle modulePanelBounds = new Rectangle(0, 50,
			800, 550);

	protected final static Rectangle toolBarBounds = new Rectangle(0, 0, 800,
			50);

	Frame parentFrame = null;

	public Toolbox(Frame parentFrame) {

		this.parentFrame = parentFrame;
		createSplashScreen();
	
		SwingUtilities.invokeLater(new Runnable() {
			//@SuppressWarnings("deprecation")
			public void run() {
				try{
					Toolbox.this.splashScreen.setVisible(true);
				}catch(Exception e){
					//do nothing 'cause Exception is thrown because swing is not thread save
				}
			}
		});
		
		initProgram();
		preloadFirstModule();

		// Show the demo and take down the splash screen. Note that
		// we again must do this on the GUI thread using invokeLater.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try{
					showMainProgram();
					hideSplash();
				}catch(Exception e){
					
				}
			}
		});

		// Start loading the rest of the demo in the background
		ModuleLoaderThread moduleLoader = new ModuleLoaderThread(this);
		moduleLoader.start();

	}

	/**
	 * Show the splash screen while the rest of the program loads
	 */
	public void createSplashScreen() {
		JLabel splashLabel = new JLabel(createImageIcon("Splash3.jpg"));

		this.splashScreen = new JWindow();
		this.splashScreen.getContentPane().add(splashLabel);
		this.splashScreen.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.splashScreen.setLocation(screenSize.width / 2
				- this.splashScreen.getSize().width / 2, screenSize.height / 2
				- this.splashScreen.getSize().height / 2);
	}

	/**
	 * destroy splash screen
	 */
	public void hideSplash() {
		this.splashScreen.setVisible(false);
		this.splashScreen = null;
	}

	/**
	 * Creates an icon from an image
	 */
	public ImageIcon createImageIcon(String filename) {
		return new ImageIcon(filename);
	}

	/**
	 * Initializes components of the main program
	 */
	private void initProgram() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"./config/toolbox.start")));

			String tmp = "";
			Vector<String> tmpV = new Vector<String>();

			while ((tmp = br.readLine()) != null) {
				tmpV.add(tmp);
			}

			if (!tmpV.isEmpty()) {
				int l = tmpV.size();
				Toolbox.modules = new String[l];

				for (int i = 0; i < l; i++) {
					Toolbox.modules[i] = tmpV.get(i);
				}
			}

			tmpV = null;
			tmp = null;
			br.close();
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}

		this.modulesVector = new Vector<ToolboxModule>();

		this.mainToolBar = new ToggleButtonToolBar();
		this.chooserButtonGroup = new ButtonGroup();

		this.modulePanel = new JPanel();
		this.modulePanel.setBounds(Toolbox.modulePanelBounds);
		this.modulePanel.setPreferredSize(Toolbox.moduleSize);

		this.modulePanel.setLayout(null);
		this.modulePanel.setBackground(Color.WHITE);

		Toolbox.frame.getContentPane().setLayout(null);
		this.mainToolBar.setToolTipText("Choose the module you wish to use");
		this.mainToolBar.setBounds(Toolbox.toolBarBounds);
		Toolbox.frame.getContentPane().add(this.mainToolBar);
		Toolbox.frame.getContentPane().add(this.modulePanel);
	}

	/**
	 * Loads first module
	 */
	protected void preloadFirstModule() {
		// WortschatzModul modul = addModule(new
		// de.uni_leipzig.asv.bordag.klf.Klassifikator(this));
		ToolboxModule modul = addModule(new de.uni_leipzig.asv.toolbox.welcomePanel.WelcomePanel(
				this));

		setModule(modul);
	}

	private void loadModules() {
		try {
			for (String element : Toolbox.modules) {
				loadModule(element);
			}
		} catch (Exception e) {

		}
	}

	public Frame getParentFrame() {
		return Toolbox.frame;
	}

	/**
	 * Loads a demo from a classname
	 */
	void loadModule(String classname) {
		// setStatus(getString("Status.loading") + getString(classname +
		// ".name"));
		ToolboxModule module = null;
		try {
			Class moduleClass = Class.forName(classname);
			Constructor moduleConstructor = moduleClass
					.getConstructor(new Class[] { Toolbox.class });
			module = (ToolboxModule) moduleConstructor
					.newInstance(new Object[] { this });
			addModule(module);
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println("Error occurred loading module: " + classname);
		}
	}

	/**
	 * Add a module to the toolbar
	 */
	public ToolboxModule addModule(ToolboxModule module) {
		this.modulesVector.add(module);
		// do the following on the gui thread
		SwingUtilities.invokeLater(new WortschatzRunnable(this, module) {
			@Override
			public void run() {
				SwitchToModuleAction action = new SwitchToModuleAction(
						this.wTool, (ToolboxModule) this.obj);
				JToggleButton tb = this.wTool.mainToolBar
						.addToggleButton(action);
				this.wTool.chooserButtonGroup.add(tb);
				if (this.wTool.chooserButtonGroup.getSelection() == null) {
					tb.setSelected(true);
				}
				tb.setText(null);
				tb.setToolTipText(((ToolboxModule) this.obj).getToolTip());
			}
		});
		return module;
	}

	/**
	 * Sets the current module
	 */
	public void setModule(ToolboxModule module) {
		this.modulePanel.removeAll();
		JPanel mod = module.getModulePanel();
		mod.setSize(Toolbox.moduleSize);
		this.modulePanel.add(mod, null);
		module.activated();
	}

	/**
	 * Bring up the SwingSet2 demo by showing the frame (only applicable if
	 * coming up as an application, not an applet);
	 */
	//@SuppressWarnings("deprecation")
	public void showMainProgram() {
		Toolbox.frame.setTitle("ASV Toolbox 2007 V1.0");
		Toolbox.frame.getContentPane().add(this, BorderLayout.CENTER);
		Toolbox.frame.setSize(Toolbox.preferredSize);
		Toolbox.frame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Toolbox.frame.setSize(Toolbox.preferredSize);
		Toolbox.frame.setLocation(screenSize.width / 2
				- Toolbox.frame.getSize().width / 2, screenSize.height / 2
				- Toolbox.frame.getSize().height / 2);
		Toolbox.frame.setResizable(false);
		Toolbox.frame.setVisible(true);
	}

	/**
	 * Create a frame for SwingSet2 to reside in if brought up as an
	 * application.
	 */
	public static JFrame createFrame() {
		JFrame frame = new JFrame();
		frame.setSize(Toolbox.preferredSize);
		WindowListener l = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(l);
		return frame;
	}

	public static void main(String[] args) {

		Toolbox.frame = Toolbox.createFrame();
		new Toolbox(Toolbox.frame);
	}

	// ------------------------- helper classes --------------------

	/**
	 * Loads modules in a thread
	 */
	class ModuleLoaderThread extends Thread {
		Toolbox program;

		public ModuleLoaderThread(Toolbox wTool) {
			this.program = wTool;
		}

		@Override
		public void run() {
			try{
				this.program.loadModules();
			}catch(Exception e){
				
			}
		}
	}

	/**
	 * This Action switches modules of the main program
	 */
	public class SwitchToModuleAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Toolbox wTool;

		ToolboxModule module;

		public SwitchToModuleAction(Toolbox wTool, ToolboxModule module) {
			super(module.getName(), module.getIcon());
			this.wTool = wTool;
			this.module = module;
		}

		public void actionPerformed(ActionEvent e) {
			this.wTool.setModule(this.module);
		}
	}

	/**
	 */
	class WortschatzRunnable implements Runnable {
		protected Toolbox wTool;

		protected Object obj;

		public WortschatzRunnable(Toolbox wTool, Object obj) {
			this.wTool = wTool;
			this.obj = obj;
		}

		public void run() {
		}
	}

	protected class ToggleButtonToolBar extends JToolBar {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Insets zeroInsets = new Insets(1, 1, 1, 1);

		public ToggleButtonToolBar() {
			super();
		}

		JToggleButton addToggleButton(Action a) {
			JToggleButton tb = new JToggleButton((String) a
					.getValue(Action.NAME), (Icon) a
					.getValue(Action.SMALL_ICON));
			tb.setMargin(this.zeroInsets);
			tb.setText(null);
			tb.setEnabled(a.isEnabled());
			tb.setToolTipText((String) a.getValue(Action.SHORT_DESCRIPTION));
			tb.setAction(a);
			add(tb);
			return tb;
		}
	}
}
