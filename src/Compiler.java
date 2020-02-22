import ast.Statement;
import ast.parser.LexicalAnalyzer;
import ast.parser.Parser;
import ast.parser.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Compiler {
    public static void main(String[] args) throws IOException {
        final String inputText1 = new String(Files.readAllBytes(Paths.get("Test program.lmoc")), "UTF-8");
        final List<Token> tokens = new LexicalAnalyzer(inputText1).tokenize();
        for (Token token: tokens) {
//            System.out.println(token);
        }
        final List<Statement> statements = new Parser(tokens).parse();
        for (Statement statement: statements) {
//            System.out.println(statement);
        }
        for (Statement statement: statements) {
            statement.execute();
        }
    }
}
