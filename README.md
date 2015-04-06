# jstrip
jstrip is a CLI tool which hooks into your application and detects which classes that are not needed and removes them from the libraries for smaller file-size


### Usage

```
java -jar jstrip.jar [-in input.jar] [-main main.Class] <-l library1.jar;library2.jar> [-o output.jar] <-stripin> <-resources>
```

- -in - File to be executed
- -main - Main class to invoke
- -l - Libraries to load, separated with ; (Optional)
- -o - Output directory, files saved like dir/name.jar
- -stripin - Also strips input file
- -resources - Detects which resources in the JAR is being used and only writes them to stripped archive

### How it works

Invokes the main method in the main class specified, logs all loaded classes, and when completed, will rewrite all libraries and only include classes that has been loaded (and resources that has been loaded if -resources is specified as argument)