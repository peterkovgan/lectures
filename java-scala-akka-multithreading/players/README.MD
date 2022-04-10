1.
To run any script, rename it, correct JAVA_HOME and MAVEN_HOME where necessary
Note, that to run build you need JDK , not JRE

2.
To run build:
use build script
The build artifact will be found in the target directory
name: players-1.0-SNAPSHOT-jar-with-dependencies.jar

3.
To run on single VM, use startBoth script

4.
To run on separate VMs (only 'localhost' is supported), use startClient and startServer scripts