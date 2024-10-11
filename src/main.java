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
		System.out.println(result);
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
		finalResult.append("</body></html>\n");
		return finalResult.toString();
	}

	@Override
	public String visitUpdatesContext(ccParser.UpdatesContextContext ctx)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<h2> Updates </h2>\n");
		for (var expr : ctx.expression())
		{
			builder.append(visit(expr)).append('\n');
			String bobTheNotBuilder = builder.toString().replace(":", "&larr;\\");
			builder = new StringBuilder();
			builder.append(bobTheNotBuilder);
		}

		return builder.toString();
	}

	@Override
	public String visitSimnputContext(ccParser.SimnputContextContext ctx)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<h2> Simulation inputs </h2>\n");
		for (var expr : ctx.expression())
		{
			builder.append(visit(expr)).append('\n');
		}

		return builder.toString();
	}

	@Override
	public String visitInputsContext(ccParser.InputsContextContext ctx)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<h2> Inputs </h2>\n");
		for (var expr : ctx.expression())
		{
			builder.append(visit(expr)).append('\n');
		}

		return builder.toString();
	}

	@Override
	public String visitOutputsContext(ccParser.OutputsContextContext ctx)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<h2> Outputs</h2>\n");
		for (var expr : ctx.expression())
		{
			builder.append(visit(expr)).append('\n');
		}

		return builder.toString();
	}

	@Override
	public String visitDefContext(ccParser.DefContextContext ctx)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<h2> Definitions</h2>\n");
		for (var expr : ctx.expression())
		{
			builder.append("\\mathit{");
			builder.append(ctx.funcName.getText());
			builder.append("}");
			builder.append('(');
			builder.append(ctx.ident1.getText());
			for (var params : ctx.ident2.getText().split(","))
			{
				builder.append(',');
				builder.append(params);
			}
			builder.append(')');
			builder.append(" = ");
			builder.append(visit(expr)).append("<br>").append('\n');
		}

		return builder.toString();
	}

	@Override
	public String visitLathesContext(ccParser.LathesContextContext ctx)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<h2> Latches</h2>\n");
		for (var expr : ctx.expression())
		{
			builder.append(visit(expr)).append('\n');
		}

		return builder.toString();
	}

	@Override
	public String visitHardwareContext(ccParser.HardwareContextContext ctx)
	{
		return "<!DOCTYPE html>\n<html><head><title>" + ctx.IDENTIFIER().getText() + "</title>\n<script src=\"https://polyfill.io/v3/polyfill.min.js?features=es6\"></script>\n<script type=\"text/javascript\" id=\"MathJax-script\"\nasync src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\">\n</script></head><body>\n";
	}

	@Override
	public String visitDEFINITION(ccParser.DEFINITIONContext ctx)
	{
		// Oscillator&larr;\(\mathit{nor}(\mathrm{Oscillator’},\mathrm{Reset})\)<br>
		StringBuilder builder = new StringBuilder();
		for (var expr : ctx.expression())
		{
			builder.append('(');
			builder.append("\\mathit{");
			builder.append(ctx.IDENTIFIER().getText());
			builder.append("}");
			builder.append('(');
			builder.append(ctx.exp1.getText());
			for (var params : ctx.exp2.getText().split(","))
			{
				builder.append(',');
				builder.append(params);
			}
			builder.append(visit(expr));
		}
		builder.append(')');
		return builder.toString();
	}

	@Override
	public String visitANDExp(ccParser.ANDExpContext ctx)
	{
		return visit(ctx.exp1) + " \\wedge " + visit(ctx.exp2);
	}

	@Override
	public String visitNotExp(ccParser.NotExpContext ctx)
	{
		return "\\neg(\\mathrm{" + ctx.IDENTIFIER().getText() + "})";
	}

	@Override
	public String visitIdentEqExp(ccParser.IdentEqExpContext ctx)
	{
		return ctx.IDENTIFIER().getText() + ":" + visit(ctx.expression());
	}

	@Override
	public String visitVariable(ccParser.VariableContext ctx)
	{
		return ctx.IDENTIFIER().getText();
	}

	@Override
	public String visitORExp(ccParser.ORExpContext ctx)
	{
		return visit(ctx.exp1) + " \\vee " + visit(ctx.exp2);
	}

	@Override
	public String visitIdentEqNum(ccParser.IdentEqNumContext ctx)
	{
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(ctx.IDENTIFIER());
		stringBuilder.append(ctx.EQUALS());
		stringBuilder.append(ctx.NUMBER());

		return stringBuilder.toString();
	}

	@Override
	public String visitExpEQExp(ccParser.ExpEQExpContext ctx)
	{
		return visit(ctx.exp1) + visit(ctx.exp2);
	}

	@Override
	public String visitExpressionInParenthesis(ccParser.ExpressionInParenthesisContext ctx)
	{
		return visit(ctx.expression());
	}
}
