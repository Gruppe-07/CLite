package org.example.astnodes;

public abstract class BinaryExpressionNode extends ExpressionNode {

    private String operator;

    public BinaryExpressionNode(String operator) {
        this.operator = operator;
    }
}

