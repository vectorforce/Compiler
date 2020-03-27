package main.java.com.vectorforce.compiler.vars;

import main.java.com.vectorforce.compiler.ast.Statement;

import java.util.List;

public class UserDefinedFunction implements Function {
    private final List<String> argNames;
    private final Statement body;

    public UserDefinedFunction(List<String> argNames, Statement body) {
        this.argNames = argNames;
        this.body = body;
    }

    public int getArgsCount() {
        return argNames.size();
    }

    public String getArgsName(int index) {
        if (index < 0 || index >= getArgsCount()) return "";
        return argNames.get(index);
    }

    @Override
    public Value execute(Value... args) {
        body.execute();
        return Variables.ZERO;
    }
}