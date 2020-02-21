import ast.Expression;
import ast.parser.LexicalAnalyzer;
import ast.parser.Parser;
import ast.parser.Token;

import java.util.List;

public class Compiler {
    public static void main(String[] args){
        final String inputText1 = "2 + 2";
        final String inputText2 = "(PI + 2.45) * -2";
        final List<Token> tokens = new LexicalAnalyzer(inputText2).tokenize();
        for (Token token: tokens) {
            System.out.println(token);
        }
        final List<Expression> expressions = new Parser(tokens).parse();
        for (Expression expression: expressions) {
            System.out.println(expression + " = " + expression.evaluate());
        }
    }
}
