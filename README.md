# jstrip
jstrip is a CLI tool which hooks into your application and detects which classes that are not needed and removes them from the libraries for smaller file-size


### Usage

java -jar jstrip.jar [-in input.jar] [-main main.Class] <-l library1.jar;library2.jar> [-o output.jar] <-stripin>
- -in - File to be executed
- -main - Main class to invoke
- -l - Libraries to load, separated with ; (Optional)
- -o - Output directory, files saved like dir/name.jar
- -stripin - Also strips input file

### How it works

Invokes the main method in the main class specified, logs all loaded classes, and when completed, will rewrite all libraries and only include classes that has been loaded (and all resources)