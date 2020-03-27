package main.java.com.vectorforce.compiler.ast.parser;

import main.java.com.vectorforce.compiler.ast.Expression;
import main.java.com.vectorforce.compiler.vars.Value;
import main.java.com.vectorforce.compiler.vars.Variables;

public class VariableExpression implements Expression {
    private final String name;

    VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value evaluate() {
        if(!Variables.isExists(name)){
            throw new RuntimeException("Constant does not exists");
        }
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
