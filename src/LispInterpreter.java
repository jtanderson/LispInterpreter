import lexer.*;
import parser.*;

class LispInterpreter { 
    public static void main(String[] args) {
    	try{
	    	Lexer l = new Lexer(System.in);
	    	Parser p = new Parser(l.getTokens());
	    	System.out.println(p.printParseTree())	;
    	} catch (Exception e){
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		System.exit(3);
    	}
    }
}