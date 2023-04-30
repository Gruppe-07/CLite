package org.example.astnodes;

public class MultiplicativeExpressionNode extends BinaryExpressionNode {
    public UnaryExpressionNode left;
    public UnaryExpressionNode right;


    public MultiplicativeExpressionNode(String operator) {
        super(operator);
    }
}

