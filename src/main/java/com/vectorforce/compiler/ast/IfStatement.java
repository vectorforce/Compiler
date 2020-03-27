package main.java.com.vectorforce.compiler.ast;

public class IfStatement implements Statement {
    private final Expression expression;
    private final Statement ifStatement;
    private final Statement elseStatement;

    public IfStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        final double result = expression.evaluate().asNumber();
        if (result != 0) {
            ifStatement.execute();
        } else {
            elseStatement.execute();
        }
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("if ").append(expression).append(ifStatement);
        if(elseStatement != null){
            buffer.append("\nelse").append(elseStatement);
        }
        return buffer.toString();
    }
}
