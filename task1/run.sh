#!/bin/bash
javac -sourcepath src/ -d bin/ -classpath "bin/:/usr/share/java/*" src/itc313_ass1/*.java && java -classpath "bin/:/usr/share/java/*" $1
