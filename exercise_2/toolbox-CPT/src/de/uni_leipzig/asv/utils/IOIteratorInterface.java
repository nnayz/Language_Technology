/*
 * $Header: /usr/cvs/toolbox/src/de/uni_leipzig/asv/utils/IOIteratorInterface.java,v 1.2 2006/11/17 15:30:25 ksveds Exp $
 * 
 * Created on 12.05.2005, 17:13:23 by knorke
 * for project knorke
 */
package de.uni_leipzig.asv.utils;

import java.sql.SQLException;

/**
 * @author knorke
 */
public interface IOIteratorInterface {
	/**
	 * returns true if more data available
	 * 
	 * @return
	 */
	public boolean hasNext();

	/**
	 * returns a String[] for every entry in file/database
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOIteratorException
	 */
	public Object next() throws IOIteratorException;
}