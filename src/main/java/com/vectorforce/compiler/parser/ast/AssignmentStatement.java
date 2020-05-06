package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.Context;
import main.java.com.vectorforce.compiler.vars.Value;
import main.java.com.vectorforce.compiler.vars.Variables;

public class AssignmentStatement implements Statement {
    private final String variable;
    private final Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void execute() {
        final Value result = expression.evaluate();

        /*
         * Write to file
         * */
        if (Variables.isExists(variable)) {
            Context.appendNewString(variable + " = " + expression);
        } else {
            Context.appendNewString("Double " + variable + " = " + expression);
        }
        Context.completeLine();

        Variables.set(variable, result);
    }

    public String getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
    }
}
