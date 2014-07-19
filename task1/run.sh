#!/bin/bash
javac -sourcepath src/ -d bin/ -classpath bin/ src/itc313_ass1/*.java && java -classpath bin/ $1
