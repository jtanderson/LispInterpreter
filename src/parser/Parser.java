package parser;

import java.util.*;
import java.lang.*;
import helpers.StringHelpers;

public class Parser {

	ParseTree parseTree;

	public Parser(Vector <String> tokens){
		parseTree = new ParseTree(tokens);
	}

	public String printParseTree(){
		return parseTree.print();
	}
}