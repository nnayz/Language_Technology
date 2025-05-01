package de.uni_leipzig.asv.toolbox.pretree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import de.uni_leipzig.asv.utils.Pretree;

public class PretreeTool {

	static int _offsetlaenge;

	static int _basis;

	static int _startchar;

	static int _endchar;

	static char _achtungZahl;

	static char _achtungKnoten;

	// defaults
	private static int _defaz = 2;

	private static int _defak = 3;

	private static int _defsc = 33;

	private static int _defec = 248;

	private static boolean _defic = false;

	private static boolean _defrv = false;

	private static double _defth = 0.5;

	private static String _tab = "\t";

	public void train(String mapfile, String treefile, boolean reverse,
			boolean ignorecase) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(mapfile));
			Pretree pretree = new Pretree();
			pretree.setReverse(reverse);
			pretree.setIgnoreCase(ignorecase);
			pretree.setStartChar(PretreeTool._startchar);
			pretree.setEndChar(PretreeTool._endchar);
			pretree.setAchtungZahl(PretreeTool._achtungZahl);
			pretree.setAchtungKnoten(PretreeTool._achtungKnoten);
			String aktZeile;
			String[] aktVals;
			while ((aktZeile = br.readLine()) != null) {
				aktVals = PretreeTool.split(aktZeile, PretreeTool._tab);
				try {
					if (aktVals.length <= 2) {
						pretree.train(aktVals[0], aktVals[1]);
					} else if (aktVals.length >= 3) {
						pretree.train(aktVals[0], aktVals[1], Integer
								.parseInt(aktVals[2]));
					}
				} catch (NumberFormatException nfe) {
					System.out.println("ignoring malformed line: " + aktZeile);
				} catch (IndexOutOfBoundsException aobe) {
					System.out.println("ignoring malformed line: " + aktZeile);
				}
			}
			pretree.save(treefile);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void prune(String treefile_in, String treefile_out, double thresh) {

		try {
			Pretree pretree = new Pretree();
			pretree.load(treefile_in);
			pretree.setThresh(thresh);
			pretree.prune();
			pretree.save(treefile_out);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void train_prune(String mapfile, String treefile, double thresh,
			boolean reverse, boolean ignorecase) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(mapfile));
			Pretree pretree = new Pretree();
			pretree.setReverse(reverse);
			pretree.setIgnoreCase(ignorecase);
			pretree.setStartChar(PretreeTool._startchar);
			pretree.setEndChar(PretreeTool._endchar);
			pretree.setAchtungZahl(PretreeTool._achtungZahl);
			pretree.setAchtungKnoten(PretreeTool._achtungKnoten);
			pretree.setThresh(thresh);
			String aktZeile;
			String[] aktVals;
			while ((aktZeile = br.readLine()) != null) {
				aktVals = PretreeTool.split(aktZeile, PretreeTool._tab);
				try {
					if (aktVals.length <= 2) {
						pretree.train(aktVals[0], aktVals[1]);
					} else if (aktVals.length >= 3) {
						pretree.train(aktVals[0], aktVals[1], Integer
								.parseInt(aktVals[2]));
					}
				} catch (NumberFormatException nfe) {
					System.out.println("ignoring malformed line: " + aktZeile);
				} catch (IndexOutOfBoundsException aobe) {
					System.out.println("ignoring malformed line: " + aktZeile);
				}
			}
			pretree.prune();
			pretree.save(treefile);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void classify(String word, String treefile, double thresh) {

		try {

			Pretree pretree = new Pretree();
			pretree.load(treefile);
			pretree.setThresh(thresh);
			System.out.println(pretree.classify(word));

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void polyclassify(String wordfile, String treefile, double thresh) {

		try {
			BufferedReader words = new BufferedReader(new FileReader(wordfile));
			Pretree pretree = new Pretree();
			pretree.load(treefile);
			pretree.setThresh(thresh);
			String aktWord;
			while ((aktWord = words.readLine()) != null) {

				String aktClass = pretree.classify(aktWord);
				System.out.println(aktWord + "\t" + aktClass);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void convert(String treefile) {

		try {
			Pretree pretree = new Pretree();
			pretree.load(treefile);
			pretree.save(treefile);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void print(String treefile) {

		try {
			Pretree pretree = new Pretree();
			pretree.load(treefile);
			System.out.println("tree: " + treefile);
			System.out
					.println("number of classes: " + pretree.getNrOfClasses());
			System.out.println("number of nodes: " + pretree.getNrOfNodes());
			System.out.println("ignore case: " + pretree.getIgnoreCase());
			System.out.println("reverse: " + pretree.getReverse());
			System.out.println("entries:");
			System.out.println(pretree.getAllEntriesString());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void printHelp() {

		System.out
				.println("usage: pretreetool COMMAND [OPTIONS] WORD|WORDFILE|MAPFILE|TREEFILE_0 TREEFILE_1 ... TREEFILE_N");
		System.out.println("commands:");
		System.out
				.println("\ttrain, t\ttrains a pretree with MAPFILE to TREEFILE_1");
		System.out
				.println("\tprune, p\tprunes a tree from TREEFILE_0 to TREEFILE_1");
		System.out
				.println("\ttrainprune, tp\ttrains a tree from MAPFILE and prunes it to TREEFILE_1");
		System.out
				.println("\tclassify, c\tclassifies WORD with tree from TREEFILE_1");
		System.out
				.println("\tconverts, cv\tconverts trees in TREEFILE_0 to TREEFILE_N into the latest format");
		System.out
				.println("\tprint, pr\tprints the content of the trees in TREEFILE_0 to TREEFILE_N");
		System.out.println("options:");
		System.out
				.println("\t-t=#\t\tsets threshold for pruning and classifying to #, a value between 0 and 1, default "
						+ PretreeTool._defth);
		System.out
				.println("\t-f\t\tonly for classifying: instead of a single word take words from WORDFILE to classify, must appear directly before WORDFILE");
		System.out
				.println("the following options only for train and trainprune:");
		System.out.println("\t-rv\t\treverse tree");
		System.out.println("\t-ic\t\tignore case");
		System.out
				.println("\t-sc=#\t\tcharacter # as first \"number\", default: "
						+ PretreeTool._defsc);
		System.out
				.println("\t-ec=#\t\tcharacter # as last \"number\", default: "
						+ PretreeTool._defec);
		System.out
				.println("\t-az=#\t\tcharacter # as number separator, default: "
						+ PretreeTool._defaz);
		System.out
				.println("\t-ak=#\t\tcharacter # as node separator, default: "
						+ PretreeTool._defak);

	}

	public static void main(String[] args) {

		try {
			PretreeTool._startchar = PretreeTool._defsc;
			PretreeTool._endchar = PretreeTool._defec;
			PretreeTool._achtungZahl = (char) PretreeTool._defaz;
			PretreeTool._achtungKnoten = (char) PretreeTool._defak;
			String command = args[0];
			boolean wordfile = false;
			boolean reverse = PretreeTool._defrv;
			boolean ignorecase = PretreeTool._defic;
			double thresh = PretreeTool._defth;
			int i = 1;
			while (args[i].startsWith("-")) {
				String[] a = PretreeTool.split(args[i], "=");
				if (a[0].equals("-t")) {
					thresh = Double.parseDouble(a[1]);
				} else if (args[i].equals("-f")) {
					wordfile = true;
				} else if (args[i].equals("-rv")) {
					reverse = true;
				} else if (args[i].equals("-ic")) {
					ignorecase = true;
				} else if (a[0].equals("-sc")) {
					PretreeTool._startchar = Integer.parseInt(a[1]);
				} else if (a[0].equals("-ec")) {
					PretreeTool._endchar = Integer.parseInt(a[1]);
				} else if (a[0].equals("-az")) {
					PretreeTool._achtungZahl = (char) Integer.parseInt(a[1]);
				} else if (a[0].equals("-ak")) {
					PretreeTool._achtungKnoten = (char) Integer.parseInt(a[1]);
				} else {
					System.out.println("unknown option " + a[0]);
					System.exit(-1);
				}
				i++;
			}
			PretreeTool._basis = PretreeTool._endchar - PretreeTool._startchar
					+ 1;
			PretreeTool._offsetlaenge = (int) Math.ceil(Math
					.log(Integer.MAX_VALUE)
					/ Math.log(PretreeTool._basis));
			PretreeTool pretree = new PretreeTool();
			if (command.equals("t") || command.equals("train")) {
				pretree.train(args[i], args[i + 1], reverse, ignorecase);
			} else if (command.equals("p") || command.equals("prune")) {
				pretree.prune(args[i], args[i + 1], thresh);
			} else if (command.equals("tp") || command.equals("trainprune")) {
				pretree.train_prune(args[i], args[i + 1], thresh, reverse,
						ignorecase);
			} else if (command.equals("c") || command.equals("classify")) {
				if (wordfile) {
					pretree.polyclassify(args[i], args[i + 1], thresh);
				} else {
					pretree.classify(args[i], args[i + 1], thresh);
				}
			} else if (command.equals("cv") || command.equals("convert")) {
				for (; i < args.length; i++) {
					pretree.convert(args[i]);
				}
			} else if (command.equals("pr") || command.equals("print")) {
				for (; i < args.length; i++) {
					pretree.print(args[i]);
				}
			}
		} catch (IndexOutOfBoundsException iobe) {
			PretreeTool.printHelp();
			System.exit(1);
		}
	}// end main

	private static String[] split(String aString, String delimiter) {

		StringTokenizer st = new StringTokenizer(aString, delimiter);
		String[] result = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			result[i++] = st.nextToken();
		}

		return result;
	}

}// end class
