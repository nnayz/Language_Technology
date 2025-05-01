/*
 * DoNothingWordTokenizerImpl.java
 *
 * Created on 28. September 2006, 11:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package de.uni_leipzig.asv.utils.tokenizer;

/**
 * Using external tokenized text you should use the tokenizer which don't apply
 * any tokenizer rule to a sentence.
 * 
 * @author mbuechler
 */
public class DoNothingWordTokenizerImpl extends AbstractWordTokenizerImpl {

	/** Creates a new instance of DoNothingWordTokenizerImpl */
	public DoNothingWordTokenizerImpl() {
	}

	@Override
	public String execute(String strLine) {
		return strLine;
	}
}
