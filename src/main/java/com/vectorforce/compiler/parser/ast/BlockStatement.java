package main.java.com.vectorforce.compiler.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {
    private List<Statement> statements;

    public BlockStatement() {
        this.statements = new ArrayList<>();
    }

    public void add(Statement statement){
        statements.add(statement);
    }

    @Override
    public void execute() {
        for(Statement statement: statements){
            statement.execute();
        }
    }
}
