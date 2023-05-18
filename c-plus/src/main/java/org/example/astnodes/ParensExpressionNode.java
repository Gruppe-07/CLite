package org.example.astnodes;

import org.example.AstVisitor;

public class ParensExpressionNode extends ExpressionNode{
    public ExpressionNode innerExpressionNode;

    public ParensExpressionNode(ExpressionNode innerExpressionNode) {
        this.innerExpressionNode = innerExpressionNode;
    }

    public ExpressionNode getInnerExpressionNode() {
        return innerExpressionNode;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitParensExpressionNode(this);
    }
}
