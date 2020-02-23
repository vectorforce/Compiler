package ast;

import vars.NumberValue;
import vars.Value;

public class ConditionalExpression implements Expression {
    private final Expression expression1;
    private final Expression expression2;
    private final char operation;

    public ConditionalExpression(char operation, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate() {
//        final Value value1 = expression1.evaluate();
//        final Value value2 = expression2.evaluate();
//        if (value1 instanceof StringValue) {
//            final String string1 = value1.asString();
//            final String string2 = value2.asString();
//            switch (operation) {
//                case '<':
//                    return new NumberValue(string1.compareTo(string2) < 0);
//                case '>':
//                    return new NumberValue(string1.compareTo(string2) > 0);
//                case '=':
//                default:
//                    return new NumberValue(string1.equals(string2));
//            }
//        }

        final double number1 = expression1.evaluate().asNumber();
        final double number2 = expression2.evaluate().asNumber();
        switch (operation) {
            case '<':
                return new NumberValue(number1 < number2);
            case '>':
                return new NumberValue(number1 > number2);
            case '=':
            default:
                return new NumberValue(number1 == number2);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", expression1, operation, expression2);
    }
}
