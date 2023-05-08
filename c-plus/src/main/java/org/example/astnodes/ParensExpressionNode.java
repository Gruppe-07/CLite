package org.example.astnodes;

import org.example.AstVisitor;

public class ParensExpressionNode extends ExpressionNode{
    public ExpressionNode innerExpressionNode;

    public ParensExpressionNode(ExpressionNode innerExpressionNode) {
        this.innerExpressionNode = innerExpressionNode;
    }

    public void accept(AstVisitor visitor){
        visitor.visitParensExpressionNode(this);
    }
}
