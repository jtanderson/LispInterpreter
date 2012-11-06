package parser;

import java.util.Vector;
import java.util.Hashtable;
import helpers.*;
import java.lang.reflect.*;

/**
 * File: SExpression.java
 * 
 * This file is the class which represents S-Expressions in the
 * interpreter. It handles evaluation and construction from various
 * kinds of input. It is also what controls whether or not the
 * output is returned in list notation or dot notation.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 *
 */

class SExpression extends TreeNode{
	protected TreeNode address;
	protected TreeNode data;
	protected Vector <String> dataTokens;
	protected Vector <String> addressTokens;

	/**
	 * Function: SExpression
	 * 
	 * Constructor: SExpression(Vector <String> s)
	 * 
	 * This is the constructor that creates an S-Expression from a
	 * vector string. It calls conshelper to make sure input is 
	 * valid and actually does the creation.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s A string vector
	 * 
	 * @throws Exception If the input is not representative of an S-Expression
	 * 
	 * @see Parser.SExpression.consHelper
	 *
	 */
	public SExpression(Vector <String> s) throws Exception{
		consHelper(s);
	}

	/**
	 * Function: SExpression
	 * 
	 * Constructor: SExpression(TreeNode t)
	 * 
	 * This function creast
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param t A treenode to be "cast" to an S-Expression
	 * 
	 * @throws Exception If the Treenode is not suitable
	 * 
	 * @see Parser.SExpression.consHelper
	 *
	 */
	public SExpression(TreeNode t) throws Exception{
		consHelper(t.tokens);
	}

	/**
	 * Function: SExpression
	 * 
	 * Constructor: SExpression(TreeNode a, Treenode d)
	 * 
	 * This function takes two TreeNodes and puts one in the 
	 * address field and the other in the data field and updates
	 * the tokens accordingly
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param a The address-to-be TreeNode
	 * @param d The data-to-be TreeNode
	 *
	 */
	public SExpression(TreeNode a, TreeNode d){
		address = a;
		data = d;
		dataTokens = d.tokens;
		addressTokens = a.tokens;
		tokens = new Vector <String> ();
		tokens.add("(");
		tokens.addAll(a.tokens);
		tokens.add(".");
		tokens.addAll(d.tokens);
		tokens.add(")");
	}

	/**
	 * Function: SExpression
	 * 
	 * Constructor: SExpression(SExpression s)
	 * 
	 * This is essentially a deep-copy constructor
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param s The to-be-copied S-Expression
	 * 
	 * @throws Exception If any of the sub-expressions are unsuitable or malformed
	 *
	 */
	public SExpression(SExpression s) throws Exception{
		data = TreeNode.create(s.dataTokens);
		address = TreeNode.create(s.addressTokens);
		dataTokens = new Vector <String> (s.dataTokens);
		addressTokens = new Vector <String> (s.addressTokens);
	}

	/**
	 * Function: consHelper
	 * 
	 * This is a magic function to take a string vector, make sure it is a
	 * suitable suitable representation of an S-Expression, and calculate
	 * which parts are the address and which is the data.  It appropriately
	 * udpates the tokens as well.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param s The string vector to fit into an S-Expression
	 * 
	 * @throws Exception If the vector is not fit for an S-Expression (i.e. does not begin with '(' )
	 *
	 */
	private void consHelper(Vector <String> s) throws Exception{
		if ( s.size() > 0 && s.get(0).matches("[(]") ){ // some sanity checking for now
			int i = 1;
			int dataStart = 3;
			if ( s.get(i) == "(" ){
				int open = 1;
				while ( open > 0 && i < s.size() ){
					i++;
					if ( s.get(i) == "(" ){
						open++;
					} else if ( s.get(i) == ")" ){
						open--;
					}
				}
				dataStart = i + 1;
			}
			i = dataStart > 3 ? s.indexOf(".", dataStart) : 2;
			addressTokens = new Vector <String>(s.subList(1,i));
			address = TreeNode.create(addressTokens);
			dataTokens = new Vector <String>(s.subList(i+1, s.size() - 1));
			data = TreeNode.create(dataTokens);
			tokens = new Vector <String> ();
			tokens.add("(");
			tokens.addAll(addressTokens);
			tokens.add(".");
			tokens.addAll(dataTokens);
			tokens.add(")");
		} else {
			throw new Exception("Error! Invalid S-Expression: " + s.toString());
		}
	}

	/**
	 * Function: isList()
	 * 
	 * This function determines if the S-Expression is part of a
	 * list or not by the fact that it is a list iff its data is
	 * NIL or the data of one of its 'sub-expressions' is NIL
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @return True or False depending on if it is a list
	 *
	 */
	protected boolean isList(){
		return data.toString().matches("NIL") || data.isList();
	}
	
	/**
	 * Function: toString
	 * 
	 * Provides the basic toString functionality. It initially tries
	 * to print it as a list but if it cannot be converted to list notation
	 * it uses standard dot notation.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @return The dot- or list-notation of the S-Expression
	 *
	 */
	public String toString(){
		if ( isList() ){
			try {
				return toListString();
			} catch (Exception e){
				return "(" + address.toString() + " . " + data.toString() + ")";
			}
		} else {
			return "(" + address.toString() + " . " + data.toString() + ")";
		}
	}

	/**
	 * Function: toListString
	 * 
	 * Outputs the SExpression data as a list using the prescribed
	 * notation.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @return The String representing the list-notation of the S-Expression
	 *
	 */
	protected String toListString() throws Exception{
		return "(" + StringHelpers.join(toVector(), " ") + ")";
	}

	/**
	 * Function: toVector
	 * 
	 * This function converts the string data to a vector suitable
	 * for conversion to list notation.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @return The String vector of the S-Expression
	 * 
	 * @throws Exception if the SExpression is not able to be converted
	 *
	 */
	private Vector <String> toVector() throws Exception{
		if ( ! isList() ){
			throw new Exception("Error!");
		}
		Vector <String> v = new Vector <String> ();
		SExpression tmp = this;
		while ( tmp.isList() ){
			v.add(tmp.address.toString());
			try { 
				tmp = new SExpression(tmp.dataTokens);
			} catch (Exception e){
				break;
			}
		}
		return v;
	}

	/**
	 * Function: evaluate
	 * 
	 * This is a connector function to the main evaluation procedure.
	 * It takes not arguments and just passes a default value to the main
	 * subroutine.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @return The result of evaluation of the SExpression
	 * 
	 * @throws Exception If evaluation fails
	 * 
	 * @see evaluate
	 *
	 */
	protected TreeNode evaluate() throws Exception{
		return this.evaluate(false);
	}

	/**
	 * Function: evaluate
	 * 
	 * This function allows invoking of the main evaluation routine
	 * without specifying the flag. It passes a default value and the
	 * given environment varables.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param env A Hashtable of the variables to be considered during evaluation
	 * 
	 * @see evaluate
	 *
	 */
	protected TreeNode evaluate(Hashtable <String, TreeNode> env) throws Exception{
		return evaluate(false, env);
	}
	
	/**
	 * Function: evaluate
	 * 
	 * Provides an interface to accept both the flag and environment varaibles
	 * as arguments. This swaps in the current scope variables and when finished,
	 * 'pops' them back out.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param flag Whether or not to take numericals literally
	 * @param env Variable bindings to use
	 * 
	 * @return The result of evaluation
	 * 
	 * @see evaluate
	 *
	 */
	protected TreeNode evaluate(boolean flag, Hashtable <String, TreeNode> env) throws Exception{	
		Hashtable <String, TreeNode> oldVars = Environment.getVarTable();
		Environment.mergeVars(env);
		TreeNode rtn = evaluate(flag);
		Environment.setVars(oldVars);
		return rtn;
	}

	/**
	 * Function: evaluate
	 * 
	 * This is the main evaluation function for an SExpression.
	 * It takes a flag that decides whether or not to take numerical
	 * items literally. That should only happen when they are arguments
	 * to a primitive or user-defined function.
	 * 
	 * This function uses reflection on the Primitives object to find the
	 * appropriate primitive function to run. It also first searches the
	 * defined functions and variables for bound values.  If none are set,
	 * it uses the string name of the function call to find the appropriate
	 * primitive function. Using reflection, that primitive is invoked with
	 * the 'data' component of the current S-Expression as an argument.  This
	 * is because in the operational semantics, the primitives operate on the
	 * CADR or CDR of the S-Expression. So we simplify here by just passing
	 * the CDR and the primitives do any further chomping.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param flag Whether or not to interpret numerics literally
	 * 
	 * @return The TreeNode representation of the result
	 * 
	 * @throws Exception If the function name, variable, etc. is undefined
	 *
	 */
	protected TreeNode evaluate( boolean flag ) throws Exception{
		String a = address.evaluate().toString();
		SExpression params;
		TreeNode rtn;

		if ( flag && a.matches(Patterns.NUMERIC_ATOM) ){
			return address.evaluate();
		} else if ( a.matches("NIL") || a.matches("T") ){
			return a.matches("NIL") ? Primitives.NIL() : Primitives.T();
		} else if ( Environment.varIsDefined(a) ){
			return Environment.getVarValue(a);
		} else if ( Environment.functionIsDefined(a) ){
			return Environment.executeFunction(a, TreeNode.create(dataTokens));
		} else if ( a.matches("CAR") || a.matches("CDR") ){
			SExpression s;
			if ( data.isList() ){
				s = new SExpression(dataTokens);
				s = new SExpression(s.address.evaluate().tokens);
				// s = new SExpression(s.evaluate().tokens);
			} else {
				s = new SExpression(dataTokens);
			}
			params = s;
		} else if ( a.matches("DEFUN") ){
			return Primitives.DEFUN((SExpression) data);
		} else {
			params = (SExpression) data;
		}

		try{
			rtn = invokePrimitive(a, params);
			
			/**
			 * @note: The commented code below was an attempt to improve
			 * performance over using reflection, but standard timing
			 * benchmarks showed no performance increase.  So I kept the 
			 * reflection strategy as opposed to increasing code complexity.
			 */

			// Primitives.Primitive enumVal = Primitives.Primitive.valueOf(a);

			// switch(enumVal){
			// 	case PLUS: rtn = Primitives.PLUS(params); break;
			// 	case MINUS: rtn = Primitives.MINUS(params); break;
			// 	case T: rtn = Primitives.T(); break;
			// 	case NIL: rtn = Primitives.NIL(); break;
			// 	case CONS: rtn = Primitives.CONS(params); break;
			// 	case CAR: rtn = Primitives.CAR(params); break;
			// 	case CDR: rtn = Primitives.CDR(params); break;
			// 	case ATOM: rtn = Primitives.ATOM(params); break;
			// 	case EQ: rtn = Primitives.EQ(params); break;
			// 	case NULL: rtn = Primitives.NULL(params); break;
			// 	case INT: rtn = Primitives.INT(params); break;
			// 	case QUOTIENT: rtn = Primitives.QUOTIENT(params); break;
			// 	case TIMES: rtn = Primitives.TIMES(params); break;
			// 	case REMAINDER: rtn = Primitives.REMAINDER(params); break;
			// 	case LESS: rtn = Primitives.LESS(params); break;
			// 	case GREATER: rtn = Primitives.GREATER(params); break;
			// 	case COND: rtn = Primitives.COND(params); break;
			// 	case QUOTE: rtn = Primitives.QUOTE(params); break;
			// 	case DEFUN: rtn = Primitives.DEFUN(params); break;
			// 	default: 
			// 		throw new Exception("Error! Undfined literal: " + a);
			// }
			return rtn;
		} catch (Exception e){
			throw e;
			// throw new Exception("Error! Undefined literal: " + toString());
			// throw new Exception("Error!");
		}
	}

	/**
	 * Function: invokePrimitive
	 * 
	 * This function contains the reflection logic to call one of the
	 * functions of the Primitives class with the appropriate SExpression
	 * passed as data.  If the return value is boolean, it returns the
	 * appropriate atom representation.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param name The string name of the requested function
	 * @param obj The data to be used as primitive data
	 * 
	 * @return The object returned by the primitive cast as a TreeNode (what it should be anyway)
	 * 
	 * @throws Exception If the primitive does not exist
	 *
	 */
	private TreeNode invokePrimitive(String name, SExpression obj) throws Exception{
		java.lang.reflect.Method m;

		m = Primitives.class.getDeclaredMethod(name, SExpression.class);
		m.setAccessible(true);
		Object o = m.invoke(null, obj);
		if ( o.toString().matches("true") ){
			return new Atom("T");
		} else if ( o.toString().matches("false") ){
			return new Atom("NIL");
		} else {
			return (TreeNode) o;
		}
	}
}
