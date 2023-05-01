package org.example.astnodes;

public class WhileLoopNode extends Statement {

    private Expression condition;

    private CompoundStatementNode body;


    public WhileLoopNode(Expression condition, CompoundStatementNode body) {
        this.condition = condition;
        this.body = body;
    }
}
