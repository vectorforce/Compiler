package main.java.com.vectorforce.compiler.ast;

public final class ContinueStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        throw this;
    }
}
