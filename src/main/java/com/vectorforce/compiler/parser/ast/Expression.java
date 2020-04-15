package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.vars.Value;

public interface Expression {
    Value evaluate();
}
