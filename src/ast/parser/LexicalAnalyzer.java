package ast.parser;

import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer {  // Class for lexical analysis of input text
    private static final String OPERATOR_CHARS = "+-*/()=";
    private static final TokenType[] OPERATOR_TOKENS = {
            TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH,
            TokenType.LPAREN, TokenType.RPAREN,
            TokenType.EQ
    };

    private final String inputText;
    private final int lenght;

    private final List<Token> tokens;

    private int currentPosition;    // Current position in text

    public LexicalAnalyzer(String inputText) {
        this.inputText = inputText;
        this.lenght = inputText.length();
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (currentPosition < lenght) {
            final char currentChar = peek(0);
            if (Character.isDigit(currentChar)) {
                tokenizeNumber();
            } else if (Character.isLetter(currentChar)) {
                tokenizeWord();
            } else if (OPERATOR_CHARS.indexOf(currentChar) != -1) {
                tokenizeOperator();
            } else if (currentChar == '"') {
                TokenizeText();
            } else {
                next(); // Skip other chars(whitespaces...)
            }
        }
        return tokens;
    }

    private void TokenizeText() {
        final StringBuilder buffer = new StringBuilder();
        next();    // Skip "
        char currentChar = peek(0);
        while (true) {
            if (currentChar == '"') {
                break;
            }
            if (currentChar == '\\') {
                currentChar = next();
                switch (currentChar) {
                    case '"':
                        currentChar = next();
                        buffer.append('"');
                        continue;
                    case 'n':
                        currentChar = next();
                        buffer.append('\n');
                        continue;
                    case 't':
                        currentChar = next();
                        buffer.append('\t');
                        continue;
                }
                buffer.append('\\');
                continue;
            }
            buffer.append(currentChar);
            currentChar = next();
        }
        addToken(TokenType.TEXT, buffer.toString());
        next();    // Skip "
    }

    private void tokenizeWord() {
        final StringBuilder buffer = new StringBuilder();
        char currentChar = peek(0);
        while (true) {
            if (!Character.isLetterOrDigit(currentChar) && (currentChar != '_') && (currentChar != '$')) {
                break;
            }
            buffer.append(currentChar);
            currentChar = next();
        }
        String toString = buffer.toString();
        if (toString.equals("print")) {
            addToken(TokenType.PRINT);
        } else {
            addToken(TokenType.WORD, toString);
        }
    }

    private void tokenizeOperator() {
        final int position = OPERATOR_CHARS.indexOf(peek(0));
        addToken(OPERATOR_TOKENS[position]);
        next();
    }

    private void tokenizeNumber() {
        final StringBuilder buffer = new StringBuilder();
        char currentChar = peek(0);
        while (true) {
            if (currentChar == '.') {
                if (buffer.indexOf(".") != -1) {
                    throw new RuntimeException("Invalid float number");
                }
            } else if (!Character.isDigit(currentChar)) {
                break;
            }
            buffer.append(currentChar);
            currentChar = next();
        }
        addToken(TokenType.NUMBER, buffer.toString());
    }

    private char next() {
        currentPosition++;
        return peek(0);
    }

    private char peek(int relativePosition) {    // Return char at relativePosition in the text
        final int position = currentPosition + relativePosition;
        if (position >= lenght) {
            return '\0';
        }
        return inputText.charAt(position);
    }

    private void addToken(TokenType tokenType) {
        addToken(tokenType, "");
    }

    private void addToken(TokenType tokenType, String text) {
        tokens.add(new Token(tokenType, text));
    }
}
