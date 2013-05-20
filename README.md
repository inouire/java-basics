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

You can use the whole library, or just pick the class that you need and copy it in your own project.

**Please keep in mind that the classes from java-basics are made to be reliable and really simple to use.**
So if you feel that they don't provide you enough power or flexibility, you should look at more complete libraries from the java world, or send a pull request if you have a way to do what you want that keep the usage really simple.

But maybe what they do will cover your needs :dart: as they cover mine ? 

The next sections present some examples to show how to use it. If you want to modify or contribute to java-basics, jump to the last section of this README.

## Args

The Args class allows you to get options values from the command line arguments.
You define the name of the flag for this option, and the option value will be taken from the argument *after* this flag (if it exists...)
The options can be of boolean, integer, or string type.

```java
//get a boolean value
boolean is_dev_mode = Args.getOption("--dev", args);

//get an integer value, with a default value if the flag is not found
int server_port = Args.getIntegerOption("--port", args, 22);

//get a string value, with a default value if the flag is not found
String player_name = Args.getStringOption("--name", args, System.getProperty("user.name"));
```

It is also possible to define a list of flags for the same option:

```java
String[] flags = new String[]{"--help", "-h", "help"}

//true if any of the flags above is present in the arguments
boolean display_help = Args.getOption(flags, args);
```

The Args class can also be used through an object. The behavior is similar to the static methods.
```java
Args decoder = new Args(args);
boolean help = decoder.getOption("-h");
int port = decoder.getIntegerOption("-p", 22);
String name = decoder.getStringOption("-o","/dev/null")
```

## MyMl

*TODO*

## TxtFileLoader

*TODO*

## ZipAssistant

*TODO*

## Modify / contribute

Clone java-basics git repository from github
``` bash
git clone https://github.com/inouire/java-basics.git
```

Build the project (you will need ant to do this)
``` bash
cd java-basics
ant default
```

This will test the library, build the jar and generate the javadoc.

If the build succeed, you will find the library jar at `dist/java-basics.jar`
The standalone version, which includes log4j dependency, is at `dist/java-basics-standalone.jar` 
The javadoc is in the `javadoc` directory.
