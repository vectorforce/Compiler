package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.Context;

public class IfStatement implements Statement {
    private final Expression expression;
    private final Statement ifStatement;
    private final Statement elseStatement;

    public IfStatement(Expression expression, Statement ifStatement, Statement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void execute() {
        final double result = expression.evaluate().asNumber();

        Context.appendNewString("if " + expression);
        Context.appendCurrentString(" {");
//        if () {
//
//        }

//        if (result != 0) {
            ifStatement.execute();
//        } else {
//            elseStatement.execute();
//        }

        Context.appendNewString("}");

        if (elseStatement != null) {
            Context.appendCurrentString(" else {");
            elseStatement.execute();
            Context.appendNewString(" }");
        }
        /*
         * Write to file
         * */

    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("if ").append(expression).append(ifStatement);
        if (elseStatement != null) {
            buffer.append("\nelse").append(elseStatement);
        }
        return buffer.toString();
    }
}
