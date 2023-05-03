package org.example.astnodes;

public class PostFixExpressionNode extends UnaryExpressionNode {
    public ExpressionNode identifierOrConstant;
    public String operator;

    public PostFixExpressionNode(ExpressionNode identifierOrConstant, String operator) {
        this.identifierOrConstant = identifierOrConstant;
        this.operator = operator;
    }
}

