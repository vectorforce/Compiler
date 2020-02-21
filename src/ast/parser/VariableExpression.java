package ast.parser;

import ast.Expression;
import lib.Constants;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public double evaluate() {
        if(!Constants.isExists(name)){
            throw new RuntimeException("Constant does not exists");
        }
        return Constants.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
