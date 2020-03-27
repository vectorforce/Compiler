package main.java.com.vectorforce.compiler.ast;

import main.java.com.vectorforce.compiler.vars.NumberValue;
import main.java.com.vectorforce.compiler.vars.StringValue;
import main.java.com.vectorforce.compiler.vars.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(double value) {
        this.value = new NumberValue(value);
    }

    public ValueExpression(String value) {
        this.value = new StringValue(value);
    }

    @Override
    public Value evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
