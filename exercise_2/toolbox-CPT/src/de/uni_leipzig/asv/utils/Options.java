package de.uni_leipzig.asv.utils;

// standard imports

/**
 * Represents the options for the program <br>
 * currently supported:<br>
 * Options:<br>
 * <br>
 * [Connection]<br>
 * URL=*<br>
 * user=*<br>
 * passwd=*<br>
 * <br>
 * [General]<br>
 * debuglevel=[0,1,2,3]<br>
 * outputfile=*<br>
 * stopwortliste=*<br>
 * <br>
 * [Trigram]<br>
 * trimaxrecursion=0...n<br>
 * triminrecursion=0...n<br>
 * trimaxkollokationen=0...n<br>
 * triquerykollokationen=*<br>
 * <br>
 * [Disambiguator]<br>
 * kollokationenperstep=0...n<br>
 * clustertreshold=0...n<br>
 * minsignifikanz=0...n<br>
 * maxkollokationen=0...n<br>
 * disquerykollokatoinen=*<br>
 * <br>
 * [Sachgebiete]<br>
 * maxdefiningwords=0...n<br>
 * disstepsperword=0...n<br>
 * sgquery=*<br>
 * sachnummerquery=*<br>
 * 
 * @author Stefan Bordag
 * @date 16.03.2002
 * @see de.uni_leipzig.asv.bordag.sgz.util.IniFile
 */
public class Options {

	public static final String TRUE = "1";

	public static final String FALSE = "0";

	public static final String INI_CONNECTION = "Connection";

	public static final String INI_CONNECTION_DRIVER = "driver";

	public static final String INI_CONNECTION_DRIVER_DEFAULT = "org.gjt.mm.mysql.Driver";

	public static final String INI_CONNECTION_URL = "Url";

	public static final String INI_CONNECTION_URL_DEFAULT = "jdbc:mysql://lipsia.informatik.uni-leipzig.de/wortschatz?user=bordag&password=blalala";

	public static final String INI_CONNECTION_USER = "user";

	public static final String INI_CONNECTION_USER_DEFAULT = "bordag";

	public static final String INI_CONNECTION_PASS = "passwd";

	public static final String INI_CONNECTION_PASS_DEFAULT = "blalala";

	public static final String INI_GENERAL = "General";

	public static final String INI_GENERAL_OUTPUTFILE = "outputfile";

	public static final String INI_GENERAL_OUTPUTFILE_DEFAULT = "output";

	public static final String INI_GENERAL_USEOUTPUTFILE = "useoutputfile";

	public static final String INI_GENERAL_USEOUTPUTFILE_DEFAULT = Options.FALSE;

	public static final String INI_GENERAL_STOPWORTFILE = "stopwortliste";

	public static final String INI_GENERAL_STOPWORTFILE_DEFAULT = "stopwoerter.txt";

	public static final String INI_GENERAL_USESTOPWORTFILE = "usestopwortliste";

	public static final String INI_GENERAL_USESTOPWORTFILE_DEFAULT = Options.TRUE;

	public static final String INI_GENERAL_DEBUGLEVEL = "debuglevel";

	public static final String INI_GENERAL_DEBUGLEVEL_DEFAULT = "0";

	public static final String INI_GENERAL_QUERYANCHOR = "queryanchor";

	public static final String INI_GENERAL_QUERYANCHOR_DEFAULT = "#ARG#";

	public static final String INI_GENERAL_SHOWCLUSTERINGCOEFF = "showclusteringcoefficient";

	public static final String INI_GENERAL_SHOWCLUSTERINGCOEFF_DEFAULT = Options.FALSE;

	public static final String INI_GENERAL_NUMBER2WORD = "number2word";

	public static final String INI_GENERAL_NUMBER2WORD_DEFAULT = "select w.wort_bin from wortliste w where w.wort_nr = #ARG#;";

	public static final String INI_GENERAL_WORD2NUMBER = "word2number";

	public static final String INI_GENERAL_WORD2NUMBER_DEFAULT = "select w.wort_nr from wortliste w where w.wort_bin = \"#ARG#\";";

	public static final String INI_GENERAL_QUERYFREQUENCY = "queryfrequencyofwordnr";

	public static final String INI_GENERAL_QUERYFREQUENCY_DEFAULT = "select w.anzahl from wortliste w where w.wort_nr = \"#ARG#\";";

	public static final String INI_TRIGRAM = "Trigram";

	public static final String INI_TRIGRAM_TRIMAXRECURSION = "trimaxrecursion";

	public static final String INI_TRIGRAM_TRIMAXRECURSION_DEFAULT = "0";

	public static final String INI_TRIGRAM_TRIMINRECURSION = "triminrecursion";

	public static final String INI_TRIGRAM_TRIMINRECURSION_DEFAULT = "0";

	public static final String INI_TRIGRAM_MINSIGNIFIKANZ = "triminsignifikanz";

	public static final String INI_TRIGRAM_MINSIGNIFIKANZ_DEFAULT = "7";

	public static final String INI_TRIGRAM_MINWORDNR = "minwordnr";

	public static final String INI_TRIGRAM_MINWORDNR_DEFAULT = "1000";

	public static final String INI_TRIGRAM_MAXKOLLOKATIONEN = "trimaxkollokationen";

	public static final String INI_TRIGRAM_MAXKOLLOKATIONEN_DEFAULT = "1000";

	public static final String INI_TRIGRAM_QUERYKOLLOKATIONEN = "triquerykollokationen";

	public static final String INI_TRIGRAM_QUERYKOLLOKATIONEN_DEFAULT = "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;";

	public static final String INI_PARASYNTAGMA = "ParaSyntagma";

	public static final String INI_PARASYNTAGMA_MINSIGNIFIKANZ = "minsignifikanz";

	public static final String INI_PARASYNTAGMA_MINSIGNIFIKANZ_DEFAULT = "10";

	public static final String INI_PARASYNTAGMA_MINSECONDARYSIGNIFIKANZ = "minsecondarysignifikanz";

	public static final String INI_PARASYNTAGMA_MINSECONDARYSIGNIFIKANZ_DEFAULT = "1";

	public static final String INI_PARASYNTAGMA_QUERYKOLLOKATIONEN = "querykollokationen";

	public static final String INI_PARASYNTAGMA_QUERYKOLLOKATIONEN_DEFAULT = "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;";

	public static final String INI_PARASYNTAGMA_QUERYDISAMBIG = "querydisambkollokationen";

	public static final String INI_PARASYNTAGMA_QUERYDISAMBIG_DEFAULT = "SELECT d.wort_nr2,d.wort_grp FROM disamb_sig d WHERE d.wort_nr2 > #ARG# and d.wort_nr1 = #ARG# order by d.wort_grp desc;";

	public static final String INI_PARASYNTAGMA_MAXKOLLOKATIONEN = "maxkollokationen";

	public static final String INI_PARASYNTAGMA_MAXKOLLOKATIONEN_DEFAULT = "1000";

	public static final String INI_PARASYNTAGMA_MINWORDNR = "minwordnr";

	public static final String INI_PARASYNTAGMA_MINWORDNR_DEFAULT = "1000";

	public static final String INI_PARASYNTAGMA_LASTWORD = "lastword";

	public static final String INI_PARASYNTAGMA_LASTWORD_DEFAULT = "Elefant";

	public static final String INI_DISAMBIGUATOR = "Disambiguator";

	public static final String INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP = "kollokationenperstep";

	public static final String INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP_DEFAULT = "15";

	public static final String INI_DISAMBIGUATOR_CLUSTERTHRESHOLD = "clusterthreshold";

	public static final String INI_DISAMBIGUATOR_CLUSTERTHRESHOLD_DEFAULT = "51";

	public static final String INI_DISAMBIGUATOR_MINSIGNIFIKANZ = "minsignifikanz";

	public static final String INI_DISAMBIGUATOR_MINSIGNIFIKANZ_DEFAULT = "10";

	public static final String INI_DISAMBIGUATOR_MAXKOLLOKATIONEN = "maxkollokationen";

	public static final String INI_DISAMBIGUATOR_MAXKOLLOKATIONEN_DEFAULT = "1000";

	public static final String INI_DISAMBIGUATOR_MINWORDNR = "minwordnr";

	public static final String INI_DISAMBIGUATOR_MINWORDNR_DEFAULT = "1000";

	public static final String INI_DISAMBIGUATOR_MAXRUNS = "maxruns";

	public static final String INI_DISAMBIGUATOR_MAXRUNS_DEFAULT = "15";

	public static final String INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN = "disquerykollokationen";

	public static final String INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN_DEFAULT = "SELECT k.wort_nr2,k.signifikanz FROM kollok_sig k WHERE k.signifikanz > #ARG# and k.wort_nr2 > #ARG# and k.wort_nr1 = #ARG# order by k.signifikanz desc limit #ARG#;";

	public static final String INI_DISAMBIGUATOR_LASTWORD = "lastword";

	public static final String INI_DISAMBIGUATOR_LASTWORD_DEFAULT = "space";

	public static final String INI_SACHGEBIETE = "Sachgebiete";

	public static final String INI_SACHGEBIETE_MAXDEFININGWORDS = "maxdefiningwords";

	public static final String INI_SACHGEBIETE_MAXDEFININGWORDS_DEFAULT = "45";

	public static final String INI_SACHGEBIETE_DISSTEPSPERWORD = "sachdisstepsperword";

	public static final String INI_SACHGEBIETE_DISSTEPSPERWORD_DEFAULT = "1";

	public static final String INI_SACHGEBIETE_MINWORDFREQ = "sachminwordfreq";

	public static final String INI_SACHGEBIETE_MINWORDFREQ_DEFAULT = "60";

	public static final String INI_SACHGEBIETE_QUERYSACHGEBIETE = "sgquery";

	public static final String INI_SACHGEBIETE_QUERYSACHGEBIETE_DEFAULT = "select w.wort_nr from wortliste w, sachgebiet s where s.sa_nr = #ARG# and w.wort_nr = s.wort_nr and w.anzahl > #ARG# limit #ARG#";

	public static final String INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER = "sachnummerquery";

	public static final String INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER_DEFAULT = "select s.sa_nr from sachgebiet s where s.sachgebiet = \"#ARG#\";";

	/**
	 * The sole instance of options for the whole program
	 */
	private static Options instance = null;

	/**
	 * The file, where the options are stored
	 */
	private IniFile iniFile = null;

	/**
	 * Hidden default constructor
	 */
	protected Options() {
		this.iniFile = new IniFile("DBKlassifikator.ini");
	}

	public static Options getInstance() {
		if (Options.instance == null) {
			synchronized (Options.class) {
				Options.instance = new Options();
			}
		}
		return Options.instance;
	}

	/**
	 * Sets or overwrites a given option with a given value
	 */
	private void setOptionValue(String part, String option, String value) {
		if ((value != null) && (value.length() > 0)) {
			this.iniFile.setValue(part, option, value);
		}
	}

	/**
	 * Returns the requested value.
	 */
	private String getAssured(String pkey, String skey, String defaultValue) {
		if (!this.iniFile.existsKeyPair(pkey, skey)) {
			this.iniFile.setValue(pkey, skey, defaultValue);
		}
		return this.iniFile.getValue(pkey, skey);
	}

	/*-------Connection-------*/

	public String getConDriver() {
		return getAssured(Options.INI_CONNECTION,
				Options.INI_CONNECTION_DRIVER,
				Options.INI_CONNECTION_DRIVER_DEFAULT);
	}

	public void setConDriver(String newOption) {
		setOptionValue(Options.INI_CONNECTION, Options.INI_CONNECTION_DRIVER,
				newOption);
	}

	/**
	 * Returns the url after checking whether the inifile contained it. If it
	 * didn't it automatically adds the default value
	 */
	public String getConUrl() {
		return getAssured(Options.INI_CONNECTION, Options.INI_CONNECTION_URL,
				Options.INI_CONNECTION_URL_DEFAULT);
	}

	public void setConUrl(String newOption) {
		System.out.println("com.bordag.util.Options.Writing: " + newOption);
		setOptionValue(Options.INI_CONNECTION, Options.INI_CONNECTION_URL,
				newOption);
	}

	/**
	 * Returns the username after checking whether the inifile contained it. If
	 * it didn't it automatically adds the default value
	 */
	public String getConUser() {
		return getAssured(Options.INI_CONNECTION, Options.INI_CONNECTION_USER,
				Options.INI_CONNECTION_USER_DEFAULT);
	}

	public void setConUser(String newOption) {
		setOptionValue(Options.INI_CONNECTION, Options.INI_CONNECTION_USER,
				newOption);
	}

	/**
	 * Returns the password after checking whether the inifile contained it. If
	 * it didn't it automatically adds the default value
	 */
	public String getConPasswd() {
		return getAssured(Options.INI_CONNECTION, Options.INI_CONNECTION_PASS,
				Options.INI_CONNECTION_PASS_DEFAULT);
	}

	public void setConPasswd(String newOption) {
		setOptionValue(Options.INI_CONNECTION, Options.INI_CONNECTION_PASS,
				newOption);
	}

	/*-------General-------*/

	/**
	 * Returns the input file from where sachgebiete are to be read
	 */
	public String getGenOutputFile() {
		return getAssured(Options.INI_GENERAL, Options.INI_GENERAL_OUTPUTFILE,
				Options.INI_GENERAL_OUTPUTFILE_DEFAULT);
	}

	public void setGenOutputFile(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_OUTPUTFILE,
				newOption);
	}

	public String getGenUseOutputFile() {
		return getAssured(Options.INI_GENERAL,
				Options.INI_GENERAL_USEOUTPUTFILE,
				Options.INI_GENERAL_USEOUTPUTFILE_DEFAULT);
	}

	public void setGenUseOutputFile(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_USEOUTPUTFILE,
				newOption);
	}

	/**
	 * Returns the input file from where sachgebiete are to be read
	 */
	public String getGenStopwortFile() {
		return getAssured(Options.INI_GENERAL,
				Options.INI_GENERAL_STOPWORTFILE,
				Options.INI_GENERAL_STOPWORTFILE_DEFAULT);
	}

	public void setGenStopwortFile(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_STOPWORTFILE,
				newOption);
	}

	public String getGenUseStopwortFile() {
		return getAssured(Options.INI_GENERAL,
				Options.INI_GENERAL_USESTOPWORTFILE,
				Options.INI_GENERAL_USESTOPWORTFILE_DEFAULT);
	}

	public void setGenUseStopwortFile(String newOption) {
		setOptionValue(Options.INI_GENERAL,
				Options.INI_GENERAL_USESTOPWORTFILE, newOption);
	}

	/**
	 * Returns the input file from where sachgebiete are to be read
	 */
	public String getGenDebugLevel() {
		return getAssured(Options.INI_GENERAL, Options.INI_GENERAL_DEBUGLEVEL,
				Options.INI_GENERAL_DEBUGLEVEL_DEFAULT);
	}

	public void setGenDebugLevel(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_DEBUGLEVEL,
				newOption);
	}

	public String getGenQueryAnchor() {
		return getAssured(Options.INI_GENERAL, Options.INI_GENERAL_QUERYANCHOR,
				Options.INI_GENERAL_QUERYANCHOR_DEFAULT);
	}

	public void setGenQueryAnchor(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_QUERYANCHOR,
				newOption);
	}

	public String getGenShowClusteringCoeff() {
		return getAssured(Options.INI_GENERAL,
				Options.INI_GENERAL_SHOWCLUSTERINGCOEFF,
				Options.INI_GENERAL_SHOWCLUSTERINGCOEFF_DEFAULT);
	}

	public void setGenShowClusteringCoeff(String newOption) {
		setOptionValue(Options.INI_GENERAL,
				Options.INI_GENERAL_SHOWCLUSTERINGCOEFF, newOption);
	}

	public String getGenQueryWord2Number() {
		return getAssured(Options.INI_GENERAL, Options.INI_GENERAL_WORD2NUMBER,
				Options.INI_GENERAL_WORD2NUMBER_DEFAULT);
	}

	public void setGenQueryWord2Number(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_WORD2NUMBER,
				newOption);
	}

	public String getGenQueryNumber2Word() {
		return getAssured(Options.INI_GENERAL, Options.INI_GENERAL_NUMBER2WORD,
				Options.INI_GENERAL_NUMBER2WORD_DEFAULT);
	}

	public void setGenQueryNumber2Word(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_NUMBER2WORD,
				newOption);
	}

	public String getGenQueryFrequency() {
		return getAssured(Options.INI_GENERAL,
				Options.INI_GENERAL_QUERYFREQUENCY,
				Options.INI_GENERAL_QUERYFREQUENCY_DEFAULT);
	}

	public void setGenQueryFrequency(String newOption) {
		setOptionValue(Options.INI_GENERAL, Options.INI_GENERAL_QUERYFREQUENCY,
				newOption);
	}

	/*-------Trigrams-------*/

	public String getTriMaxRecursion() {
		return getAssured(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_TRIMAXRECURSION,
				Options.INI_TRIGRAM_TRIMAXRECURSION_DEFAULT);
	}

	public void setTriMaxRecursion(String newOption) {
		setOptionValue(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_TRIMAXRECURSION, newOption);
	}

	public String getTriMinRecursion() {
		return getAssured(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_TRIMINRECURSION,
				Options.INI_TRIGRAM_TRIMINRECURSION_DEFAULT);
	}

	public void setTriMinRecursion(String newOption) {
		setOptionValue(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_TRIMINRECURSION, newOption);
	}

	public String getTriMinSignifikanz() {
		return getAssured(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_MINSIGNIFIKANZ,
				Options.INI_TRIGRAM_MINSIGNIFIKANZ_DEFAULT);
	}

	public void setTriMinSignifikanz(String newOption) {
		setOptionValue(Options.INI_TRIGRAM, Options.INI_TRIGRAM_MINSIGNIFIKANZ,
				newOption);
	}

	public String getTriMinWordNr() {
		return getAssured(Options.INI_TRIGRAM, Options.INI_TRIGRAM_MINWORDNR,
				Options.INI_TRIGRAM_MINWORDNR_DEFAULT);
	}

	public void setTriMinWordNr(String newOption) {
		setOptionValue(Options.INI_TRIGRAM, Options.INI_TRIGRAM_MINWORDNR,
				newOption);
	}

	public String getTriMaxKollokationen() {
		return getAssured(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_MAXKOLLOKATIONEN,
				Options.INI_TRIGRAM_MAXKOLLOKATIONEN_DEFAULT);
	}

	public void setTriMaxKollokationen(String newOption) {
		setOptionValue(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_MAXKOLLOKATIONEN, newOption);
	}

	public String getTriQueryKollokationen() {
		return getAssured(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_QUERYKOLLOKATIONEN,
				Options.INI_TRIGRAM_QUERYKOLLOKATIONEN_DEFAULT);
	}

	public void setTriQueryKollokationen(String newOption) {
		setOptionValue(Options.INI_TRIGRAM,
				Options.INI_TRIGRAM_QUERYKOLLOKATIONEN, newOption);
	}

	/*-------ParaSyntagma--------*/

	public String getParaMinSignifikanz() {
		return getAssured(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MINSIGNIFIKANZ,
				Options.INI_PARASYNTAGMA_MINSIGNIFIKANZ_DEFAULT);
	}

	public void setParaMinSignifikanz(String newOption) {
		setOptionValue(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MINSIGNIFIKANZ, newOption);
	}

	public String getParaMinSecondarySignifikanz() {
		return getAssured(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MINSECONDARYSIGNIFIKANZ,
				Options.INI_PARASYNTAGMA_MINSECONDARYSIGNIFIKANZ_DEFAULT);
	}

	public void setParaMinSecondarySignifikanz(String newOption) {
		setOptionValue(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MINSECONDARYSIGNIFIKANZ, newOption);
	}

	public String getParaQueryKollokationen() {
		return getAssured(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_QUERYKOLLOKATIONEN,
				Options.INI_PARASYNTAGMA_QUERYKOLLOKATIONEN_DEFAULT);
	}

	public void setParaQueryKollokationen(String newOption) {
		setOptionValue(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_QUERYKOLLOKATIONEN, newOption);
	}

	public String getParaQueryDisambig() {
		return getAssured(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_QUERYDISAMBIG,
				Options.INI_PARASYNTAGMA_QUERYDISAMBIG_DEFAULT);
	}

	public void setParaQueryDisambig(String newOption) {
		setOptionValue(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_QUERYDISAMBIG, newOption);
	}

	public String getParaMaxKollokationen() {
		return getAssured(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MAXKOLLOKATIONEN,
				Options.INI_PARASYNTAGMA_MAXKOLLOKATIONEN_DEFAULT);
	}

	public void setParaMaxKollokationen(String newOption) {
		setOptionValue(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MAXKOLLOKATIONEN, newOption);
	}

	public String getParaMinWordNr() {
		return getAssured(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MINWORDNR,
				Options.INI_PARASYNTAGMA_MINWORDNR_DEFAULT);
	}

	public void setParaMinWordNr(String newOption) {
		setOptionValue(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_MINWORDNR, newOption);
	}

	public String getParaLastWord() {
		return getAssured(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_LASTWORD,
				Options.INI_PARASYNTAGMA_LASTWORD_DEFAULT);
	}

	public void setParaLastWord(String newOption) {
		setOptionValue(Options.INI_PARASYNTAGMA,
				Options.INI_PARASYNTAGMA_LASTWORD, newOption);
	}

	/*-------Disambiguator-------*/

	public String getDisKollokationenPerStep() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP,
				Options.INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP_DEFAULT);
	}

	public void setDisKollokationenPerStep(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP, newOption);
	}

	public String getDisClusterThreshold() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_CLUSTERTHRESHOLD,
				Options.INI_DISAMBIGUATOR_CLUSTERTHRESHOLD_DEFAULT);
	}

	public void setDisClusterThreshold(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_CLUSTERTHRESHOLD, newOption);
	}

	public String getDisMinSignifikanz() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MINSIGNIFIKANZ,
				Options.INI_DISAMBIGUATOR_MINSIGNIFIKANZ_DEFAULT);
	}

	public void setDisMinSignifikanz(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MINSIGNIFIKANZ, newOption);
	}

	public String getDisMaxKollokationen() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MAXKOLLOKATIONEN,
				Options.INI_DISAMBIGUATOR_MAXKOLLOKATIONEN_DEFAULT);
	}

	public void setDisMaxKollokationen(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MAXKOLLOKATIONEN, newOption);
	}

	public String getDisMinWordNr() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MINWORDNR,
				Options.INI_DISAMBIGUATOR_MINWORDNR_DEFAULT);
	}

	public void setDisMinWordNr(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MINWORDNR, newOption);
	}

	public String getDisMaxRuns() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MAXRUNS,
				Options.INI_DISAMBIGUATOR_MAXRUNS_DEFAULT);
	}

	public void setDisMaxRuns(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_MAXRUNS, newOption);
	}

	public String getDisQueryKollokationen() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN,
				Options.INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN_DEFAULT);
	}

	public void setDisQueryKollokationen(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN, newOption);
	}

	public String getDisLastWord() {
		return getAssured(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_LASTWORD,
				Options.INI_DISAMBIGUATOR_LASTWORD_DEFAULT);
	}

	public void setDisLastWord(String newOption) {
		setOptionValue(Options.INI_DISAMBIGUATOR,
				Options.INI_DISAMBIGUATOR_LASTWORD, newOption);
	}

	/*-------Sachgebiete-------*/

	public String getSachMaxDefiningWords() {
		return getAssured(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_MAXDEFININGWORDS,
				Options.INI_SACHGEBIETE_MAXDEFININGWORDS_DEFAULT);
	}

	public void setSachMaxDefiningWords(String newOption) {
		setOptionValue(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_MAXDEFININGWORDS, newOption);
	}

	public String getSachDisStepsPerWord() {
		return getAssured(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_DISSTEPSPERWORD,
				Options.INI_SACHGEBIETE_DISSTEPSPERWORD_DEFAULT);
	}

	public void setSachDisStepsPerWord(String newOption) {
		setOptionValue(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_DISSTEPSPERWORD, newOption);
	}

	public String getSachQuerySachgebiete() {
		return getAssured(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_QUERYSACHGEBIETE,
				Options.INI_SACHGEBIETE_QUERYSACHGEBIETE_DEFAULT);
	}

	public void setSachQuerySachgebiete(String newOption) {
		setOptionValue(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_QUERYSACHGEBIETE, newOption);
	}

	public String getSachQuerySachNr() {
		return getAssured(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER,
				Options.INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER_DEFAULT);
	}

	public void setSachQuerySachNr(String newOption) {
		setOptionValue(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER, newOption);
	}

	public String getMinWordFreq() {
		return getAssured(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_MINWORDFREQ,
				Options.INI_SACHGEBIETE_MINWORDFREQ_DEFAULT);
	}

	public void setMinWordFreq(String newOption) {
		setOptionValue(Options.INI_SACHGEBIETE,
				Options.INI_SACHGEBIETE_MINWORDFREQ, newOption);
	}

	/*-------Other-------*/

	/**
	 * Resets all options to defaultvalues by simply calling once all the gets
	 */
	public void resetAll() {
		getConUrl();
		getConUser();
		getConPasswd();

		getGenDebugLevel();
		getGenOutputFile();
		getGenUseOutputFile();
		getGenStopwortFile();
		getGenUseStopwortFile();
		getGenShowClusteringCoeff();
		getGenQueryNumber2Word();
		getGenQueryWord2Number();
		getGenQueryFrequency();

		getTriMaxRecursion();
		getTriMinRecursion();
		getTriMaxKollokationen();
		getTriMinSignifikanz();
		getTriMinWordNr();
		getTriQueryKollokationen();

		getDisKollokationenPerStep();
		getDisClusterThreshold();
		getDisMaxKollokationen();
		getDisMinSignifikanz();
		getDisMinWordNr();
		getDisMaxRuns();
		getDisQueryKollokationen();
		getDisLastWord();

		getParaLastWord();
		getParaMaxKollokationen();
		getParaMinSignifikanz();
		getParaMinWordNr();
		getParaQueryDisambig();
		getParaQueryKollokationen();

		getSachDisStepsPerWord();
		getSachMaxDefiningWords();
		getSachQuerySachgebiete();
		getSachQuerySachNr();
		getMinWordFreq();
	}

	/**
	 * Prints nicely all the settings with their current values
	 */
	public void printOptions() {
		Output.println();
		Output.println("[" + Options.INI_CONNECTION + "]");
		Output.println(Options.INI_CONNECTION_URL + " = " + getConUrl());
		Output.println(Options.INI_CONNECTION_USER + " = " + getConUser());
		Output.println(Options.INI_CONNECTION_PASS + " = " + getConPasswd());
		Output.println();
		Output.println("[" + Options.INI_GENERAL + "]");
		Output.println(Options.INI_GENERAL_DEBUGLEVEL + " = "
				+ getGenDebugLevel());
		Output.println(Options.INI_GENERAL_OUTPUTFILE + " = "
				+ getGenOutputFile());
		Output.println(Options.INI_GENERAL_STOPWORTFILE + " = "
				+ getGenStopwortFile());
		Output.println(Options.INI_GENERAL_SHOWCLUSTERINGCOEFF + " = "
				+ getGenShowClusteringCoeff());
		Output.println(Options.INI_GENERAL_WORD2NUMBER + " = "
				+ getGenQueryWord2Number());
		Output.println(Options.INI_GENERAL_NUMBER2WORD + " = "
				+ getGenQueryNumber2Word());
		Output.println(Options.INI_GENERAL_QUERYFREQUENCY + " = "
				+ getGenQueryFrequency());
		Output.println();
		Output.println("[" + Options.INI_TRIGRAM + "]");
		Output.println(Options.INI_TRIGRAM_TRIMAXRECURSION + " = "
				+ getTriMaxRecursion());
		Output.println(Options.INI_TRIGRAM_TRIMINRECURSION + " = "
				+ getTriMinRecursion());
		Output.println(Options.INI_TRIGRAM_MAXKOLLOKATIONEN + " = "
				+ getTriMaxKollokationen());
		Output.println(Options.INI_TRIGRAM_MINSIGNIFIKANZ + " = "
				+ getTriMinSignifikanz());
		Output.println(Options.INI_TRIGRAM_MINWORDNR + " = "
				+ getTriMinWordNr());
		Output.println(Options.INI_TRIGRAM_QUERYKOLLOKATIONEN + " = "
				+ getTriQueryKollokationen());
		Output.println();
		Output.println("[" + Options.INI_DISAMBIGUATOR + "]");
		Output.println(Options.INI_DISAMBIGUATOR_CLUSTERTHRESHOLD + " = "
				+ getDisClusterThreshold());
		Output.println(Options.INI_DISAMBIGUATOR_MAXKOLLOKATIONEN + " = "
				+ getDisMaxKollokationen());
		Output.println(Options.INI_DISAMBIGUATOR_KOLLOKATIONENPERSTEP + " = "
				+ getDisKollokationenPerStep());
		Output.println(Options.INI_DISAMBIGUATOR_MINSIGNIFIKANZ + " = "
				+ getDisMinSignifikanz());
		Output.println(Options.INI_DISAMBIGUATOR_MINWORDNR + " = "
				+ getDisMinWordNr());
		Output.println(Options.INI_DISAMBIGUATOR_MAXRUNS + " = "
				+ getDisMaxRuns());
		Output.println(Options.INI_DISAMBIGUATOR_QUERYKOLLOKATIONEN + " = "
				+ getDisQueryKollokationen());
		Output.println();
		Output.println("[" + Options.INI_PARASYNTAGMA + "]");
		Output.println(Options.INI_PARASYNTAGMA_LASTWORD + " = "
				+ getParaLastWord());
		Output.println(Options.INI_PARASYNTAGMA_MAXKOLLOKATIONEN + " = "
				+ getParaMaxKollokationen());
		Output.println(Options.INI_PARASYNTAGMA_MINSIGNIFIKANZ + " = "
				+ getParaMinSignifikanz());
		Output.println(Options.INI_PARASYNTAGMA_MINWORDNR + " = "
				+ getParaMinWordNr());
		Output.println(Options.INI_PARASYNTAGMA_QUERYDISAMBIG + " = "
				+ getParaQueryDisambig());
		Output.println(Options.INI_PARASYNTAGMA_QUERYKOLLOKATIONEN + " = "
				+ getParaQueryKollokationen());
		Output.println();
		Output.println("[" + Options.INI_SACHGEBIETE + "]");
		Output.println(Options.INI_SACHGEBIETE_DISSTEPSPERWORD + " = "
				+ getSachDisStepsPerWord());
		Output.println(Options.INI_SACHGEBIETE_MAXDEFININGWORDS + " = "
				+ getSachMaxDefiningWords());
		Output.println(Options.INI_SACHGEBIETE_QUERYSACHGEBIETE + " = "
				+ getSachQuerySachgebiete());
		Output.println(Options.INI_SACHGEBIETE_QUERYSACHGEBIETSNUMMER + " = "
				+ getSachQuerySachNr());
		Output.println(Options.INI_SACHGEBIETE_MINWORDFREQ + " = "
				+ getMinWordFreq());
		Output.println();
	}

}
