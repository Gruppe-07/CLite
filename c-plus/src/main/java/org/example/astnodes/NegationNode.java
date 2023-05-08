package org.example.astnodes;

import org.example.AstVisitor;

public class NegationNode extends UnaryExpressionNode {
    public ExpressionNode innerExpressionNode;

    public NegationNode(ExpressionNode innerExpressionNode) {
        this.innerExpressionNode = innerExpressionNode;
    }

    public void accept(AstVisitor visitor){
        visitor.visitNegationNode(this);
    }
}
