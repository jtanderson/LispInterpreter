package parser;

import java.util.*;
import java.lang.*;
import helpers.StringHelpers;

/**
 * File: ParseTree.java
 * 
 * This files contains the main tree structure of the Lisp program.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 *
 */

class ParseTree{
	private TreeNode root;
	
	/**
	 * Function: ParseTree
	 * 
	 * Constructor(Vector <String> s)
	 * 
	 * Uses the given vector to create the root. It calls the
	 * TreeNode factory constructor
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s A vector representing the outermost expression
	 * 
	 * @throws Exception If node creation fails
	 *
	 */
	public ParseTree(Vector <String> s) throws Exception{
		root = TreeNode.create(s);
	}

	/**
	 * Function: evaluate
	 * 
	 * Evaluates the root node and returns the result as a string
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @return The string of the executed statement
	 *
	 * @throws Exception If evaluation fails
	 *
	 */
	protected String evaluate() throws Exception{
		String rtn = root.evaluate().toString();
		return rtn;
	}

	/**
	 * Function: print
	 * 
	 * Stringifies and returns the parsetree as-is
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @deprecated used only for early debuggin
	 * 
	 * @return String of the root node
	 *
	 */
	protected String print(){
		String result = root.toString();
		return result;
	}
}
