java-basics
===========

Java basics is a collection of simple tools for java.

Here are some things that it will help you to do:
- `Args`: parse command line arguments
- `MyMl`: load information from files with a "Yaml like" tree structure
- `MyMlValidator`: validate the content of such a tree structure
- `TxtFileLoader`: load the lines of a "txt" file into an array
- `ZipAssistant`: zip the wole content of a folder
- `SimpleLog`: use log4j without any xml config files
- `JarContentExtractor`: extract files from application jar

You can use the whole library, or just pick the class that you need and copy it in your own project.

**Please keep in mind that the classes from java-basics are made to be really simple to use.**
So if you feel that they don't provide you enough power or flexibility, you should look at more complete libraries from the java world, or send a pull request if you have a way to do what you want that keep the usage really simple.

The next sections present quick examples to show how to use it. For more information, see the dedicated documentation of each class.

If you want to modify or contribute to java-basics, jump to the last section of this README.

# Overview

## Args

The Args class allows you to get options values from the command line arguments. See [full documentation](./doc/Args.md).

```java
String[] flags = new String[]{"--help", "-h", "help"}
boolean display_help = Args.getOption(flags, args);
```

## MyMl

The MyMl class provides a way to parse tree-structured config files. See [full documentation](./doc/MyMl.md).

```YAML
#my_conf.txt
database:
    host: localhost
    port: 1234
    auth:
        username: john
        password: doe
```

```java
MyMl config = MyMl.loadFile("my_conf.txt");
String db_host  = MyMl.getValue("database.host"); // = localhost
String db_port = MyMl.getValue("database.port"); // = 1234
String db_user = MyMl.getValue("database.auth.username"); // = john
```

## SimpleLog

The SimpleLog class allows you to initiate a log4j logger without any configuration file.
Several modes are available:
- consoleConfig which has only a console appender
- devConfig with both a console and a file appender to application.log
- prodConfig which has only a file appender to a custom file name

```java
SimpleLog.initConsoleConfig();
SimpleLog.logger.info("Loading config"); //will be logged to console only
[...]
SimpleLog.initProdConfig("/var/log/myapp.log");
SimpleLog.logger.info("Starting myapp");//will be logged to myapp.log only
```

## TxtFileLoader

The TxtFileLoader class provides a way to painlessly load the content of a 'txt' file into an array. 
Each line of the file is an element of the array, thus you can browse it peacefully as many time as you want, and forget about the InputStream and other BufferedReaders.
Several options are available in order to ignore/clean things from the input file.

```java
TxtFileLoader loader = new TxtFileLoader();
loader.ignoreEmptyLines(false)
      .trimLines(true)
      .trimComments(true)
      .setCommentPattern("//");
ArrayList<String> lines = loader.loadFile("my_file.txt");
```

## ZipAssistant

With the ZipAssistant class, you can create a zip file with the whole content of a folder of your disk. The directory structure will be kept inside the created zip.

*TODO*

## JarContentExtractor

The JarContentExtrator class allows you to extract to your disk any file contained in your application jar, just like that:
```java
JarContentExtractor.extractToFile("/my/package/default_conf.txt","conf.txt");
```

## Modify / contribute

Clone java-basics git repository from github and build the project with ant
``` bash
git clone https://github.com/inouire/java-basics.git
cd java-basics
ant
```

This will test the library, build the jar and generate the javadoc.

If the build succeed, you will find the library jar at `dist/java-basics.jar`
The standalone version, which includes log4j dependency, is at `dist/java-basics-standalone.jar` 
The javadoc is in the `javadoc` directory.
