package helpers;

import java.lang.String;
import java.util.List;

public class StringHelpers{
	public static String join(List s, String glue){
		String newString = "";
		for ( int i = 0; i < s.size() - 1; i++ ){
			newString = newString + s.get(i).toString() + glue;
		}
		return newString + s.get(s.size() - 1).toString();
	}
}