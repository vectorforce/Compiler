package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.Context;
import main.java.com.vectorforce.compiler.vars.Variables;

public class ForStatement implements Statement {
    private Statement initStatement;
    private Expression termination;
    private Statement increment;
    private Statement block;

    public ForStatement(Statement initStatement, Expression termination, Statement increment, Statement block) {
        this.initStatement = initStatement;
        this.termination = termination;
        this.increment = increment;
        this.block = block;
    }

    @Override
    public void execute() {
        Variables.set(((AssignmentStatement) initStatement).getVariable(), ((AssignmentStatement) initStatement).getExpression().evaluate());

        /*
         * Write to file
         * */
        Context.appendNewString("for (double " + initStatement.toString());
        Context.appendCurrentString("; ");
        Context.appendCurrentString(termination.toString());
        Context.appendCurrentString("; ");
        Context.appendCurrentString(increment.toString());
        Context.appendCurrentString(") {");
        block.execute();
        Context.appendNewString(" }");

//        for (initStatement.execute(); termination.evaluate().asNumber() != 0; increment.execute()) {
//            try {
//                block.execute();
//            } catch (BreakStatement e) {
//                break;
//            } catch (ContinueStatement e) {
//                continue;
//            }
//        }
    }
}
