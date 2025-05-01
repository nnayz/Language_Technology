/** 
 * @title Prefixkompression - Demo fuer Training und Test von Prefixbaumklassifikatoren 
 * @author Christian Biemann 
 * @version 12.04.2003
 *
 * Aufruf: java TestTree classify.tree testfile thresh
 * 
 *  Testfile hat Struktur WORT1 <tab> KLASSE1 <CRLF> WORT2 ...


 */
package de.uni_leipzig.asv.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestTree {

	static String w = new String();

	static Pretree baum = new Pretree();

	static boolean d = false; // Debugging AUS

	static boolean disp = true;

	public static void main(String args[]) throws FileNotFoundException,
			IOException {

		if (args.length != 2) {
			System.out.println("falsche Parameter!\n Treefile Testfile");
			System.exit(1);
		}
		String inputtree = args[0];
		String searchfile = args[1];
		int r;
		String inword = new String();
		String inclass = new String();

		// Hier laden

		// System.out.println("Now loading ...");
		baum = new Pretree();
		baum.load(inputtree);

		// Anzeige
		// baum.ttyout();

		// System.out.println("now classifying ...");
		int sum = 0;
		int right = 0;
		int wrong = 0;
		int undecided = 0;

		// /////////////////////Suchen aus
		// TxtFile**********************************************************

		FileInputStream instr = new FileInputStream(searchfile);

		sum = 0;
		right = 0;
		wrong = 0;
		undecided = 0;
		try {
			while ((r = instr.read()) != -1) { // durchl. File bis EOF
				while ((r != 9) && (r != -1)) { // Parsen des Wortes,
												// Konvertierung Uppercase und
												// Umlaute,

					if (r != 46) {
						inword += (char) (r); // ß
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
				String kfiziert = baum.classify(inword);
				// if (disp)
				// System.out.print(inword+"\t"+inclass+"\t"+kfiziert)inword+"\t"+inclass+"\t"+kfiziert);
				if (kfiziert.equals(inclass)) {
					right++;
				} else {
					if (kfiziert.equals("undecided")) {
						undecided++;
						if (disp) {
							System.out.println(inword + "\t" + inclass + "\t"
									+ kfiziert + "\tUN");
							String grund = baum.giveReason(inword);
							System.out.println(grund);

						}
					} else {
						wrong++;
						if (disp) {
							System.out.println(inword + "\t" + inclass + "\t"
									+ kfiziert + "\tFALSCH");
							String grund = baum.giveReason(inword);
							System.out.println(grund);
						}
					}
				}
				sum++;

				// System.gc();
				if (d) {
					baum.ttyout();
				}
				inword = ""; // und Wort resetten
				inclass = "";

			}// wend r=..

			double prec = (double) right / (double) (right + wrong);
			double rec = (double) right / (double) (right + wrong + undecided);

			System.out.println(" P: " + prec + " R: " + rec + " F: "
					+ (2 * prec * rec) / (prec + rec) + " bei thresh="
					+ baum.getThresh());

		} catch (IOException e) {
			System.out.println("Can't open " + searchfile);
		} // ende Test

	} // main

} // end class TestTree

