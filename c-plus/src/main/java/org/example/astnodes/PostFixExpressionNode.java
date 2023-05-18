package org.example.astnodes;

import org.example.AstVisitor;

public class PostFixExpressionNode extends UnaryExpressionNode {
    public ExpressionNode identifierOrConstant;
    public String operator;

    public PostFixExpressionNode(ExpressionNode identifierOrConstant, String operator) {
        this.identifierOrConstant = identifierOrConstant;
        this.operator = operator;
    }

    public ExpressionNode getIdentifierOrConstant() {
        return identifierOrConstant;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitPostFixExpressionNode(this);
    }
}

