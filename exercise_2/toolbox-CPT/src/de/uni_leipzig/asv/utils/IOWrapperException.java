/*
 * $Header: /usr/cvs/toolbox/src/de/uni_leipzig/asv/utils/IOWrapperException.java,v 1.2 2006/11/17 15:30:25 ksveds Exp $
 * 
 * Created on May 16, 2005
 * by knorke
 * 
 * package de.uni_leipzig.asv.utils
 * for knorke project
 *
 */
package de.uni_leipzig.asv.utils;

/**
 * @author knorke
 * @date May 16, 2005 3:02:46 PM
 */
public class IOWrapperException extends Exception {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3546645395301020216L;

	IOWrapperException(String string) {
		super(string);
	}
}
