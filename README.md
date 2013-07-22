java-basics
===========

Java basics is a collection of simple tools for java.

Here are some things that it will allow you to do:
- parse command line arguments
- load information from files with a "Yaml like" tree structure
- validate the content of such a tree structure
- load the lines of a "txt" file into an array
- zip the wole content of a folder
- use log4j without any xml config files
- extract files from application jar

You can use the whole library, or just pick the class that you need and copy it in your own project.

**Please keep in mind that the classes from java-basics are made to be reliable and really simple to use.**
So if you feel that they don't provide you enough power or flexibility, you should look at more complete libraries from the java world, or send a pull request if you have a way to do what you want that keep the usage really simple.

The next sections present quick examples to show how to use it. For more information, see the dedicated documentation of each class

If you want to modify or contribute to java-basics, jump to the last section of this README.

# Overview

## Args

The Args class allows you to get options values from the command line arguments.
You define the name of the flag for this option, and the option value will be taken from the argument *after* this flag (if it exists...)
The options can be of boolean, integer, or string type.

```java
String[] flags = new String[]{"--help", "-h", "help"}
boolean display_help = Args.getOption(flags, args);
```

## MyMl

The MyMl class provides a way to parse tree-structured config files:
```YAML
#my_conf.txt
database:
    host: localhost
    port: 1234
    name: test_db
```

```java
MyMl config = MyMl.loadFile("my_conf.txt");
String db_host  = MyMl.getValue("database.host"); // = localhost
String db_port = MyMl.getValue("database.port"); // = 1234
```

## TxtFileLoader

*TODO*

## ZipAssistant

*TODO*

## Modify / contribute

Clone java-basics git repository from github and build the project with ant
``` bash
git clone https://github.com/inouire/java-basics.git
cd java-basics
ant default
```

This will test the library, build the jar and generate the javadoc.

If the build succeed, you will find the library jar at `dist/java-basics.jar`
The standalone version, which includes log4j dependency, is at `dist/java-basics-standalone.jar` 
The javadoc is in the `javadoc` directory.
