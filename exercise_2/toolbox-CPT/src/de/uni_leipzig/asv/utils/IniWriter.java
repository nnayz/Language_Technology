package de.uni_leipzig.asv.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * An IniWriter is created with a reference to a Reader. At any time the Reader
 * can then write down the changes.<br>
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
 * @see de.uni_leipzig.asv.bordag.sgz.util.IniReader
 */
public class IniWriter extends Thread {

	/**
	 * A double Hashtable containing the structure of the file
	 */
	protected Hashtable<String, Hashtable<String, String>> entries = null;

	/**
	 * The filename which was used to read the information
	 */
	protected String file = null;

	/**
	 * This is the Writer we write the data into
	 */
	protected BufferedWriter writer = null;

	/**
	 * The lines in the file previously
	 */
	protected Vector<String> fileLines = null;

	/**
	 * Default constructor hidden to prevent useless instances
	 */
	private IniWriter() {
	}

	/**
	 * Creates an instance of the IniReader and reads the file, thus cashing the
	 * data
	 */
	@SuppressWarnings("unchecked")
	public IniWriter(IniFile reader) {
		init(reader.getFileName());
		synchronized (this) {
			this.entries = (Hashtable<String, Hashtable<String, String>>) reader
					.getPrimaryKeys().clone();
		}
		start();
	}

	/**
	 * Turn by turn write the lines into the file
	 */
	@Override
	public void run() {
		if (this.writer == null) {
			return;
		}
		Enumeration<String> enums = this.entries.keys();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement();
			try {
				this.writer.write(IniFile.PKEY_LEFT_BRACKET + key
						+ IniFile.PKEY_RIGHT_BRACKET);
				this.writer.newLine();
				Hashtable tempHash = this.entries.get(key);
				for (Enumeration hashEnum = tempHash.keys(); hashEnum
						.hasMoreElements();) {
					String hashKey = (String) hashEnum.nextElement();
					this.writer
							.write(hashKey
									+ IniFile.ASSIGNMENT
									+ convertNewLines(tempHash.get(hashKey)
											.toString()));
					this.writer.newLine();
				}
				this.writer.newLine();
			} catch (Exception ex) {
				Debugger.getInstance().println(
						"IniWriter: Couldn't write " + key + " into "
								+ this.file, Debugger.MED_LEVEL);
			}
		}

		try {
			this.writer.flush();
			this.writer.close();
			Debugger.getInstance().println(
					"File " + this.file + " successfully closed",
					Debugger.MAX_LEVEL);
		} catch (Exception ex) {
			Debugger.getInstance().println(
					"File " + this.file + " could not be closed!",
					Debugger.MED_LEVEL);
		}
	}

	private String convertNewLines(String string) {
		return string.replace('\n', '~');
	}

	/**
	 * Initializes the reader
	 */
	private void init(String fileName) {
		this.file = fileName;
		try {
			this.entries = new Hashtable<String, Hashtable<String, String>>();

			// first we try to read the file so that we can keep comments and
			// structure when writing it again
			try {
				BufferedReader reader = null;
				if (fileName != null) {
					reader = new BufferedReader(new FileReader(fileName));
				}

				this.fileLines = getValidLines(reader);
			} catch (Exception ex) // if it didn't work, we assume it wasn't
			// there before
			{
				this.fileLines = new Vector<String>();
			}

			// Then I open it for writing and that's it.
			if (fileName != null) {
				this.writer = new BufferedWriter(new FileWriter(fileName));
			}
		} catch (Exception ignore) {
			Debugger.getInstance().println(
					"Wasn't able to open ini file for writing: "
							+ ignore.getLocalizedMessage(), Debugger.MED_LEVEL);
		}
	}

	/**
	 * Returns an ordered list containing only valid IniReader-valid lines, i.e.
	 * containing either at least one char
	 */
	private Vector<String> getValidLines(BufferedReader reader)
			throws IOException {
		Vector<String> fileLines = new Vector<String>();
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
	 * Returns a printrepresentation of this instance
	 */
	@Override
	public String toString() {
		return "FileWriter with filename " + this.file;
	}
}
