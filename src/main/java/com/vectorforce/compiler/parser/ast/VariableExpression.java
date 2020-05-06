package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.vars.NumberValue;
import main.java.com.vectorforce.compiler.vars.Value;
import main.java.com.vectorforce.compiler.vars.Variables;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value evaluate() {
        if(!Variables.isExists(name)){
//            throw new RuntimeException("Constant does not exists");
            Variables.set(name, new NumberValue(0));
        }
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
