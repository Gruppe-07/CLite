package org.example.astnodes;

public class AdditiveExpressionNode extends BinaryExpressionNode {
    public MultiplicativeExpressionNode left;
    public MultiplicativeExpressionNode right;

    public AdditiveExpressionNode(String operator) {
        super(operator);
    }
}