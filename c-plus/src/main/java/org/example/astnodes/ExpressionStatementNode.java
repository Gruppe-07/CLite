package org.example.astnodes;

import org.example.AstVisitor;

public class ExpressionStatementNode extends StatementNode {
    public ExpressionNode expressionNode;
    public ExpressionStatementNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitExpressionStatementNode(this);
    }
}
