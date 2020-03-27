package main.java.com.vectorforce.compiler.ast;

import main.java.com.vectorforce.compiler.vars.Functions;
import main.java.com.vectorforce.compiler.vars.Value;

import java.util.ArrayList;
import java.util.List;

public class FunctionalExpression implements Expression{
    private final String name;
    private final List<Expression> arguments;

    public FunctionalExpression(String name) {
        this.name = name;
        arguments = new ArrayList<>();
    }

    public FunctionalExpression(String name, List<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public void addArgument(Expression arg) {
        arguments.add(arg);
    }

    @Override
    public Value evaluate() {
        final int size = arguments.size();
        final Value[] values = new Value[size];
        for (int index = 0; index < size; index++) {
            values[index] = arguments.get(index).evaluate();
        }
        return Functions.get(name).execute(values);
    }
}
