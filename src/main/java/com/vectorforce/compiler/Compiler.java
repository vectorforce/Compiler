package main.java.com.vectorforce.compiler;

import main.java.com.vectorforce.compiler.parser.ast.Statement;
import main.java.com.vectorforce.compiler.parser.LexicalAnalyzer;
import main.java.com.vectorforce.compiler.parser.Parser;
import main.java.com.vectorforce.compiler.parser.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Compiler {
    public static void main(String[] args) throws IOException {
        final String inputText1 = new String(Files.readAllBytes(Paths.get("Test program.lmoc")), "UTF-8");
        final List<Token> tokens = new LexicalAnalyzer(inputText1).tokenize();
//        for (Token token: tokens) {
//            System.out.println(token);
//        }
        final Statement statement = new Parser(tokens).parse();
        statement.execute();
    }
}
