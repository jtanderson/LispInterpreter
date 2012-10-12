package parser;

import java.lang.*;

class Atom extends TreeNode {
	private String literalString;

	public Atom(String s){
		literalString = s;
	}

	public String toString(){
		return literalString;
	}
}