package parser;

import java.util.*;
import helpers.*;

class SExpression extends TreeNode{
	TreeNode address;
	TreeNode data;

	public SExpression(Vector <String> s){
		if ( s.get(0) == "(" ){ // some sanity checking for now
			int i = 1;
			int dataStart = 3;
			if ( s.get(i) == "(" ){
				int open = 1;
				while ( open > 0 ){
					i++;
					if ( s.get(i) == "(" ){
						open++;
					} else if ( s.get(i) == ")" ){
						open--;
					}
				}
				dataStart = i + 1;
			}
			i = dataStart > 3 ? s.indexOf(".", dataStart) : 2;
			address = TreeNode.create(new Vector <String>(s.subList(1,i)));
			data = TreeNode.create(new Vector <String>(s.subList(i+1, s.size())));
		}
	}

	public String toString(){
		return "( " + address.toString() + " . " + data.toString() + " )";
	}
}