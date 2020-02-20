package ast.parser;

import ast.BinaryExpression;
import ast.Expression;
import ast.NumberExpression;
import ast.UnaryExpression;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final Token EOF = new Token(TokenType.EOF, "");

    private final List<Token> tokens;
    private final int size;

    private int currentPosition;    // Token's current position

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.size = tokens.size();
    }

    public List<Expression> parse() {
        final List<Expression> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(expression());
        }
        return result;
    }

    /*
     * Stack of operations
     * and their priority
     * */
    private Expression expression() {
        return additive();
    }

    private Expression additive() {
        Expression result = multiplicative();

        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, multiplicative());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression multiplicative() {
        Expression result = unary();

        while (true) {
            if (match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression unary() {
        if (match(TokenType.MINUS)) {
            return new UnaryExpression('-', primary());
        }
        if (match(TokenType.PLUS)) {
            return primary();
        }
        return primary();
    }

    private Expression primary() {
        final Token currentToken = peek(0);

        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(currentToken.getText()));
        }
        if(match(TokenType.LPAREN)){
            Expression result = expression();
            match(TokenType.RPAREN);
            return result;
        }
        throw new RuntimeException("Unknown expression");
    }

    private boolean match(TokenType tokenType) {    // Match check of types
        final Token currentToken = peek(0);
        if (tokenType != currentToken.getType()) {
            return false;
        }
        currentPosition++;
        return true;
    }

    private Token peek(int relativePosition) {  // Return token at relativePosition in the array of tokens
        final int position = currentPosition + relativePosition;
        if (position >= size) {
            return EOF;
        }
        return tokens.get(position);
    }
}
