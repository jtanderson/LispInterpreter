package parser;
import java.util.*;

/**
 * File: Environment.java
 * 
 * This houses the so-called "d-list" of the program. It mangages
 * the binding of functions and variables within the context of the
 * running Lisp program.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-01
 * @version 2012-11-01
 *
 */
class Environment{
	public static java.util.Hashtable <String, UserFunction> funcs = new Hashtable <String, UserFunction>();
	public static java.util.Hashtable <String, TreeNode> vars = new Hashtable <String, TreeNode>();

	/**
	 * Function: executeFunction
	 * 
	 * Executes a requested function with the actual parameters
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param name The name of the function
	 * @param params An SExpression or Atom to be used as actual parameter
	 * 
	 * @return The TreeNode (S-Expression or Atom) which is the result of evaluation
	 * 
	 * @throws Exception If the requested function is undefined
	 *
	 */
	public static TreeNode executeFunction(String name, TreeNode params) throws Exception{
		if ( !funcs.containsKey(name) ){
			throw new Exception("Error! Undefined function: " + name);
		}

		UserFunction f = funcs.get(name);
		return f.evaluate(params);
	}

	/**
	 * Function: registerFunction
	 * 
	 * This defines a function in the "d-list"
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param name The string name of the function
	 * @param params The formal parameter list of the function
	 * @param body The literal or SExpression representing the body of the function
	 * 
	 * @throws Exception If the function definition is illegal
	 *
	 */
	public static void registerFunction(String name, TreeNode params, TreeNode body) throws Exception{
		UserFunction f = new UserFunction(name, params, body);
		funcs.put(name, f);
	}

	/**
	 * Function: functionIsDefined
	 * 
	 * Detects if a function is in the current scope
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param name The string name of the function
	 * 
	 * @return True if the function is defined. False if not.
	 *
	 */
	public static boolean functionIsDefined(String name){
		return funcs.containsKey(name);
	}

	/**
	 * Function: print
	 * 
	 * Sends the variable hashtable stringified to stdout
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 *
	 */
	public static void print(){
		System.out.println(vars.toString());
	}

	/**
	 * Function: stringify
	 * 
	 * Returns the string version of the variable hashtable.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @return The stringified hashtable of bound variables
	 *
	 * @deprecated Only for early debugging
	 *
	 */
	public static String stringify(){
		return vars.toString();
	}

	/**
	 * Function: varIsDefined
	 * 
	 * Detects if a variable is defined in the current scope
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 * 
	 * @param name The string name of the requested variable
	 *
	 * @return True if the variable is bound. False if not.
	 *
	 */
	public static boolean varIsDefined(String name){
		return vars.containsKey(name);
	}

	/**
	 * Function: unbindAll
	 * 
	 * Used to remove variables from the environment
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * details
	 *
	 */
	public static void unbindAll(Hashtable <String, TreeNode> tbl){
		Iterator it = tbl.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        if ( vars.get(pairs.getKey()) == pairs.getValue() ){
	        	vars.remove(pairs.getKey());
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	/**
	 * Function: unbind
	 * 
	 * Unbinds a specific variable from the envrionment
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param name String name of the variable
	 *
	 */
	public static void unbind(String name){
		vars.remove(name);
	}
	
	/**
	 * Function: getVarValue
	 * 
	 * Finds a variable and returns it's value if it exists
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-01
	 * @version 2012-11-01
	 *
	 * @param name	The name of the variable
	 * 
	 * @return The TreeNode value of the variable
	 * 
	 * @throws Exception If the variable is not bound
	 *
	 */
	public static TreeNode getVarValue(String name) throws Exception{
		if ( vars.containsKey(name) ){
			return vars.get(name);
		} else {
			throw new Exception("Error! No such variable.");
		}
	}
	
	/**
	 * Function: mergeVars
	 * 
	 * This substitutes in new variable bindings to the current environment
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param newVars A Hashtable of the new variable bindings
	 *
	 */
	public static void mergeVars(Hashtable <String, TreeNode> newVars){
		Iterator it = newVars.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
			if ( vars.contains(pairs.getKey()) ){ // Do not let it store multiple things in one bucket
				vars.remove(pairs.getKey());
			}
			vars.put( (String) pairs.getKey(), (TreeNode) pairs.getValue() );
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	/**
	 * Function: getVarTable
	 * 
	 * Returns a copy of the current environment bindings. Uses a
	 * copy to avoid accidental corruption.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @return A copy of the current environment bindings
	 */
	public static Hashtable <String, TreeNode> getVarTable(){
		return new Hashtable <String, TreeNode> (vars);
	}
	
	/**
	 * Function: setVars
	 * 
	 * This takes a table of bindings and swaps it into the current
	 * variable table.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param v The new Hashtable of variable bindings
	 *
	 */
	public static void setVars(Hashtable <String, TreeNode> v){
		vars = new Hashtable <String, TreeNode> (v);
	}
}