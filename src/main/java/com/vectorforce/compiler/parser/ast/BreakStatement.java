package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.Context;

public final class BreakStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        /*
         * Write to file
         * */
        Context.appendNewString("break");
        Context.completeLine();

//        throw this;
    }
}
