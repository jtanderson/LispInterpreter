import lexer.*;
import parser.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * File: LispInterpreter.java
 * 
 * This is the driver file for the Lisp Interpreter project
 * 
 * It takes input from stdin and evaluates it line by line.
 * One should note that if muliple statements are on the same line,
 * their results will not be output until all have executed
 * successfully.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 */

class LispInterpreter { 
	
	/**
	 * Function: main
	 * 
	 * The main function of the program. 
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param args Any command line arguments. None are used as of this version.
	 */
	
    public static void main(String[] args) {
    	try{
	    	Lexer l = new Lexer(System.in);
	    	Parser p = new Parser(l.getTokens());
	    	// System.out.println(p.printParseTree());
			p.evaluate();
		} catch (IOException e){
			System.out.println("End of input...");
    	} catch (Exception e){	
			System.out.println("Error!");
			if ( args.length > 0 && args[0].matches("-d") ){
    			System.out.println(e.getMessage());
	    		e.printStackTrace();
			}
    		System.exit(3);
    	}
    }
}