package parser;

/**
 * File: Patterns.java
 * 
 * This file holds some useful Regular Expressions
 * used in validating symbols and literals in Lisp
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 *
 */

class Patterns{
	protected static final String LETTER = "[a-zA-Z]";
	protected static final String LITERAL = "[a-zA-Z0-9]+";
	protected static final String VALID_FUNCTION_NAME = "[a-zA-Z][a-zA-Z0-9]*";
	protected static final String WHIESPACE = "[\\s]+";
	protected static final String NUMERIC_ATOM = "[\\d\\+\\-]?[\\d]*";
	protected static final String SYMBOL = "[().]";
}