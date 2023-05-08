package org.example.astnodes;

import org.example.AstVisitor;

public class ExpressionStatementNode extends StatementNode {
    public ExpressionNode expressionNode;
    public ExpressionStatementNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    @Override
    public void accept(AstVisitor visitor) {

    }
}
