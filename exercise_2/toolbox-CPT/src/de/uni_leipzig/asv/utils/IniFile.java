package de.uni_leipzig.asv.utils;

// standard imports
import java.io.File;
import java.util.Hashtable;

/**
 * An IniFile is a cache which reads some specified file of the given format.
 * The values of the file can then be directly accessed at any time, as well as
 * changed. Changes will immediately be written into the file. This class is not
 * intended for massive writing, as each change requires rewriting of the whole
 * file.<br>
 * <br> # Format of Inifiles should look like this:<br>
 * <br> # comment<br>
 * <br>
 * [PRIMARY_KEY]<br>
 * SECONDARY_KEY_1=value<br>
 * SECONDARY_KEY_2=value<br>
 * <br>
 * 
 * @author Stefan Bordag
 * @date 28.12.2001
 * @see de.uni_leipzig.asv.bordag.sgz.util.IniReader
 * @see de.uni_leipzig.asv.bordag.sgz.util.IniWriter
 */
public class IniFile {
	/**
	 * Constants defining the format of the file
	 */
	public static final String PKEY_LEFT_BRACKET = "[";

	public static final String PKEY_RIGHT_BRACKET = "]";

	public static final String ASSIGNMENT = "=";

	public static final String COMMENT = "#";

	/**
	 * A double Hashtable containing the structure and values of the file
	 */
	protected Hashtable<String, Hashtable<String, String>> entries = null;

	/**
	 * The file which was used to read the information and which is then used to
	 * write changes
	 */
	protected String file = null;

	/**
	 * Default constructor, don't use it, as instances without a filename are
	 * senseless
	 */
	public IniFile() {
		init(null);
	}

	/**
	 * Convenience constructor
	 */
	public IniFile(File file) {
		init(file.getAbsolutePath());
	}

	/**
	 * Creates an instance of the IniReader and reads the file, thus cashing the
	 * data
	 */
	public IniFile(String fileName) {
		init(fileName);
	}

	/**
	 * Initializes the reader
	 */
	private void init(String fileName) {
		this.file = fileName;
		IniReader reader = null;
		try {
			reader = new IniReader(fileName);
			this.entries = reader.getPrimaryKeys();
		} catch (Exception ignore) {
			this.entries = new Hashtable<String, Hashtable<String, String>>();
		}
	}

	/**
	 * Returns a Hashtable containing all the entries belonging to the given
	 * primary key
	 */
	public Hashtable getPrimaryKey(String key) {
		return this.entries.get(key);
	}

	/**
	 * Returns the required value of the given primary key
	 */
	public String getValue(String primaryKey, String secondaryKey) {
		return this.entries.get(primaryKey).get(secondaryKey);
	}

	/**
	 * Sets the values of the entire given primary key section to the given
	 * hashtable of values
	 */
	public void setValues(String primaryKey, Hashtable<String, String> hash) {
		if ((primaryKey == null) || (hash == null) || (primaryKey.length() < 1)
				|| (hash.size() < 1)) {
			return;
		}
		this.entries.put(primaryKey, hash);
		new IniWriter(this);
	}

	/**
	 * Sets or Adds a given value to a given keypair
	 */
	@SuppressWarnings("unchecked")
	public void setValue(String primaryKey, String secondaryKey, String value) {
		if ((primaryKey == null) || (secondaryKey == null) || (value == null)
				|| (primaryKey.length() < 1) || (secondaryKey.length() < 1)) {
			return;
		}
		Object secVal = this.entries.get(primaryKey);
		Hashtable<String, String> tempHash = null;
		if (secVal == null) {
			tempHash = new Hashtable<String, String>();
		} else {
			tempHash = (Hashtable<String, String>) secVal;
		}
		tempHash.put(secondaryKey, value);
		this.entries.put(primaryKey, tempHash);
		new IniWriter(this);
	}

	/**
	 * Tests whtether this inifile contains a value with the given Keypair
	 */
	public boolean existsKeyPair(String primKey, String secKey) {
		Hashtable val = this.entries.get(primKey);
		if (val != null) {
			if (val.containsKey(secKey)) {
				return true;
			}
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
	 * Returns the name of the file which is associated with this instance of
	 * IniFile
	 */
	public String getFileName() {
		return this.file;
	}

	/**
	 * Prints out a representation of this class
	 */
	@Override
	public String toString() {
		return "IniFile with filename " + this.file;
	}
}