package parser;

import java.util.*;
import helpers.*;

/**
 * File: TreeNode.java
 * 
 * This is the central data structure for representing Atoms
 * and S-Expressions.  It maintains the string vector of
 * tokens which make up the object and also employs the factory
 * pattern to create new TreeNodes based on whether or not they
 * are valid S-Expressions.
 * 
 * This class also stipulates the evaluate funcntions and the
 * isList method.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 *
 */

abstract public class TreeNode{
	protected abstract boolean isList();
	protected Vector <String> tokens = new Vector <String> ();

	/**
	 * Function: create
	 * 
	 * Uses the factory pattern to create an atom if the passed string
	 * vector is appropriate or an S-Expression.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The string vector of the new TreeNode
	 * 
	 * @return The new TreeNode object
	 * 
	 * @throws Exception If the node is empty or if the constructor fails
	 *
	 */
	static TreeNode create(Vector <String> s) throws Exception{
		if ( s.size() > 0 && s.get(0).matches("[(]") ){
			return new SExpression(s);
		} else if ( s.size() > 0 ){
			return new Atom(s.get(0));
		} else {
			throw new Exception("Tried to create a TreeNode with no data");
		}
	}

	/**
	 * Function: create
	 * 
	 * Adapts the factory pattern to force the creation of an Atom
	 * if the data is a boolean value.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param b The boolean value of the Atom-to-be
	 * 
	 * @return The new atom object
	 *
	 */
	static TreeNode create(boolean b){
		return new Atom(b);
	}

	/**
	 * Function: create
	 * 
	 * Adapts the factory pattern to create an Atom if the data
	 * is an integer.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param i The integer to use as a literal
	 * 
	 * @return The new Atom
	 *
	 */
	static TreeNode create(int i){
		return new Atom(i);
	}

	/**
	 * Function: evaluate
	 * 
	 * The evaluation of TreeNodes returns a new TreeNode.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @return The result of evaluating the TreeNode
	 *
	 */
	abstract TreeNode evaluate() throws Exception;

	/**
	 * Function: evaluate
	 * 
	 * The evaluation of TreeNodes returns a new TreeNode.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param flag Whether or not to take numericals literally
	 * 
	 * @return The result of evaluating the TreeNode
	 *
	 */
	abstract TreeNode evaluate(boolean flag) throws Exception;
	
	/**
	 * Function: evaluate
	 * 
	 * The evaluation of TreeNodes returns a new TreeNode.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param flag Whether or not to take numericals literally
	 * @param env The scoped variables
	 * 
	 * @return The result of evaluating the TreeNode
	 *
	 */
	abstract TreeNode evaluate(boolean flag, Hashtable <String, TreeNode> env) throws Exception;
	
	/**
	 * Function: evaluate
	 * 
	 * The evaluation of TreeNodes returns a new TreeNode.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param env The scoped variables
	 * 
	 * @return The result of evaluating the TreeNode
	 *
	 */	
	abstract TreeNode evaluate(Hashtable <String, TreeNode> env) throws Exception;
}
