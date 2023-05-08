package org.example.astnodes;

public class WhileLoopNode extends StatementNode {
    private ExpressionNode condition;
    private CompoundStatementNode body;


    public WhileLoopNode(ExpressionNode condition, CompoundStatementNode body) {
        this.condition = condition;
        this.body = body;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public CompoundStatementNode getBody() {
        return body;
    }
}
