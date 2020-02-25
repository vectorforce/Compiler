package ast.parser;

import ast.*;

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

    public List<Statement> parse() {
        final List<Statement> result = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            result.add(statement());
        }
        return result;
    }

    /*
     * Stack of operations
     * and their priority
     * */
    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return new PrintStatement(expression());
        }
        if (match(TokenType.IF)) {
            return ifElse();
        }
        return assignmentStatement();
    }

    private Statement assignmentStatement() {
        final Token currentToken = peek(0);
        if (match(TokenType.WORD) && peek(0).getType() == TokenType.EQ) {
            final String variable = currentToken.getText();
            match(TokenType.EQ);
            return new AssignmentStatement(variable, expression());
        }
        throw new RuntimeException("Unknown statement");
    }

    private Statement ifElse() {
        final Expression condition = expression();
        final Statement ifStatement = statement();
        final Statement elseSteteement;
        if (match(TokenType.ELSE)) {
            elseSteteement = statement();
        } else {
            elseSteteement = null;
        }
        return new IfStatement(condition, ifStatement, elseSteteement);
    }

    private Expression expression() {
        return logicalOr();
    }

    private Expression logicalOr() {
        Expression result = logicalAnd();
        while (true) {
            if (match(TokenType.BARBAR)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.OR, result, logicalAnd());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression logicalAnd() {
        Expression result = conditionalExpression();
        while (true) {
            if (match(TokenType.AMPAMP)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.AND, result, conditionalExpression());
                continue;
            }
            break;
        }
        return result;
    }

//    private Expression equality() {
//        Expression result = conditionalExpression();
//        if (match(TokenType.EQEQ)) {
//            return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, conditionalExpression());
//        }
//        if (match(TokenType.EXCLUDEEQ)) {
//            return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, conditionalExpression());
//        }
//        return result;
//    }

    private Expression conditionalExpression() {
        Expression result = additive();

        while (true) {
            if (match(TokenType.LT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LT, result, additive());
                continue;
            }
            if (match(TokenType.LTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, result, additive());
                continue;
            }
            if (match(TokenType.GT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GT, result, additive());
                continue;
            }
            if (match(TokenType.GTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, result, additive());
                continue;
            }
            if (match(TokenType.EQEQ)) {
                return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, additive());
            }
            if (match(TokenType.EXCLUDEEQ)) {
                return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, additive());
            }
            break;
        }
        return result;
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
            return new ValueExpression(Double.parseDouble(currentToken.getText()));
        }
        if (match(TokenType.WORD)) {
            return new VariableExpression(currentToken.getText());
        }
        if (match(TokenType.TEXT)) {
            return new ValueExpression(currentToken.getText());
        }
        if (match(TokenType.LPAREN)) {
            Expression result = expression();
            match(TokenType.RPAREN);
            return result;
        }
        throw new RuntimeException("Unknown expression");
    }

    private Token consume(TokenType tokenType) {    // Match check of types
        final Token currentToken = peek(0);
        if (tokenType != currentToken.getType()) {
            throw new RuntimeException("Token " + currentToken + " doesn't match " + tokenType);
        }
        currentPosition++;
        return currentToken;
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
