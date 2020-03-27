package main.java.com.vectorforce.compiler.ast.parser;

import main.java.com.vectorforce.compiler.ast.Statement;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {
    private List<Statement> statements;

    BlockStatement() {
        this.statements = new ArrayList<>();
    }

    void add(Statement statement){
        statements.add(statement);
    }

    @Override
    public void execute() {
        for(Statement statement: statements){
            statement.execute();
        }
    }
}