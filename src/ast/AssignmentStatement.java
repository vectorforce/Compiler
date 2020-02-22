package ast;

import vars.Value;
import vars.Variables;

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
        Variables.set(variable, result);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
    }
}
