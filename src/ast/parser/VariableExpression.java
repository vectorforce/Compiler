package ast.parser;

import ast.Expression;
import lib.Variables;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public double evaluate() {
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
