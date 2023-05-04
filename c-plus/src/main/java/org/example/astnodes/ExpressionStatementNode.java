package org.example.astnodes;

public class ExpressionStatementNode extends StatementNode {
    public ExpressionNode expressionNode;
    public ExpressionStatementNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }
}
