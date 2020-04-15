package main.java.com.vectorforce.compiler.parser;

public enum TokenType {       // Enumeration for all types of tokens
    NUMBER, WORD, TEXT,

    PRINT, IF, ELSE, FOR, WHILE, DO, BREAK, CONTINUE,

    DEF,

    PLUS, MINUS, STAR, SLASH,

    EQ, EQEQ, LT, LTEQ, GT, GTEQ, EXCLUDE, EXCLUDEEQ, BAR, BARBAR, AMP, AMPAMP,

    LPAREN, RPAREN, LBRACE, RBRACE, SEMICOLON, COMMA,

    EOF;
}
