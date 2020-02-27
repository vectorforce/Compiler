package ast;

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
        for (initStatement.execute(); termination.evaluate().asNumber() != 0; increment.execute()) {
            try {
                block.execute();
            } catch (BreakStatement e) {
                break;
            } catch (ContinueStatement e) {
                continue;
            }
        }
    }
}
