java-basics
===========

Java basics is a collection of simple tools for java.

Here is the things these tools will allow you to do,:
- parse command line arguments
- load information from files with a "Yaml like" tree structure
- validate the content of such a tree structure
- load the lines of a "txt" file into an array
- zip the wole content of a folder
- use log4j without any xml config files

You can use the whole library, or just pick the class that you need and copy it in your own project.

**Please keep in mind that the classes from java-basics are really made to be reliable and simple to use.**
If you feel that these classes don't provide you enough power, maybe you should look at more complete libraries from the java world.
But maybe what they do will cover your needs as they cover mine. :dart:

The next section present some examples to show how to use it. If you want to modify or contribute to java-basics, jump to the last section of this README.

## Examples

### Args

*TODO*

### MyMl

*TODO*

### TxtFileLoader

*TODO*

### ZipAssistant

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
