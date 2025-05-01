/*
 * AbstractWordTokenizerImpl.java
 *
 * Created on 16. M�rz 2006, 19:55
 *
 * Diese Klasse ist im Rahmen meiner Diplomarbeit zum Thema
 * "fensterbasierte Kookkurrenzen" entstanden. Die Rechte an dieser
 * Klasse liegen beim Lehrstuhl f�r
 *
 *      Automatische Sprachverarbeitung
 *      Augustusplatz 10/11
 *      04109 Leipzig
 *
 *      http://www.asv.informatik.uni-leipzig.de
 *
 *      Betreuer    : Prof. Gerhard Heyer, Dipl.-Inf. Stefan Bordag
 *      Konzeption  : Dipl.-Inf. Stefan Bordag, Marco B�chler
 *      Entwicklung : Marco B�chler
 *
 *
 */

package de.uni_leipzig.asv.utils.tokenizer;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author development
 */
public abstract class AbstractWordTokenizerImpl {
	public final String EXPLIZIT_CUT[] = new String[] { "(", ")", "{", "}",
			"[", "]", "\"", ":", "!", "?", "�", ";", "'", ",", "#",

			"�", "�" };

	protected String strAbbrevListFile = null;

	protected Set<String> objAbbrevList = new HashSet<String>();

	/**
	 * Creates a new instance of AbstractWordTokenizerImpl
	 */
	public AbstractWordTokenizerImpl() {
	}

	protected int getWhiteSpaceBeforeDot(int intPosDot, String strFragment) {
		int intResult = intPosDot - 1;

		while (intResult > 0) {
			if (Character.isWhitespace(strFragment.charAt(intResult))) {
				return intResult;
			}
			intResult--;
		}

		return 0;
	}

	public String execute(String strLine) {
		return null;
	}

	protected ArrayList<int[]> getWhitespacePositions(String strCurLine) {
		ArrayList<int[]> objTokens = new ArrayList<int[]>(32);

		int intEndPos = strCurLine.length();
		for (int i = 0; i < intEndPos; i++) {
			if (Character.isWhitespace(strCurLine.charAt(i))) {
				int intPos[] = new int[1];
				intPos[0] = i;
				objTokens.add(intPos);
			}
		}

		int intPos[] = new int[1];
		intPos[0] = strCurLine.length();
		objTokens.add(intPos);
		return objTokens;
	}
}
