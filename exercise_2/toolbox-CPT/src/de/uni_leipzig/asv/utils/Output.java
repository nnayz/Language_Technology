package de.uni_leipzig.asv.utils;

// standard imports
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * This class knows what the settings are and performs output operations for the
 * whole program.
 * 
 * @author Stefan Bordag
 * @date 02.04.2002
 */
public class Output {

	/**
	 * No instances of this class.
	 */
	private Output() {
	}

	/**
	 * Prints a newline
	 */
	public static void println() {
		Output.println("");
	}

	/**
	 * Wrapper of the standard print method
	 */
	public static void print(String string) {
		String output = Options.getInstance().getGenOutputFile();
		if (!output.equalsIgnoreCase("stdout")) {
			try {
				PrintStream out = new PrintStream(new FileOutputStream(output,
						true));
				out.print(string);
				out.close();
			} catch (Exception ex) {
				System.err.println("Couldn't open outputfile " + output
						+ " for appending.");
			}
		}
		System.out.print(string);
	}

	/**
	 * The same a the print method, adds a newline
	 */
	public static void println(String string) {
		Output.print(string + "\n");
	}
}
