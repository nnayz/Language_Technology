package de.uni_leipzig.asv.utils;

import java.io.File;
import java.io.IOException;

/* from http://www.javaworld.com/javaworld/javatips/jw-javatip66.html
 * modified
 * */

/**
 * This is the Browser Control. It try to open the html-help with a system
 * specific browser
 * 
 * @author Daniel Zimmermann, Torsten Thalheim
 */
public class BrowserControl {
	// Used to identify the windows platform.
	private static final String WIN_ID = "Windows";

	// The default system browser under windows.
	private static final String WIN_PATH = "rundll32";

	// The flag to display a url.
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";

	// The default browser under unix.
	private static final String UNIX_PATH = "netscape";

	// Used to identify the windows platform.
	private static final String LINUX_ID = "Linux";

	// The default browser under Linux.
	private static final String LINUX_PATH = "konqueror";

	/**
	 * Display a file in the system browser. If you want to display a file, you
	 * must include the absolute path name.
	 * 
	 * @param url
	 *            the file's url (the url must start with either "http://" or
	 *            "file://").
	 */
	public static void displayURL(String url) {
		boolean windows = BrowserControl.isWindowsPlatform();
		String cmd = null;
		try {
			if (windows) {
				/* cmd = 'rundll32 url.dll,FileProtocolHandler http://...' */
				cmd = BrowserControl.WIN_PATH + " " + BrowserControl.WIN_FLAG
						+ " " + url;
				cmd = cmd.replace('\\', '/');
				Runtime.getRuntime().exec(cmd);
			} else {
				/* cmd = 'netscape file://<abolute_path>/Hilfe/index.htm' */
				if (BrowserControl.isLinuxPlatform()) {
					cmd = BrowserControl.LINUX_PATH + " " + url;
				} else {
					cmd = BrowserControl.UNIX_PATH + " " + url;
				}
				Runtime.getRuntime().exec(cmd);

			}
		} catch (IOException x) {
			// couldn't exec browser
			System.err.println("Could not invoke browser, command=" + cmd);
			System.err.println("please open help manually: " + url);
			System.err.println("Caught: " + x);
			ErrorDialog ed = new ErrorDialog(
					"Could not invoke browser, command=" + cmd,
					"please open help manually: " + url);
			ed.show(ed);
		}
	}

	/**
	 * Try to determine whether this application is running under Windows or
	 * some other platform by examing the "os.name" property.
	 * 
	 * @return true if this application is running under a Windows OS
	 */
	public static boolean isWindowsPlatform() {
		String os = System.getProperty("os.name");
		if ((os != null) && os.startsWith(BrowserControl.WIN_ID)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Try to determine whether this application is running under Linux or some
	 * other platform by examing the "os.name" property.
	 * 
	 * @return true if this application is running under a Linux OS
	 */
	public static boolean isLinuxPlatform() {
		String os = System.getProperty("os.name");
		if ((os != null) && os.startsWith(BrowserControl.LINUX_ID)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * launch the browser, needed to execute this class
	 * 
	 */
	public static void launch_browser(String oHP) { /*
													 * get the current absolute
													 * path
													 */
		if ((oHP.indexOf("www") > -1) || (oHP.indexOf("http") > -1)) {
			BrowserControl.displayURL(oHP);
		} else {
			File file = new File(".");
			String test = file.getAbsolutePath();
			/* the path of the java main class */
			BrowserControl.displayURL("file://"
					+ test.substring(0, test.length() - 1) + oHP);
		}

	}

}
