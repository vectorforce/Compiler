package ast.parser;

public enum TokenType {       // Enumeration for all types of tokens
    NUMBER, WORD, TEXT,

    PRINT, IF, ELSE,

    PLUS, MINUS, STAR, SLASH,

    EQ, EQEQ, LT, LTEQ, GT, GTEQ, EXCLUDE, EXCLUDEEQ, BAR, BARBAR, AMP, AMPAMP,

    LPAREN, RPAREN,

    EOF
}
