package org.example.astnodes;

import org.example.AstVisitor;

public class NegationNode extends UnaryExpressionNode {
    public ExpressionNode innerExpressionNode;

    public NegationNode(ExpressionNode innerExpressionNode) {
        this.innerExpressionNode = innerExpressionNode;
    }

    public ExpressionNode getInnerExpressionNode() {
        return innerExpressionNode;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitNegationNode(this);
    }
}
