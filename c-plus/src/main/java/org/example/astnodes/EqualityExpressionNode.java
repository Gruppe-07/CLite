package org.example.astnodes;

public class EqualityExpressionNode extends BinaryExpressionNode {
    public RelationalExpressionNode left;
    public RelationalExpressionNode right;

    public EqualityExpressionNode(String operator) {
        super(operator);
    }
}

