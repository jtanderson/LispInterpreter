package parser;
import java.util.*;

/**
 * File: UserFunction.java
 * 
 * This is the data structure which represents user-defined
 * Lisp functions.
 * 
 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
 * @since 2012-11-02
 * @version 2012-11-02
 *
 */

class UserFunction{

	protected String name;
	protected Vector <String> formals;
	protected TreeNode body;

	/**
	 * Function: UserFunction
	 * 
	 * Constructor: UserFunction(String n, TreeNode f, TreeNode b)
	 * 
	 * This function creates a user-defined function with the specified
	 * name, list of formal parameters, and body.
	 * 
	 * @todo: allow the body to be a non-list
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param n The name of the function
	 * @param f The list of formals - can be ()
	 * @param b The body of the function
	 * 
	 * @throws Exception If the parameters are not in the correct format
	 *
	 */
	public UserFunction(String n, TreeNode f, TreeNode b) throws Exception{
		name = n;
		if ( (!f.isList() && !f.toString().matches("NIL") ) || ( !b.isList() && !b.toString().matches("NIL") ) ){
			throw new Exception("Invalid function parameters or body.\n" + f.toString() + "\n" + b.toString());
		}

		String formalString = f.toString();
		formals = splitParamList(formalString);
		body = b;
	}

	/**
	 * Function: evaluate
	 * 
	 * This carries out the basic evaluation of a custom function by
	 * invoking the body with the passed actual parameters.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param actuals The list (possible NIL) of actual parameters
	 * 
	 * @return The result of evaluating the body
	 * 
	 * @throws Exception If evaluation fails
	 *
	 */
	protected TreeNode evaluate(TreeNode actuals) throws Exception{
		Hashtable <String, TreeNode> bindings = bind(actuals);
		// Environment.vars.putAll(bindings);
		// Iterator it = bindings.entrySet().iterator();
		// int i = 0;
		// TreeNode tmp = TreeNode.create(body.tokens);
		// while ( it.hasNext() ){
		// 	Map.Entry pair = (Map.Entry) it.next();
		// 	tmp.replace(formals.get(i), pair.getValue().toString());
		// 	it.remove();
		// 	i++;
		// }
		return body.evaluate(true, bindings);
	}

	/**
	 * Function: splitParams
	 * 
	 * This is a private helper function to create a vector of parameters
	 * from a string. It also checks for distinct parameters and legal
	 * parameter names.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param s The string of parameters
	 * 
	 * @return A string vector of parameter names
	 * 
	 * @throws Exception If the parameters are malformed or inappropriate
	 *
	 */
	private static Vector <String> splitParamList(String s) throws Exception{
		String[] chunks = s.substring(1, s.length()-1).split("\\s");
		Vector <String> rtn = new Vector <String> ();
		for ( int i = 0; i < chunks.length; i++ ){
			if ( chunks[i].matches(Patterns.VALID_FUNCTION_NAME) ){
				if ( ! rtn.contains(chunks[i]) ){
					rtn.add(chunks[i]);
				} else {
					throw new Exception("Error! Formal parameter names must be distinct.");
				}
			} else {
				throw new Exception("Error! Invalid parameter name: " + chunks[i]);
			}
		}
		return rtn;
	}

	/**
	 * Function: bind
	 * 
	 * Creates a hashtable representing the bound values of 
	 * formal parameters to actual parameters.
	 * 
	 * Also provides some checking to make sure the number of
	 * actuals matches the number of formals.
	 * 
	 * @author Joseph T. Anderson <jtanderson@ratiocaeli.com>
	 * @since 2012-11-02
	 * @version 2012-11-02
	 *
	 * @param s The TreeNode object representing the actual paramters
	 * 
	 * @return A Hashtable of bindings
	 * 
	 * @throws Exception If the wrong number of parameters are used or they
	 * are malformed
	 *
	 */
	private Hashtable <String, TreeNode> bind (TreeNode s) throws Exception{
		if ( ! s.isList() && !s.toString().matches("NIL") ){
			throw new Exception("Error! Invalid parameters to function: " + name);
		}

		Hashtable <String, TreeNode> env = new Hashtable <String, TreeNode> ();

		if ( ! s.isList() ){
			return env;
		}

		SExpression tmp = new SExpression(s);
		int i;
		for ( i = 0; i < formals.size(); i++ ){
			env.put(formals.get(i), tmp.address.evaluate());
			try{
				tmp = new SExpression(tmp.dataTokens);
			} catch (Exception e){
				break;
			}
		}

		if ( i < formals.size() - 1 ){
			throw new Exception("Error! Too few arguments for: " + name);
		} else if ( ! tmp.data.evaluate().toString().matches("NIL") ){
			throw new Exception("Error! Too many arguments for: " + name);
		}

		return env;
	}
}