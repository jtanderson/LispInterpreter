package lexer;

/**
 * File: LexerPatters
 * 
 * Seperates out some regular expressions which represent the meaningful and
 * acceptable strings in a Lisp program
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 */

class LexerPatterns{
	public static final String LETTER = "[a-zA-Z]";
	public static final String LITERAL = "[a-zA-Z0-9]+";
	public static final String WHIESPACE = "[\\s]+";
	public static final String NUMERIC_ATOM = "[\\d\\+\\-]?[\\d]*";
	public static final String SYMBOL = "[().]";
}