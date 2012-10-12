package parser;

import java.util.*;
import java.lang.*;
import helpers.StringHelpers;

class ParseTree{
	private TreeNode root;

	public ParseTree(Vector <String> s){
		if ( s.get(0) == "(" ){
			root = new SExpression(s);
		} else {
			root = new Atom(StringHelpers.join(s, " "));
		}
	}

	protected String print(){
		String result = root.toString();
		return result;
	}
}