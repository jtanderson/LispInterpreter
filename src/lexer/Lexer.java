package lexer;

import java.io.*;
import java.util.*;

public class Lexer{

	private String programString = "";
	private Vector <String> tokenArray = new Vector <String> ();

	public Lexer(InputStream stream) throws IOException{
		byte[] bytes = new byte[1024];
		while (stream.available() > 0){
	        stream.read(bytes, 0, 1024);
	        programString = programString.concat(new String(bytes)).trim().toUpperCase();
        }
        tokenArray = tokenize(programString);
	}

	public Lexer(String s){
		programString = s;
		tokenArray = tokenize(programString);
	}

	public Vector <String> getTokens(){
		return convertToDotNotation(tokenArray);
	}

	private Vector <String> tokenize(String s){
		int i = 0;
		Vector <String> tokens = new Vector <String>();
		if ( s.length() == 1 ){
			tokens.add(s);
			return tokens;
		}
		while (  i < s.length() ){
			int j = i + 1;
			if ( s.substring(i, j).matches(LexerPatterns.LETTER) || s.substring(i, j).matches(LexerPatterns.NUMERIC_ATOM) ){
				while ( s.substring(i,j + 1).matches(LexerPatterns.LITERAL) || s.substring(i, j + 1).matches(LexerPatterns.NUMERIC_ATOM) ){
					j++;
				}
				tokens.add(s.substring(i,j));
			} else if ( s.substring(i, j).matches(LexerPatterns.SYMBOL) ){
				tokens.add(s.substring(i,j));
			}
			i = j;
		}
		return tokens;
	}

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

	public int endOfExpression(Vector <String> s) throws IllegalArgumentException, ArrayIndexOutOfBoundsException{
		if ( ! s.get(0).matches("[(]") ){
			throw new IllegalArgumentException("ERROR: Tried to find the end of an expression that did not begin with '('.");
		}
		int openPairs = 1;
		int end = 1;
		while ( openPairs > 0 ){
			if ( end >= s.size() ){
				throw new ArrayIndexOutOfBoundsException("Error: Unbalanced parentheses");
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