package main.java.com.vectorforce.compiler.ast.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicalAnalyzer {  // Class for lexical analysis of input text
    private static final String OPERATOR_CHARS = "+-*/(){}=<>!&|;,";

    private final static Map<String, TokenType> OPERATORS;

    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", TokenType.PLUS);
        OPERATORS.put("-", TokenType.MINUS);
        OPERATORS.put("*", TokenType.STAR);
        OPERATORS.put("/", TokenType.SLASH);
        OPERATORS.put("(", TokenType.LPAREN);
        OPERATORS.put(")", TokenType.RPAREN);
        OPERATORS.put("{", TokenType.LBRACE);
        OPERATORS.put("}", TokenType.RBRACE);
        OPERATORS.put("=", TokenType.EQ);
        OPERATORS.put("<", TokenType.LT);
        OPERATORS.put(">", TokenType.GT);
        OPERATORS.put(";", TokenType.SEMICOLON);
        OPERATORS.put(",", TokenType.COMMA);

        OPERATORS.put("!", TokenType.EXCLUDE);
        OPERATORS.put("&", TokenType.AMP);
        OPERATORS.put("|", TokenType.BAR);

        OPERATORS.put("==", TokenType.EQEQ);
        OPERATORS.put("!=", TokenType.EXCLUDEEQ);
        OPERATORS.put("<=", TokenType.LTEQ);
        OPERATORS.put(">=", TokenType.GTEQ);

        OPERATORS.put("&&", TokenType.AMPAMP);
        OPERATORS.put("||", TokenType.BARBAR);
    }

    private final String inputText;
    private final int length;

    private final List<Token> tokens;

    private int currentPosition;    // Current position in text

    public LexicalAnalyzer(String inputText) {
        this.inputText = inputText;
        this.length = inputText.length();
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (currentPosition < length) {
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
        while (currentChar != '"') {
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
        while (Character.isLetterOrDigit(currentChar) || (currentChar == '_')/* || (currentChar == '$')*/) {
            buffer.append(currentChar);
            currentChar = next();
        }
        String toString = buffer.toString();

        switch (toString) {
            case "print":
                addToken(TokenType.PRINT);
                break;
            case "if":
                addToken(TokenType.IF);
                break;
            case "else":
                addToken(TokenType.ELSE);
                break;
            case "for":
                addToken(TokenType.FOR);
                break;
            case "do":
                addToken(TokenType.DO);
                break;
            case "break":
                addToken(TokenType.BREAK);
                break;
            case "continue":
                addToken(TokenType.CONTINUE);
                break;
            case "while":
                addToken(TokenType.WHILE);
                break;
            case "def":
                addToken(TokenType.DEF);
                break;
            default:
                addToken(TokenType.WORD, toString);
                break;
        }
    }

    private void tokenizeOperator() {
        char currentChar = peek(0);
        final StringBuilder buffer = new StringBuilder();
        while (true) {
            final String currentBuffer = buffer.toString();
            if(!OPERATORS.containsKey(currentBuffer + currentChar) && !currentBuffer.isEmpty()){
                    addToken(OPERATORS.get(currentBuffer));
                    return;
            }
            buffer.append(currentChar);
            currentChar = next();
        }
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
        if (position >= length) {
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
