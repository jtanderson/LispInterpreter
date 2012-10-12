package parser;

import java.util.*;
import helpers.*;

abstract class TreeNode{
	static TreeNode create(Vector <String> s){
		if ( s.size() > 0 && s.get(0) == "(" ){
			return new SExpression(s);
		} else if ( s.size() > 0 ){
			return new Atom(s.get(0));
		} else {
			System.out.println("Tried to create a TreeNode with no data");
			return new Atom("");	
		}
	}

	public String toString;
}