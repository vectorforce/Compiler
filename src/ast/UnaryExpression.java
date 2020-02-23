package ast;

import vars.NumberValue;
import vars.Value;

public class UnaryExpression implements Expression {
    private final Expression expression;
    private final char operation;

    public UnaryExpression(char operation, Expression expression) {
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public Value evaluate() {
        switch (operation) {
            case '-':
                return new NumberValue(-expression.evaluate().asNumber());
            case '+':
            default:
                return expression.evaluate();
        }
    }

    @Override
    public String toString() {
        return String.format("(%c%s)", operation, expression);
    }
}
