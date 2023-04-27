package org.example.astnodes;

public class RelationalExpressionNode extends BinaryExpressionNode {
    public AdditiveExpressionNode left;
    public AdditiveExpressionNode right;

    public RelationalExpressionNode(String operator) {
        super(operator);
    }
}
