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

The Args class can also be used through an object. The behavior is similar to the static methods, but you won't need to specify args array at each call.
```java
Args decoder = new Args(args);
boolean help = decoder.getOption("-h");
int port = decoder.getIntegerOption("-p", 22);
String name = decoder.getStringOption("-o","/dev/null")
```
