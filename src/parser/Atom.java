package parser;

import java.lang.*;
import java.util.Vector;
import java.util.Hashtable;

/**
 * File: Atom.java
 * 
 * This file is the Atom class.
 * 
 * The atom class is used for elements of a Lisp program which are alphanumeric literals
 * (appropriately formed with only leading alphabeticals) or strictly numerics.  The 
 * constructor uses the Patterns class to access the common regular expressions
 * to identify legal atoms.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 */	

class Atom extends TreeNode {
	private String literalString;
	
	protected boolean isList(){ return false; }
	
	/**
	 * Function: Atom
	 * 
	 * Constructor(String s)
	 * 
	 * This forms an atom (if it of valid form) from a string as a literal
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param s A literal string to be used for the atom
	 * 
	 * @throws Exception If the string is not that of an acceptable atom
	 */

	public Atom(String s) throws Exception{
		if ( ! s.matches(Patterns.LITERAL) && ! s.matches(Patterns.NUMERIC_ATOM) ){
			throw new Exception("Error!");
		}
		literalString = s;
		tokens = new Vector <String> ();
		tokens.add(literalString);
	}
	
	/**
	 * Function: Atom
	 * 
	 * Constructor(boolean b)
	 * 
	 * This constructs an atom to be T or NIL from a boolean argument.  If true is
	 * given, then the atom will be T and NIL for false.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param b A boolean representing T or NIL
	 */

	public Atom(boolean b){
		literalString = b ? "T" : "NIL";
		tokens = new Vector <String> ();
		tokens.add(literalString);
	}
	
	/**
	 * Function Atom
	 * 
	 * Constructor(int i)
	 * 
	 * This constructs an Atom from the Integer primitive. Simply converts
	 * the integer to a string to be used for the literal representation.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param i An integer
	 */

	public Atom(int i){
		literalString = Integer.toString(i);
		tokens = new Vector <String> ();
		tokens.add(literalString);
	}
	
	/**
	 * Function toString
	 * 
	 * This simply returns the atom literal. It accounts for redundantly positive
	 * numbers - that is - numbers which are preceded by '+' and can simply be
	 * expressed without it. That prevents Integer.parseInt() from throwing format
	 * errors.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @return The string version of the literal atom
	 */

	public String toString(){
		if ( literalString.matches(Patterns.NUMERIC_ATOM) ){
			return literalString.replaceAll("\\A\\+", "");
		} else {
			return literalString;
		}
	}
	
	/**
	 * Function evaluate
	 * 
	 * Returns the atom itself, and if it is representing a variable,
	 * then return the value of that variable.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @return The atom or the variable value of it if applicable
	 * 
	 * @throws Exception Require by parent class
	 */

	protected TreeNode evaluate() throws Exception{
		if ( Environment.varIsDefined(literalString) ){
			return Environment.getVarValue(literalString);
		} else {
			return this;
		}
	}
	
	/**
	 * Function: evaluate
	 * 
	 * This calls the base evaluate function but supports calls that come with extra parameters.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param env A Hashtable of bound variables. Not used.
	 *
	 */

	protected TreeNode evaluate(Hashtable <String, TreeNode> env) throws Exception{
		return evaluate();
	}
	
	/**
	 * Function: evaluate
	 * 
	 * This calls the base evaluate function but supports calls that come with extra parameters.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param env A Hashtable of bound variables. Not used.
	 *
	 */

	protected TreeNode evaluate(boolean flag, Hashtable <String, TreeNode> env) throws Exception{
		return evaluate();
	}
	
	/**
	 * Function: evaluate
	 * 
	 * This calls the base evaluate function but supports calls that come with extra parameters.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param env A Hashtable of bound variables. Not used.
	 *
	 */

	protected TreeNode evaluate(boolean flag) throws Exception{
		return evaluate();
	}
}
