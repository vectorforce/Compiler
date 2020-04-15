package main.java.com.vectorforce.compiler.parser.ast;

import main.java.com.vectorforce.compiler.parser.ast.BreakStatement;
import main.java.com.vectorforce.compiler.parser.ast.ContinueStatement;
import main.java.com.vectorforce.compiler.parser.ast.Expression;
import main.java.com.vectorforce.compiler.parser.ast.Statement;

public class WhileStatement implements Statement {
    private final Expression condition;
    private final Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {
        while (condition.evaluate().asNumber() != 0) {
            try {
                statement.execute();
            } catch (BreakStatement e) {
                break;
            } catch (ContinueStatement e) {
                continue;
            }
        }
    }
}
