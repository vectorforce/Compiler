package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.Context;

public final class ContinueStatement extends RuntimeException implements Statement {

    @Override
    public void execute() {
        /*
         * Write to file
         * */
        Context.appendNewString("continue");
        Context.completeLine();

//        throw this;
    }
}
