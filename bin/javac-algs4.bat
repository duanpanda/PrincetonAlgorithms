@echo off

:: ***************************************************
:: javac-algs4
:: ------------------
:: Wrapper for javac that includes stdlib.jar, and
:: algs4.jar (including stdlib and selected exercise
:: code)
:: ***************************************************

set INSTALLDIR=%USERPROFILE%\code\PrincetonAlgorithms

"%JAVA_HOME%"\bin\javac -cp ".;%INSTALLDIR%\stdlib.jar;%INSTALLDIR%\algs4.jar" -g -encoding UTF-8 -Xlint:all %*