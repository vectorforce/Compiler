package main.java.com.vectorforce.compiler;

import main.java.com.vectorforce.compiler.parser.LexicalAnalyzer;
import main.java.com.vectorforce.compiler.parser.Parser;
import main.java.com.vectorforce.compiler.parser.Token;
import main.java.com.vectorforce.compiler.parser.ast.Statement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Compiler {
    public static void main(String[] args) throws IOException {
        final String inputText1 = new String(Files.readAllBytes(Paths.get("Test program1.lmoc")), "UTF-8");
        final List<Token> tokens = new LexicalAnalyzer(inputText1).tokenize();
        final Statement statement = new Parser(tokens).parse();
        statement.execute();

        ResultFileBuilder.generate(Context.getContext(), Context.getMethods());
    }
}
