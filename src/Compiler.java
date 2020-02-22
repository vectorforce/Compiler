import ast.Expression;
import ast.Statement;
import ast.parser.LexicalAnalyzer;
import ast.parser.Parser;
import ast.parser.Token;
import lib.Variables;

import java.util.List;

public class Compiler {
    public static void main(String[] args){
        final String inputText1 = "var = 2 + 2";
        final String inputText2 = "(PI + 2.45) * -2";
        final List<Token> tokens = new LexicalAnalyzer(inputText1).tokenize();
        for (Token token: tokens) {
            System.out.println(token);
        }
        final List<Statement> statements = new Parser(tokens).parse();
        for (Statement statement: statements) {
            System.out.println(statement);
        }
        for (Statement statement: statements) {
            statement.execute();
        }
        System.out.printf("%s = %f", "var", Variables.get("var"));
    }
}
