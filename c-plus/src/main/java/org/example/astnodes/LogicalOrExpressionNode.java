package org.example.astnodes;

public class LogicalOrExpressionNode extends BinaryExpression {
    public LogicalAndExpressionNode left;
    public LogicalAndExpressionNode right;

    public LogicalOrExpressionNode(String operator) {
        super(operator);
    }
}
