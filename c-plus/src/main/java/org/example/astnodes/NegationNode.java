package org.example.astnodes;

public class NegationNode extends UnaryExpressionNode {
    public ExpressionNode innerExpressionNode;

    public NegationNode(ExpressionNode innerExpressionNode) {
        this.innerExpressionNode = innerExpressionNode;
    }

    public ExpressionNode getInnerExpressionNode() {
        return innerExpressionNode;
    }
}
