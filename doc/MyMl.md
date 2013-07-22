## MyMl

The MyMl class provides a way to parse tree-structured config files with a format inspired from Yaml. The format *is not* Yaml, but looks like this:
```YAML
#my_conf.txt
database:
    host: localhost
    port: 1234
    name: test_db
app:
    log:
        file: /var/log/app.log
        level: warning
        rotate: 5M
```
Each value is reachable by its absolute key, with the dot as a separator. The indentation must be of 4 spaces.

### Parsing

Here is how to use it:
```java
MyMl config = MyMl.loadFile("my_conf.txt");
String db_host  = MyMl.getValue("database.host"); // = localhost;
String log_file = MyMl.getValue("app.log.file"); // = /var/log/app.log;
```
(if the specified key cannot be found, a `MyMlException` will be raised)

The list of the keys contained in the structure can be exported:
```java
ArrayList<String> keys = config.getAbsoluteKeyList();
```
The `toString()` method will return a correctly indented String representation of the MyMl object.
 
### Validation
 
A MyMl file can be validated against some constraints with the MyMlValidator class.
By default a config file will be valid if each key specified in the constraints has a value, and that this value has the good type.
However you can tell the validator to look for the existence of mandatory keys only, or to check that the values of the structure to validate have the good type.
Available types are `int`, `bool`, and `string`.
```java
MyMlValidator validator = new MyMlValidator();
validator.useKeyValidationOnly()
         .setValidationKeys(new String[] { "database.host", "app.log.file"});
try{
    validator.validate(config);
}catch(MyMlException mex){
    //structure is invalid
}
```

You can also validate without raising any exception with validateQuiet. It will juste return true if the structure is valid, false if not.
```java
MyMlValidator validator = new MyMlValidator();
validator.setValidationPattern(validation_myml);
boolean valid = validator.validateQuiet(config);
```
