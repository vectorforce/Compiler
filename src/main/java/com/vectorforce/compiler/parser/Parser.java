package main.java.com.vectorforce.compiler.parser;

import main.java.com.vectorforce.compiler.parser.ast.*;

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

    public Statement parse() {
        final BlockStatement blockStatement = new BlockStatement();
        while (!match(TokenType.EOF)) {
            blockStatement.add(statement());
        }
        return blockStatement;
    }

    /*
     * Stack of operations
     * by their priority
     * */
    private Statement block() {
        final BlockStatement blockStatement = new BlockStatement();
        consume(TokenType.LBRACE);  // Skip {
        while (!match(TokenType.RBRACE)) {
            blockStatement.add(statement());
        }
        return blockStatement;
    }

    private Statement statementOrBlock() {
        if (peek(0).getType() == TokenType.LBRACE) {
            return block();
        }
        return statement();
    }

    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return new PrintStatement(expression());
        }
        if (match(TokenType.IF)) {
            return ifElse();
        }
        if (match(TokenType.DO)) {
            return doWhileStatement();
        }
        if (match(TokenType.WHILE)) {
            return whileStatement();
        }
        if (match(TokenType.FOR)) {
            return forStatement();
        }
        if (match(TokenType.BREAK)) {
            return new BreakStatement();
        }
        if (match(TokenType.CONTINUE)) {
            return new ContinueStatement();
        }
        if (match(TokenType.DEF)) {
            return functionDefine();
        }
        if (peek(0).getType() == TokenType.WORD && peek(1).getType() == TokenType.LPAREN) {
            return new FunctionStatement(function());
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
        final Statement ifStatement = statementOrBlock();
        final Statement elseStatement;
        if (match(TokenType.ELSE)) {
            elseStatement = statementOrBlock();                       //!!!!!!!!!!!!!!!!!!!!!!! statement REPLACED on statementOrBlock
        } else {
            elseStatement = null;
        }
        return new IfStatement(condition, ifStatement, elseStatement);
    }

    private Statement doWhileStatement() {
        final Statement statement = statementOrBlock();
        consume(TokenType.WHILE);
        final Expression condition = expression();
        return new DoWhileStatement(condition, statement);
    }

    private Statement whileStatement() {
        final Expression condition = expression();
        final Statement statement = statementOrBlock();
        return new WhileStatement(condition, statement);
    }

    private Statement forStatement() {
        final Statement initStatement = assignmentStatement();
        consume(TokenType.SEMICOLON);
        final Expression termination = expression();
        consume(TokenType.SEMICOLON);
        final Statement increment = assignmentStatement();
        final Statement statement = statementOrBlock();
        return new ForStatement(initStatement, termination, increment, statement);
    }

    private FunctionalExpression function() {
        final String name = consume(TokenType.WORD).getText();
        consume(TokenType.LPAREN);
        final FunctionalExpression function = new FunctionalExpression(name);
        while (!match(TokenType.RPAREN)) {
            function.addArgument(expression());
            match(TokenType.COMMA);
        }
        return function;
    }

    private FunctionDefineStatement functionDefine() {
        final String name = consume(TokenType.WORD).getText();
        consume(TokenType.LPAREN);
        final List<String> argNames = new ArrayList<>();
        while (!match(TokenType.RPAREN)) {
            argNames.add(consume(TokenType.WORD).getText());
            match(TokenType.COMMA);
        }
        final Statement body = statementOrBlock();
        return new FunctionDefineStatement(name, argNames, body);
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
        if (peek(0).getType() == TokenType.WORD && peek(1).getType() == TokenType.LPAREN) {
            return function();
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
