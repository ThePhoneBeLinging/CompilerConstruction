antlrjar = antlr-4.13.2-complete.jar

###### FOR LINUX AND MAC -- comment the following line if you use Windows:
classpath = '$(antlrjar):.'

###### FOR WINDOWS -- uncomment the following line if you use Windows:
#classpath = '$(antlrjar);.'

antlr4 = java -cp $(classpath) org.antlr.v4.Tool
grun = java -cp $(classpath) org.antlr.v4.gui.TestRig
SRCFILES = main.java
GENERATED = ccListener.java ccBaseListener.java ccParser.java ccLexer.java ccBaseVisitor.java ccVisitor.java

1a = 01a-hello-world.txt
1b = 01b-hello-world-withdef.hw
02 = 02-trafiklys-minimal.hw
03 = 03-trafiklys.hw
04 = 04-von-Neumann.hw

all:
	make run

ccLexer.java:	cc.g4
	$(antlr4) -visitor cc.g4

grun:	ccLexer.class cc.txt
	$(grun) cc start -gui -tokens test/$(1a)

main.class:	ccLexer.java main.java
	javac -cp $(classpath) $(GENERATED) main.java

run:	main.class
	java -cp $(classpath) main test/$(04)

clean:
	rm $(GENERATED) *.class cc.interp ccLexer.interp ccLexer.tokens cc.tokens