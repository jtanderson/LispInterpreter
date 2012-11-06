package helpers;

import java.lang.String;
import java.util.*;

/**
 * File: StringHelpers.java
 * 
 * This file is for some helpers to work with strings and vectors of strings.
 * 
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 */

public class StringHelpers{

	/**
	 * Function: join
	 *
	 * This function is used to implodde a generic list together
	 * with the specified "glue" string
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param s 	Any List whose elements support the toString method
	 * @param glue	A string with which to implode the list element strings
	 * 
	 * @return 		The string of list elements stringified and joined
	 */	
	public static String join(List s, String glue){
		String newString = "";
		for ( int i = 0; i < s.size() - 1; i++ ){
			newString = newString + s.get(i).toString() + glue;
		}
		return newString + s.get(s.size() - 1).toString();
	}

	/**
	 * Function: vectorize
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param s A string
	 * @return A vector where each element is a signle character of the string
	 */	
	public static Vector <String> vectorize(String s){
		Vector <String> v = new Vector <String> ();
		int i;
		for ( i = 0; i < s.length() - 1; i ++ ){
			v.add(s.substring(i,i+1));
		}
		if ( i > 0 ){
			v.add(s.substring(i));
		}
		return v;
	}
}