package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.Context;

public class DoWhileStatement implements Statement {
    private final Expression condition;
    private final Statement statement;

    public DoWhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {
        /*
         * Write to file
         * */
        Context.appendNewString("do {");
        statement.execute();
        Context.appendNewString(" }");
        Context.appendCurrentString(" while " + condition);
        Context.completeLine();

//        do {
//            try {
//                statement.execute();
//            } catch (BreakStatement e) {
//                break;
//            } catch (ContinueStatement e) {
//                continue;
//            }
//        } while (condition.evaluate().asNumber() != 0);
    }
}
