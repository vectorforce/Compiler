package main.java.com.vectorforce.compiler.ast;

import main.java.com.vectorforce.compiler.vars.NumberValue;
import main.java.com.vectorforce.compiler.vars.StringValue;
import main.java.com.vectorforce.compiler.vars.Value;

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
    public Value evaluate() {
        final Value value1 = expression1.evaluate();
        final Value value2 = expression2.evaluate();
        if (value1 instanceof StringValue) {
            final String string1 = value1.asString();
            switch (operation){
                case '*':
                    if(!(value2 instanceof NumberValue)){
                        throw new RuntimeException("Invalid type of second token");
                    }
                    final int iterarions = (int)value2.asNumber();
                    final StringBuilder buffer = new StringBuilder();
                for(int index = 0; index < iterarions; index++){
                    buffer.append(string1);
                }
                return new StringValue(buffer.toString());
                case '+':
                    default: return new StringValue(string1 + value2.asString());
            }
        }

        final double doubleExpression1 = expression1.evaluate().asNumber();
        final double doubleExpression2 = expression2.evaluate().asNumber();
        switch (operation) {
            case '-':
                return new NumberValue(doubleExpression1 - doubleExpression2);
            case '*':
                return new NumberValue(doubleExpression1 * doubleExpression2);
            case '/':
                return new NumberValue(doubleExpression1 / doubleExpression2);
            case '+':
            default:
                return new NumberValue(doubleExpression1 + doubleExpression2);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", expression1, operation, expression2);
    }
}
