#########################################################################

# Makefile author: Joseph Anderson <jtanderson@ratiocaeli.com>

# This is where you specify the necessary source files

# Program packages and files
#	- The packages should be the path inside your src directory. eg: package1 package2/package3
#	- The classes are at the top level of the src directory. eg: MyClass.java Test.java
PACKAGES = helpers lexer parser 
#JAVA_FILES = LispInterpreter.java Parser.java SExpression.java

###################### DO NOT EDIT BELOW THIS LINE #######################

# Java compiler
JAVAC = javac
JVM = 1.6

# Directory for compiled binaries
BIN = ./bin/

# Directory of source files
SRC = ./src/

# Java compiler flags
JAVAFLAGS = -g -d $(BIN) -cp $(SRC) -target $(JVM)

# Creating a .class file
COMPILE = $(JAVAC) $(JAVAFLAGS)

EMPTY = 

JAVA_FILES = $(subst $(SRC), $(EMPTY), $(wildcard $(SRC)*.java))

ifdef PACKAGES
PACKAGEDIRS = $(addprefix $(SRC), $(PACKAGES))
PACKAGEFILES = $(subst $(SRC), $(EMPTY), $(foreach DIR, $(PACKAGEDIRS), $(wildcard $(DIR)/*.java)))
ALL_FILES = $(PACKAGEFILES) $(JAVA_FILES)
else
#ALL_FILES = $(wildcard $(SRC).java)
ALL_FILES = $(JAVA_FILES)
endif

# One of these should be the "main" class listed in Runfile
# CLASS_FILES = $(subst $(SRC), $(BIN), $(ALL_FILES:.java=.class))
CLASS_FILES = $(ALL_FILES:.java=.class)

# The first target is the one that is executed when you invoke
# "make". 

#all : $(subst $(SRC), $(BIN), $(CLASS_FILES))
all : $(addprefix $(BIN), $(CLASS_FILES))

# The line describing the action starts with <TAB>
$(BIN)%.class : $(SRC)%.java
	$(COMPILE) $<

clean :
	rm -rf $(BIN)*
