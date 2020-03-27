package main.java.com.vectorforce.compiler.ast;

public final class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        System.out.print(expression.evaluate().asString());
    }

    @Override
    public String toString() {
        return "print " + expression;
    }
}
