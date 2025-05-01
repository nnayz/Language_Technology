package de.uni_leipzig.asv.toolbox.toolboxGui;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Title: Automatische Sachgebietszuordnung Description: Copyright: Copyright
 * (c) 2001 Company: n/a
 * 
 * @author Stefan Bordag
 * @version 1.0
 */

abstract public class ToolboxModule extends JPanel {

	protected final static Dimension moduleSize = new Dimension(800, 550);

	protected final static Rectangle moduleBounds = new Rectangle(0, 00, 800,
			550);

	private Toolbox wTool = null;

	public ToolboxModule() {
	}

	public ToolboxModule(Toolbox wTool) {
		setLayout(null);

		this.wTool = wTool;
	}

	public abstract void activated();

	public abstract JPanel getModulePanel();

	public abstract char getMnemonic();

	@Override
	public abstract String getName();

	public abstract Icon getIcon();

	public abstract String getToolTip();

	public Toolbox getWTool() {
		return this.wTool;
	}

	public ImageIcon createImageIcon(String filename) {
		if (this.wTool != null) {
			return this.wTool.createImageIcon(filename);
		}
		return null;
	}

}
