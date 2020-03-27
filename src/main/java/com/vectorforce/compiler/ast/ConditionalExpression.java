package main.java.com.vectorforce.compiler.ast;

import main.java.com.vectorforce.compiler.vars.NumberValue;
import main.java.com.vectorforce.compiler.vars.Value;

public class ConditionalExpression implements Expression {
    public enum Operator {
        PLUS("+"), MINUS("-"), MULTIPLY("*"), DEVIDE("*"),
        EQUALS("=="), NOT_EQUALS("!="),
        LT("<"), LTEQ("<="), GT(">"), GTEQ(">="),
        AND("&&"), OR("||");

        private final String name;
        Operator(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private final Expression expression1;
    private final Expression expression2;
    private final Operator operation;

    public ConditionalExpression(Operator operation, Expression expression1, Expression expression2) {
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
        boolean result;
        switch (operation) {
            case LT:
                result = number1 < number2;
                break;
            case LTEQ:
                result = number1 <= number2;
                break;
            case GT:
                result = number1 > number2;
                break;
            case GTEQ:
                result = number1 >= number2;
                break;
            case AND:
                result = (number1 != 0) && (number2 != 0);
                break;
            case OR:
                result = (number1 != 0) || (number2 != 0);
                break;
            case NOT_EQUALS:
                result = number1 != number2;
                break;
            case EQUALS:
            default:
                result = number1 == number2;
                break;
        }
        return new NumberValue(result);
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", expression1, operation.getName(), expression2);
    }
}
