package parser;

import java.lang.Integer;
import java.lang.String;
import java.util.*;
import helpers.*;

/**
 * File: Primitives.java
 * 
 * This files has the required primitive functions for Lisp
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 *
 */

class Primitives{

	// public static enum Primitive{
	// 	PLUS,
	// 	MINUS,
	// 	T,
	// 	NIL,
	// 	CONS,
	// 	CAR,
	// 	CDR,
	// 	ATOM,
	// 	EQ,
	// 	NULL,
	// 	INT,
	// 	QUOTIENT,
	// 	TIMES,
	// 	REMAINDER,
	// 	LESS,
	// 	GREATER,
	// 	COND,
	// 	QUOTE,
	// 	DEFUN
	// }
	
	/**
	 * Function: T
	 * 
	 * Creates a new atom representing the "true" value
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @return The T Atom
	 *
	 */
	public static TreeNode T(){ 
		return new Atom(true); 
	};
	 
	/**
	 * Function: NIL
	 * 
	 * Creates an atom to represent the NIL value
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @return The NIL Atom
	 *
	 */
	public static TreeNode NIL(){ 
		return new Atom(false); 
	};
	 
	/**
	 * Function: CONS
	 * 
	 * Carries out the CONS operation as defined by the Lisp
	 * operational semantics. It combines the CAR and CADR of
	 * the argument list into a single list. If the arguments
	 * fail for these operations, an Exception will be thrown
	 * in the SExpression constructor.
 	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The SExpression arguments in dot-notation
	 * 
	 * @return CONS[ CAR[s], CADR[s] ] - the semantically defined CONS
	 * 
	 * @throws Exception if the arguments are inappropriate
	 *
	 */
	public static SExpression CONS ( SExpression s ) throws Exception {
		SExpression tmp = new SExpression(s.dataTokens);
		return new SExpression(s.address.evaluate(), tmp.address.evaluate());
	}

	/**
	 * Function: CAR
	 * 
	 * returns the car of the given S-Expression as defined by the 
	 * operational semantics.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The argument S-Expression
	 * 
	 * @return The address of the given S-Exp
	 * 
	 * @throws Exception If the S-Expression is incompatible
	 *
	 */
	public static TreeNode CAR ( SExpression s ) throws Exception{
		return s.address;
	}
	
	/**
	 * Function: CDR
	 * 
	 * Returns the data of the given S-Expression
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s An S-Expression
	 * 
	 * @return The data of the S-Expression
	 *
	 */
	public static TreeNode CDR ( SExpression s )  throws Exception{
		return s.data;
	}
	 
	/**
	 * Function: ATOM
	 * 
	 * Determines if an S-Expression is semantically an Atom
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The S-Expression in question
	 * 
	 * @return True if it is an atom literal. False otherwise.
	 *
	 */
	public static TreeNode ATOM ( SExpression s ) throws Exception{
		return TreeNode.create(s.address.evaluate().toString().matches(Patterns.LITERAL));
	}
	 
	/**
	 * Function: EQ
	 * 
	 * Determines if the value of two S-Expressions are equal
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The paramenter S-Expression
	 * 
	 * @return T or NIL whether or not they are the same
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode EQ ( SExpression s ) throws Exception{
		return TreeNode.create(s.address.evaluate(true).toString().matches(s.data.evaluate(true).toString()));
	}
	 
	/**
	 * Function: NULL
	 * 
	 * Determines if the S-Expression is NIL
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The S-Expression in question
	 * 
	 * @return T or NIL whether or not the S-Expression is NIL
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode NULL ( SExpression s ) throws Exception{
		return TreeNode.create(s.data.evaluate().toString().matches("NIL"));
	}
	 
	/**
	 * Function: INT
	 * 
	 * Determines if an S-Expression is an integer
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param s An S-Expression
	 *
	 * @return T or NIL whether or not it is an integer
	 *
	 */
	public static TreeNode INT ( SExpression s ) throws Exception{
		return TreeNode.create(s.address.evaluate(true).toString().matches(Patterns.NUMERIC_ATOM));
	}
	 
	/**
	 * Function: PLUS
	 * 
	 * Adds two numbers
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression
	 * 
	 * @return The sum of the two elements in the given list (dot-notation form)
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode PLUS ( SExpression s ) throws Exception{
		return TreeNode.create(Integer.parseInt(s.address.evaluate(true).toString()) + Integer.parseInt(s.data.evaluate(true).toString()));
	}

	/**
	 * Function: MINUS
	 * 
	 * Subtracts two numbers
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression list in dot-notation
	 * 
	 * @return The difference of the two paramenters as an atom
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode MINUS ( SExpression s ) throws Exception{
		return TreeNode.create(Integer.parseInt(s.address.evaluate(true).toString()) - Integer.parseInt(s.data.evaluate(true).toString()));
	}

	/**
	 * Function: QUOTIENT
	 * 
	 * Divides two numbers with integer division
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression
	 * 
	 * @return The quotient of the two paramenters given by the S-Expression
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode QUOTIENT ( SExpression s ) throws Exception{
		return TreeNode.create(Integer.parseInt(s.address.evaluate(true).toString()) / Integer.parseInt(s.data.evaluate(true).toString()));
	}

	/**
	 * Funcntion: TIMES
	 * 
	 * Computes the product of two numbers
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression
	 * 
	 * @return The product of the two parameters as derived from the S-Expression
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode TIMES ( SExpression s ) throws Exception{
		return TreeNode.create(Integer.parseInt(s.address.evaluate(true).toString()) * Integer.parseInt(s.data.evaluate(true).toString()));
	}

	/**
	 * Function: REMAINDER
	 * 
	 * Returns the r term in the integer division of x by y as
	 * given by x = y * q + r
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression
	 * 
	 * @return The remainder after division as derived from the S-Expression
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode REMAINDER ( SExpression s ) throws Exception{
		return TreeNode.create(Integer.parseInt(s.address.evaluate(true).toString()) % Integer.parseInt(s.data.evaluate(true).toString()));
	}

	/**
	 * Function: LESS
	 * 
	 * Compares two objects and computes the strict 'less than' operator
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression
	 * 
	 * @return the boolean answer to the 'less than' operation
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode LESS ( SExpression s ) throws Exception{
		return TreeNode.create(Integer.parseInt(s.address.evaluate(true).toString()) < Integer.parseInt(s.data.evaluate(true).toString()));
	}
	
	/**
	 * Function: GREATER
	 * 
	 * Computer the 'greater than' operator using the arguments
	 * found in the paramter S-Expression
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression
	 * 
	 * @return The boolean answer to the 'greater than' operator
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode GREATER ( SExpression s ) throws Exception{
		return TreeNode.create(Integer.parseInt(s.address.evaluate(true).toString()) > Integer.parseInt(s.data.evaluate(true).toString()));
	}
	 
	/**
	 * Function: COND
	 * 
	 * As per the operational semantics of Lisp, takes an S-Expression
	 * which represents a list of conditions. It evaluates them until
	 * one's CAR evaluates to T and then returns the second item.
	 * 
	 * This roughly approximates the evcon function in the operational semantics
	 * where the error condition is taken care of by the SExpression 
	 * constructor.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The S-Expression describing the conditions
	 * 
	 * @return The result of evaluating the expression in the list with the first boolean component
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode COND ( SExpression s ) throws Exception {
		SExpression a = new SExpression(s.addressTokens);
		if ( a.address.evaluate().toString().matches("T") ){
			SExpression tmp = new SExpression(a.dataTokens);
			return tmp.address.evaluate(true);
		} else {
			SExpression b = new SExpression(s.dataTokens);
			return COND(b);
		}
	}
	 
	/**
	 * Function: QUOTE
	 * 
	 * As per the operational semantics, returns the CADR of 
	 * the containing S-Expression. Note that in this context,
	 * the 'data' has been passed as an argument similar to 
	 * the other primitives, so we must only take the address
	 * portion and return it.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression
	 * 
	 * @return The address of s
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode QUOTE ( SExpression s ) throws Exception {
		return s.address;
	}
	 
	/**
	 * Function: DEFUN
	 * 
	 * This function takes a List denoting the name of a new function,
	 * its parameter list, and its body. It is then broken down and 
	 * entered into the environment, the representation of the operational
	 * 'd-list' and then simply returns an Atom of the name of the function.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s S-Expression containing all the necessary information
	 * 
	 * @return An Atom of the function name if the registration is successful
	 * 
	 * @throws Exception If the S-Expression is malformed
	 *
	 */
	public static TreeNode DEFUN (SExpression s) throws Exception {
		String name = s.address.toString();

		if ( ! name.matches(Patterns.VALID_FUNCTION_NAME) ){
			throw new Exception("Error! Function names must be character literals only");
		}

		if ( Primitives.primitiveExists(name) ){
			throw new Exception("Error! Cannot override a primitive function.");
		}

		SExpression d = new SExpression(s.dataTokens);
		TreeNode params = TreeNode.create(d.addressTokens);
		SExpression tmp = new SExpression(d.dataTokens);
		TreeNode body = TreeNode.create(tmp.addressTokens);

		Environment.registerFunction(name, params, body);

		return new Atom(name);
	}

	/**
	 * Function: primitiveExists
	 * 
	 * This uses reflection to check if a particular primitive is defined.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param name The name of the primitive in question
	 * 
	 * @return true or false depending on whether or not the primitive is defined
	 *
	 */
	private static boolean primitiveExists(String name){
		java.lang.reflect.Method m;
		try{
			m = Primitives.class.getDeclaredMethod(name, SExpression.class);
			return true;
		} catch (Exception e){
			return false;
		}
	}
}