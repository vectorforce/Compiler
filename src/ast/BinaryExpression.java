package ast;

public class BinaryExpression implements Expression {
    private final Expression expression1;
    private final Expression expression2;
    private final char operation;

    public BinaryExpression(char operation, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public double evaluate() {
        switch (operation) {
            case '-':
                return expression1.evaluate() - expression2.evaluate();
            case '*':
                return expression1.evaluate() * expression2.evaluate();
            case '/':
                return expression1.evaluate() / expression2.evaluate();
            case '+':
            default:
                return expression1.evaluate() + expression2.evaluate();
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", expression1, operation, expression2);
    }
}
