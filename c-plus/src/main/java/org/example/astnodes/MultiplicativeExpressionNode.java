package org.example.astnodes;

public class MultiplicativeExpressionNode extends BinaryExpression {
    public UnaryExpression left;
    public UnaryExpression right;


    public MultiplicativeExpressionNode(String operator) {
        super(operator);
    }
}

