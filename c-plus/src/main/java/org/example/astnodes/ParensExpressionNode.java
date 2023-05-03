package org.example.astnodes;

public class ParensExpressionNode extends ExpressionNode{
    public ExpressionNode innerExpressionNode;

    public ParensExpressionNode(ExpressionNode innerExpressionNode) {
        this.innerExpressionNode = innerExpressionNode;
    }
}
