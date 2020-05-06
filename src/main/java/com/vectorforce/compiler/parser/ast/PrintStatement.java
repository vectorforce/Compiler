package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.Context;
import main.java.com.vectorforce.compiler.vars.NumberValue;
import main.java.com.vectorforce.compiler.vars.StringValue;
import main.java.com.vectorforce.compiler.vars.Value;

public final class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value expressionValue = expression.evaluate();
        System.out.print(expressionValue);

        /*
         * Write to file
         * */
        String expressionToString = expression.toString();
        Context.appendNewString("System.out.print(");
        if (expressionValue instanceof NumberValue) {
            Context.appendCurrentString(expressionToString);
        } else if (expressionValue instanceof StringValue) {
            Context.appendCurrentString("\"");
            if (expressionToString.equals("\n")) {
                Context.appendCurrentString("\\n");
            } else {
                Context.appendCurrentString(expression.toString());
            }
            Context.appendCurrentString("\"");
        }
        Context.appendCurrentString(")");
        Context.completeLine();
//        }
    }

    @Override
    public String toString() {
        return "print " + expression;
    }
}
