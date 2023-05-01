package org.example.astnodes;

public class LogicalAndExpressionNode extends BinaryExpression {
    public EqualityExpressionNode left;
    public EqualityExpressionNode right;

    public LogicalAndExpressionNode(String operator) {
        super(operator);
    }
}
