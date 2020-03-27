package main.java.com.vectorforce.compiler.ast;

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
