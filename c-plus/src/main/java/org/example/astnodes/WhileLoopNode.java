package org.example.astnodes;

import org.example.AstVisitor;

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

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitWhileLoopNode(this);
    }
}
