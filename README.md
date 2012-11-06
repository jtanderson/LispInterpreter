# Joe Anderson

Running and compiling the interpreter
======================================

* 	The project comes with a custom Makefile to compiile the project to the version of Java present on the current system.  The makefile can be run 	from the directory of the project with the command: `make`

* 	The binary files can be quickly removed to re-build the project with `make clean`

* 	The project uses several custom packages, so when invoking the project with `java` the classpath must be set to the top-most directory of the 		binary files

* 	A generic run command can look like this: java -cp ./bin LispInterpreter < file1 > file2

	* This will run the lisp interpreter taking the data from file1 as input and direct all output to file2

	* This example can be found in Runfile


Design information
==================

The Lisp interpreter has two main packages: *Lexer* and *Parser*. These fill the standard interpreter roles of lexical analysis and parsing respectively.  

Lexer
-----

This contains the functionality to split the input into meaningful "chunks" according to the Lisp semantics. For instance, literal words (PLUS, MINUS, T, NIL, etc.) are taken as single tokens while parentheses and dots are individual tokens. For numeric literals, the sign is kept with the literal (+1, -5, etc.) in the token list. This list of tokens is then made available via the `getTokens()` method.

Parser
------

The parser is what does most of the "work" of the interpreter. It has several parts:

*	Converting the input program to dot-notation
*	Creating the parse tree from the program tokens
*	Call the evaluation on the parse tree in a top-down fashion
*	Handles the S-Expression data structure
*	Maintains an environment hash for defined functions and variables during function calls
*	Handles any errors raised by S-Expression evaluation or other parsing operations

### ParseTree

The parse tree is a standard tree model where each node is a `TreeNode`. This abstract class represents either an S-Expression or an Atom and defines any common behavior. It also employs the factory design pattern so that one may call `TreeNode.create(fromData)` and it will return either an `Atom` or `SExpression` based on the content of the passed data.

### Atoms

These represent the standard literals of the language: variables, function names, numerics, etc. When "evaluated" by the program, they either return themselves, or in the case of a variable, its value in the current environment. They have only one attribute - a string of the literal which is represented.

### S-Expressions

S-Expressions are represented in the interpreter by the `SExpression` class. This class has two fields to reflect the structure of a Lisp S-Expression: address and data.  These fields are `TreeNode` objects and thus enabling the S-Expressions to be recursive in nature. 

Evaluation also happens recursively: as per the operational semantics, the CDR is passed to any primitive functions and they operate from there. So if, in the operational semantics, CADR is used, they just take the CAR of their input.  The `evaluate` method of the `SExpression` is set up to use one main version of the function but allowing essentially any combination of the parameters (including none) and passes defaults when none are given. This is inconsequential to the actual running of the interpreter, but provides compatibility for evaluation that takes no regard for either literal interpretation, environment variables, or both.

### User-Defined Functions

When a call is made to DEFUN, the appropriate parts of the S-Expression are broken apart and used to define a new `UserFunction` object and bind it to the current environment. This is accomplished via an `Environment` class which has a static hash table for functions and a static hashtable for variables. The variable hash table is used to keep track of bindings made when calling a user-defined function.  The function body is evaluated and passed a hash of the bindings to use. The S-Expression `evaluate` function merges the new bindings into the current variable bindings and when finished, restores the previous state of the environment. This is to prevent "upward" binding of variables where a variable bound later in a program can be used by a function defined earlier.

### Other notable components

#### Patterns

The parser uses a class of static regular expressions common to identifying legal function names, variable names, etc.  They are kept in the `Patterns` class and are static and public to the entire `Parser` package.

#### Program Evaluation

The parser takes the input of tokens and processes it statement-by-statement. If one of them errors, program termination is halted an no further statements are executed. This is mainly to avoid errors if a later statement requires something defined by one that failed.

#### Debug Mode

In the event of errors, if one wants to see a stack trace of the error, the program can be run with the `-d` flag set: `java -cp ./bin LispInterpreter -d < infile` and the stack trace of any error will be sent to `stdout`
