package main.java.com.vectorforce.compiler.ast;

public final class BreakStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        throw this;
    }
}
