package parser;

import java.util.*;
import java.lang.*;
import helpers.StringHelpers;

/**
 * File: Parser.java
 * 
 * This is the main Parser class for the program. It handles the
 * tokens from the lexical analysis, converts them to dot-notation,
 * and provides public access to program evaluation.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 *
 * details
 *
 */

public class Parser {

	Vector <ParseTree> statements = new Vector <ParseTree> ();
	
	/**
	 * Function: Parser
	 * 
	 * Constructor(Vector <String> tokens)
	 * 
	 * This function initializes the parser fro the lexical tokens,
	 * splitting them into statements, and storing them.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param tokens The vector of lexical tokens from analysis
	 * 
	 * @throws Exception If conversion to dot-notation fails
	 *
	 */
	public Parser(Vector <String> tokens) throws Exception{
		Vector <String> t;
		int i = 0;
		int k, l;

		while ( i < tokens.size() && i >= 0 ){
			// The variable l must always hold the last location of the current statement!!!
			k = tokens.indexOf("(", i);
			if ( k == i ){
				// There is a new statement to add - starting at i
				l = endOfExpression(new Vector <String> (tokens.subList(i, tokens.size()))) + i;
			} else if ( k > i ){
				// The next statement is after some non-parenthetical stuff
				l = k - 1;
			} else {
				// There are no more parenthetical statements. Parse the rest of the program
				l = tokens.size() - 1;
			}

			t = convertToDotNotation( new Vector <String> (tokens.subList(i, l+1)));
			statements.add(new ParseTree(t));

			i = l + 1;
		}
	}

	/**
	 * Function: evaluate
	 * 
	 * This evaluates the statements one-by-one and prints
	 * the results.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @throws Exception If any evaluation fails on individual statements
	 *
	 */
	public void evaluate() throws Exception{
		// String rtn = "";
		for ( int i = 0; i < statements.size(); i++ ){
			System.out.println(statements.get(i).evaluate());
			// rtn = rtn + statements.get(i).evaluate();
			// 			if ( i < statements.size() - 1){
			// 				rtn = rtn + "\n";
			// 			}
		}
		// return rtn;
	}

	/**
	 * Function: convertToDotNotation
	 * 
	 * This uses a naïve grammar to split the given program and rejoin
	 * it into legal Lisp S-Expressions.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The vector of tokens representing the program
	 * 
	 * @return The program converted to dot notaion in a string vector
	 *
	 */
	private Vector <String> convertToDotNotation(Vector <String> s){
		Vector <String> r = new Vector <String>();
		Vector <String> temp;
		int nextInnerToken;
		if ( s.get(0).matches("[(]") ){
			// We have a list or S-Expression

			int closeParen = endOfExpression(s);
			if ( closeParen > 1 ){
				// The expression is not ()

				r.add("(");
				if ( closeParen > 2 ){
					// There is more than one token - so not ( a )

					// Is the first one a nested expression?
					if ( s.get(1).matches("[(]") ){
						nextInnerToken = endOfExpression(new Vector <String>(s.subList(1, closeParen))) + 2;
					} else {
						nextInnerToken = 2;
					}

					if ( ! s.get(nextInnerToken).matches("[.]") ){
						// The expression must be a list because it is not in dot-notation
						r.addAll(convertToDotNotation(new Vector <String>(s.subList(1,nextInnerToken))));
						r.add(".");
						temp = new Vector <String>();
						temp.add("(");
						temp.addAll(s.subList(nextInnerToken, closeParen));
						temp.add(")");
						r.addAll(convertToDotNotation(temp));
					} else {
						// Since it is in the form of ( [stuff] . [stuff] ), we pass [stuff] to be converted
						r.addAll(convertToDotNotation(new Vector <String>(s.subList(1,nextInnerToken))));
						r.add(".");
						r.addAll(convertToDotNotation(new Vector <String>(s.subList(nextInnerToken+1, closeParen))));
					}
				} else {
					// The statement is in the form ( a )
					r.add(s.get(1));
					r.add(".");
					r.add("NIL");
				}
				r.add(")");
			} else {
				// We have ()
				r.add("NIL");
			}
		} else {
			if ( s.indexOf("(") > 0 ){
				r.addAll(s.subList(0, s.indexOf("(")));
				r.addAll(convertToDotNotation(new Vector <String>(s.subList(s.indexOf("("), s.size()))));
			} else {
				r = s;	
			}
		}
		return r;
	}
	
	/**
	 * Function: endOfExpression
	 * 
	 * This finds the closing parenthesis of a given Lisp segment
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param s The tokens (in dot-notation) of a segment of Lisp code
	 * 
	 * @return the index of the closing parenthesis
	 * 
	 * @throws IllegalArgumentException If the vector is not a parenthetical expression
	 * @throws ArrayIndexOutOfBoundsException If the statement does not have a closing parenthesis
	 *
	 */
	private int endOfExpression(Vector <String> s) throws IllegalArgumentException, ArrayIndexOutOfBoundsException{
		if ( ! s.get(0).matches("[(]") ){
			throw new IllegalArgumentException("ERROR: Tried to find the end of an expression that did not begin with '('.");
		}
		int openPairs = 1;
		int end = 1;
		while ( openPairs > 0 ){
			if ( end >= s.size() ){
				throw new ArrayIndexOutOfBoundsException("Error! Unbalanced parentheses.");
			}
			if ( s.get(end).matches("[)]") ){
				openPairs--;
			} else if ( s.get(end).matches("[(]") ){
				openPairs++;
			}
			if ( openPairs == 0 ){
				break;
			} else {
				end++;
			}
		}
		return end;
	}
}