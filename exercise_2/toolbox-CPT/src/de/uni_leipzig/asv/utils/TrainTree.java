/** 
 * @title Prefixkompression - Demo fuer Training und Test von Prefixbaumklassifikatoren 
 * @author Christian Biemann 
 * @version 12.04.2003
 *
 *  
 *  Beide Files haben Struktur WORT1 <tab> KLASSE1 <CRLF> WORT2 ...

 */

package de.uni_leipzig.asv.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

public class TrainTree {

	static String w = new String();

	static Pretree baum = new Pretree();

	static boolean d = false; // Debugging AUS

	static double thresh = 0.46;

	static boolean rev = false;

	static boolean disp = true;

	static StringTokenizer stc;

	public static void main(String args[]) throws FileNotFoundException,
			IOException {

		if (args.length != 4) {
			System.out
					.println("falsche Parameter\n Trainingsfile, reverse(r/n), ignorecase(+/-), ZielTreefile");
			System.exit(0);
		}
		String inputfile = args[0];
		if (args[1].equals("r")) {
			rev = true;
		} else {
			rev = false;
		}
		if (args[2].equals("+")) {
			baum.setIgnoreCase(true);
		} else {
			baum.setIgnoreCase(false);
		}
		String zielfile = args[3];

		baum.setReverse(rev);

		FileInputStream instr = new FileInputStream(inputfile);

		int r;
		String inword = new String();
		String inclass = new String();

		// Einlesen von "input.txt" und Aufbau des Baums

		try {
			while ((r = instr.read()) != -1) { // durchl. File bis EOF
				while ((r != 9) && (r != -1)) { // Parsen des Wortes,
												// Konvertierung Uppercase und
												// Umlaute,

					if (r != 46) {
						inword += (char) (r);
					}
					if (d) {
						System.out.print((char) r + ":" + r + "  ");
					}
					r = instr.read();
				} // end if weiterlesen bis Tab
				r = instr.read();
				while ((r != 10) && (r != 13) && (r != -1)) { // Parsen des
																// Wortes,
																// Konvertierung
																// Uppercase und
																// Umlaute,

					inclass += (char) r; // ß
					if (d) {
						System.out.print((char) r + ":" + r + "  ");
					}
					r = instr.read();
				} // elihw weiterlesen bis CR LF
				// if ((r==10)||(r==13)) {r=instr.read();}

				// Aktion
				if (d) {
					System.out.println("Read: " + inword + "\t" + inclass);
				}

				stc = new StringTokenizer(inclass, ",");
				while (stc.hasMoreTokens()) {
					baum.train(inword, stc.nextToken()); // in Baum
															// einhängen
				}
				if (d) {
					baum.ttyout();
				}
				inword = ""; // und Wort resetten
				inclass = "";

			}// wend r=..
			if (d) {
				baum.ttyout();
			}
		} catch (IOException e) {
			System.out.println("Can't open " + inputfile);
		} // ende Aufbau

		// Hier Pruning
		System.out.println("Now Pruning...");
		baum.prune();

		// Hier Speichern und laden

		System.out.println("Now Saving ...");
		baum.save(zielfile);

		// Anzeige
		System.out.println("Pretree in " + zielfile);
		System.out.println(" reverse=" + baum.getReverse());
		System.out.println(" ignoreCase=" + baum.getIgnoreCase());
		System.out.println(" classification Threshold:=" + baum.getThresh());

	} // main

} // end class TrainTree

