package org.example.astnodes;

public class EqualityExpressionNode extends BinaryExpression {
    public RelationalExpressionNode left;
    public RelationalExpressionNode right;

    public EqualityExpressionNode(String operator) {
        super(operator);
    }
}

