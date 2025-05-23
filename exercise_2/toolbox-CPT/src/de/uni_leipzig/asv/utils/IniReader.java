package de.uni_leipzig.asv.utils;

// standard imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class reads file for the IniFile class.<br>
 * <br> # Format of Inifiles should look like this:<br>
 * <br> # comment<br>
 * <br>
 * [PRIMARY_KEY]<br>
 * SECONDARY_KEY_1=value<br>
 * SECONDARY_KEY_2=value<br>
 * 
 * @author Stefan Bordag
 * @date 28.12.2001
 * @see de.uni_leipzig.asv.bordag.sgz.util.IniFile
 * @see de.uni_leipzig.asv.bordag.sgz.util.IniWriter
 */
public class IniReader {

	// actually a double Hashtable
	protected Hashtable<String, Hashtable<String, String>> entries = null;

	// the file which was used to read the information
	protected String file = null;

	/**
	 * Default constructor hidden away to prevent creating nonsense instances
	 */
	private IniReader() {
	}

	/**
	 * Convenience constructor
	 */
	public IniReader(File file) throws FileNotFoundException {
		init(file.getAbsolutePath());
	}

	/**
	 * Creates an instance of the IniReader and reads the file, thus cashing the
	 * data
	 */
	public IniReader(String fileName) throws FileNotFoundException {
		init(fileName);
	}

	/**
	 * Initializes the reader
	 */
	private void init(String fileName) throws FileNotFoundException {
		this.file = fileName;
		try {
			this.entries = new Hashtable<String, Hashtable<String, String>>();

			BufferedReader reader = null;
			if (fileName != null) {
				reader = new BufferedReader(new FileReader(fileName));
			}

			Vector<String> fileLines = getValidLines(reader);
			this.entries = fillEntries(this.entries, fileLines);
		} catch (FileNotFoundException fnf) {
			this.entries = new Hashtable<String, Hashtable<String, String>>();
			throw fnf;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns an ordered list containing only valid IniReader-valid lines, i.e.
	 * containing either at least one
	 */
	private Vector<String> getValidLines(BufferedReader reader)
			throws IOException {
		Vector<String> fileLines = new Vector<String>();
		if (reader == null) {
			return fileLines;
		}
		String line = reader.readLine();
		while (line != null) {
			if (valid(line)) {
				fileLines.add(line);
			}
			line = reader.readLine();
		}
		return fileLines;
	}

	/**
	 * Fills the double Hashtable
	 */
	private Hashtable<String, Hashtable<String, String>> fillEntries(
			Hashtable<String, Hashtable<String, String>> table,
			Vector<String> lines) {
		Hashtable<String, String> temp = new Hashtable<String, String>();
		String line = null;
		lines = reverse(lines);

		for (Enumeration<String> e = lines.elements(); e.hasMoreElements();) {
			line = e.nextElement();

			if (isPrimaryKey(line)) {
				if (temp != null) {
					table.put(line.substring(1, line.length() - 1), temp); // done
					// with
					// a
					// primary
					// key
				}
				temp = new Hashtable<String, String>();
			} else if (isSecondaryKey(line)) {
				if (temp == null) {
					Debugger.getInstance().println("malformed ini file.",
							Debugger.MED_LEVEL);
				}
				temp.put(getKey(line), convertNewLines(getValue(line)));
			}
		}
		return table;
	}

	private String convertNewLines(String string) {
		if (string.lastIndexOf("~") > -1) {
			StringTokenizer tokenizer = new StringTokenizer(string, "~");
			String retVal = "";
			int i = 0;
			while (tokenizer.hasMoreTokens()) {
				String curToken = tokenizer.nextToken();
				if (i > 0) {
					retVal = retVal + "\n" + curToken;
				} else {
					retVal = retVal + "" + curToken;
				}
				i++;
			}
			return retVal;
		}
		return string;
	}

	/**
	 * Reverses a Vector
	 */
	private Vector<String> reverse(Vector<String> lines) {
		Vector<String> newLines = new Vector<String>(lines.capacity());
		for (String string : lines) {
			newLines.add(0, string);
		}
		return newLines;
	}

	/**
	 * Returns the String before the ASSIGNMENT string, removing all whitespace
	 * between the last char and the assignment string
	 */
	private String getKey(String line) {
		String retVal = line.substring(0, line.indexOf(IniFile.ASSIGNMENT));
		retVal = retVal.trim();
		return retVal;
	}

	/**
	 * Returns the String after the ASSIGNMENT string, removing all whitespace
	 * between the assignment string and the first non-whitespace char
	 */
	private String getValue(String line) {
		String retVal = line.substring(line.indexOf(IniFile.ASSIGNMENT)
				+ IniFile.ASSIGNMENT.length(), line.length());
		retVal = retVal.trim();
		return retVal;
	}

	/**
	 * Computes whether the given line of a file is valid for IniReader or not
	 */
	private boolean valid(String line) {
		if (line.indexOf(IniFile.COMMENT) == 0) {
			return false; // it's a comment
		}

		if (line.indexOf(IniFile.ASSIGNMENT) != -1) // check whether it as a
		// secondary key entry
		{
			return true; // it's a secondary key entry
		}

		if ((line.indexOf(IniFile.PKEY_LEFT_BRACKET) != -1) && // or a primary
				(// key
				line.indexOf(IniFile.PKEY_RIGHT_BRACKET) != -1)) // but that
																	// one
		// mus thave
		// both brackets
		// present
		{
			return true; // it's a primary key
		}

		return false; // some other line, probably malformed
	}

	/**
	 * Returns whether the line contains a secondary key or not
	 */
	private boolean isSecondaryKey(String line) {
		if ((line.indexOf(IniFile.PKEY_LEFT_BRACKET) != -1) && // or a primary
				(// key
				line.indexOf(IniFile.PKEY_RIGHT_BRACKET) != -1)) // but that
																	// one
		// mus thave
		// both brackets
		// present
		{
			return false; // it's a primary key
		}
		if (line.indexOf(IniFile.ASSIGNMENT) != -1) // check whether it as a
		// secondary key entry
		{
			return true; // it's a secondary key entry
		}
		return false;
	}

	/**
	 * Returns whether the line contains a primary key or not
	 */
	private boolean isPrimaryKey(String line) {
		if ((line.indexOf(IniFile.PKEY_LEFT_BRACKET) != -1) && // or a primary
				(// key
				line.indexOf(IniFile.PKEY_RIGHT_BRACKET) != -1)) // but that
																	// one
		// mus thave
		// both brackets
		// present
		{
			return true; // it's a primary key
		}
		return false;
	}

	/**
	 * Returns the whole Hashtable
	 */
	public Hashtable<String, Hashtable<String, String>> getPrimaryKeys() {
		return this.entries;
	}

	/**
	 * Returns a printrepresentation of this class
	 */
	@Override
	public String toString() {
		return "IniReader with filename " + this.file;
	}
}
