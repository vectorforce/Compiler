package main.java.com.vectorforce.compiler.ast;

import main.java.com.vectorforce.compiler.vars.Value;

public interface Expression {
    Value evaluate();
}
