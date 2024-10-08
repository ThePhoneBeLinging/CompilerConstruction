import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
	public static void main(String[] args) throws IOException{

		// we expect exactly one argument: the name of the input file
		if (args.length!=1) {
			System.err.println("\n");
			System.err.println("Please give as input argument a filename\n");
			System.exit(-1);
		}
		String filename=args[0];

		// open the input file
		CharStream input = CharStreams.fromFileName(filename);
		//new ANTLRFileStream (filename); // depricated

		// create a lexer/scanner
		ccLexer lex = new ccLexer(input);

		// get the stream of tokens from the scanner
		CommonTokenStream tokens = new CommonTokenStream(lex);

		// create a parser
		ccParser parser = new ccParser(tokens);

		// and parse anything from the grammar for "start"
		ParseTree parseTree = parser.start();

		// Construct an interpreter and run it on the parse tree
		Interpreter interpreter = new Interpreter();
		String result=interpreter.visit(parseTree);
		System.out.println("The result is: " + result);
	}
}


class Interpreter extends AbstractParseTreeVisitor<String>
		implements ccVisitor<String> {


	@Override
	public String visitStart(ccParser.StartContext ctx)
	{
		StringBuilder finalResult = new StringBuilder();
		for (var child : ctx.children)
		{
			finalResult.append(visit(child));
		}
		return finalResult.toString();
	}

	@Override
	public String visitUpdatesContext(ccParser.UpdatesContextContext ctx)
	{
		return "";
	}

	@Override
	public String visitSimnputContext(ccParser.SimnputContextContext ctx)
	{
		return "";
	}

	@Override
	public String visitInputsContext(ccParser.InputsContextContext ctx)
	{
		return "";
	}

	@Override
	public String visitOutputsContext(ccParser.OutputsContextContext ctx)
	{
		return "";
	}

	@Override
	public String visitDefContext(ccParser.DefContextContext ctx)
	{
		return "";
	}

	@Override
	public String visitLathesContext(ccParser.LathesContextContext ctx)
	{
		return "";
	}

	@Override
	public String visitAssignmentContext(ccParser.AssignmentContextContext ctx)
	{
		return "ASSIGNMENT";
	}

	@Override
	public String visitHardwareContext(ccParser.HardwareContextContext ctx)
	{
		return "\"<!DOCTYPE html>\n\\\"\"<html><head><title>" + ctx.name + "</title>\n\"\"<script src=\\\"https://polyfill.io/v3/polyfill.min.js?features=es6\\\"></script>\n\"\"<script type=\"text/javascript\" id=\"MathJax-script\"\\n\"\"async\n\"\"src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\">\n\"\"</script></head><body>\\n\"";
	}

	@Override
	public String visitANDExp(ccParser.ANDExpContext ctx)
	{
		return "";
	}

	@Override
	public String visitNotExp(ccParser.NotExpContext ctx)
	{
		return "";
	}

	@Override
	public String visitIdentEqExp(ccParser.IdentEqExpContext ctx)
	{
		return "";
	}

	@Override
	public String visitVariable(ccParser.VariableContext ctx)
	{
		return "";
	}

	@Override
	public String visitDEF(ccParser.DEFContext ctx)
	{
		return "";
	}

	@Override
	public String visitORExp(ccParser.ORExpContext ctx)
	{
		return "\\not";
	}
}
