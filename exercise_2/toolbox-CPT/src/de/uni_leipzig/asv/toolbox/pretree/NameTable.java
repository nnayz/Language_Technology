package de.uni_leipzig.asv.toolbox.pretree;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;

import de.uni_leipzig.asv.util.commonDB.CommonDB;
import de.uni_leipzig.asv.util.commonDB.DBConnection;

/* Class NameTable
 Author: Christian Biemann, 01/002


 Implementiert File-IO für Hashtables.

 Das File-Format sieht so aus:

 Key1 TAB Val1
 Key2 TAB Val2
 ...


 Funktionen:

 Nametable NameTable.loadFile(filename): Fügt neues File in die Tabelle ein. Alte Elemente bleiben bestehen!

 void NameTable.saveFile(filename): Schreibt Tabelle in File.
 void NameTabelle.appendFile(filename): Hängt Tabelle an File an.



 */

public class NameTable extends java.util.Hashtable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DBConnection dbcon;

	public void writeFile(String filename, boolean append) throws IOException {
		FileWriter file = new FileWriter(filename, append);
		String outstr = new String();
		char outChar;

		outstr = toString(); // wandle um in "{Key1=Val1, Key2=Val2,
		// ...}"
		try {
			for (int pos = 0; pos < outstr.length(); pos++) {
				outChar = outstr.charAt(pos);
				if (outChar == '{') {
					continue;
				}
				if (outChar == '}') {
					continue;
				}
				if (outChar == ' ') {
					continue;
				}
				if (outChar == ',') {
					file.write(13);
					file.write(10);
					continue;
				} // Neue Zeile
				if (outChar == '=') {
					file.write(9);
					continue;
				} // Tabulator

				file.write(outChar);
			} // rof
			file.write(13);
			file.write(10); // Endet mit CR&LF
		} catch (IOException e) {
			System.out.println("Can.t write " + filename);
		} finally {
			file.close();
		}

	} // end writeFile (für saveFile und appendFile)

	public void appendFile(String filename) throws IOException {
		try {
			writeFile(filename, true);
		} catch (IOException e) {
			System.out.println("Can.t write " + filename);
		}
	}

	public void saveFile(String filename) throws IOException {
		try {
			writeFile(filename, false);
		} catch (IOException e) {
			System.out.println("Can.t write " + filename);
		}
	}

	public NameTable loadFile(String filename) throws IOException {

		FileReader file = new FileReader(filename);
		int inInt;
		String inkey = "", inval = ""; // Strings für Key und Value

		try {
			while ((inInt = file.read()) != -1) { // lese bis EOF
				inkey = "";
				inval = "";
				while ((inInt != 9) && (inInt != -1)) { // Lese KEY
					if ((inInt != 10) && (inInt != 13)) {
						inkey += (char) inInt;
					}
					inInt = file.read();
				} // elihw inInt<>9
				inInt = file.read(); // Lese nächstes (Überspringe TAB)
				while ((inInt != 10) && (inInt != 13) && (inInt != -1)) { // Lese
					// Value
					inval += (char) inInt;
					inInt = file.read();
				} // elihw inInt<>13
				if (inInt == 13) {
					inInt = file.read();
				} // Überspringe LR
				put(inkey, inval); // Füge in NameTable ein

				// System.out.println("Inputting: "+inkey+"\t"+inval);
			} // elihw EOF

		} catch (IOException e) {
			System.out.println("Can't find file " + filename + "\n");
		}
		return this;
	} // end loadFile

	public NameTable loadDB(DBConnection dbcon, String queryfile) {

		this.dbcon = dbcon;

		CommonDB cdb = new CommonDB(this.dbcon, queryfile, false);

		int pos_key = 0, pos_val = 0;

		ResultSet rs = cdb.executeQueryWithResults_ResultSetCatched(cdb
				.getQuery("pretree.query_getTable"), null);
		String primkey = cdb.getPrimaryKeyNameCatched(cdb
				.getQuery("pretree.tableName_getTable"));
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int cc = rsmd.getColumnCount();
			for (int i = 1; i <= cc; i++) {
				if (!rsmd.getColumnName(i).equals(primkey)) {
					if (pos_key == 0) {
						pos_key = i;
					} else if ((pos_key != 0) && (pos_val == 0)) {
						pos_val = i;
					}
				}
			}

			while (rs.next()) {
				put(rs.getString(pos_key), rs.getString(pos_val));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {

		if (super.containsKey(key)) {
			Object prev = super.get(key);
			super.remove(key);
			super.put(key, value);
			return prev;
		} else {
			return super.put(key, value);
		}
	}

	public void insert(NameTable toInsert) {
		String item;

		for (Enumeration e = toInsert.keys(); e.hasMoreElements();) {
			item = (String) e.nextElement();
			put(item, toInsert.get(item));
		} // rof Enumeration toInsert

	} // end insert

} // end NameTable
