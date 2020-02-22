package ast;

import vars.NumberValue;
import vars.StringValue;
import vars.Value;

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
