# jstrip
jstrip is a CLI tool which hooks into your application and detects which classes that are not needed and removes them from the libraries for smaller file-size


### Usage

```
java -jar jstrip.jar [-in input.jar] [-main main.Class] <-l library1.jar;library2.jar> [-o output.jar] <-stripin> <-resources>
```

- __-i | --input__ - File to be executed
- __-c | --mainclass__ - Main class to invoke
- __-cp | --classpath__ - Libraries to load, separated with ; (Optional)
- __-o | --out__ - Output directory, files saved like dir/name.jar
- __--stripin__ - Also strips input file
- __-r | --resources__ - Detects which resources in the JAR is being used and only writes them to stripped archive

### How it works

Invokes the main method in the main class specified, logs all loaded classes, and when completed, will rewrite all libraries and only include classes that has been loaded (and resources that has been loaded if -resources is specified as argument)