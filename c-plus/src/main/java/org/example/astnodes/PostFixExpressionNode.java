package org.example.astnodes;

import org.example.AstVisitor;

public class PostFixExpressionNode extends UnaryExpressionNode {
    public ExpressionNode identifierOrConstant;
    public String operator;

    public PostFixExpressionNode(ExpressionNode identifierOrConstant, String operator) {
        this.identifierOrConstant = identifierOrConstant;
        this.operator = operator;
    }

    public void accept(AstVisitor visitor){
        visitor.visitPostFixExpressionNode(this);
    }
}

