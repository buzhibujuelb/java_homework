@echo off
dir /s /b *.java > list.txt
javac "@list.txt" -d bin -Xlint:unchecked
