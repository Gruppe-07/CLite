package org.example.astnodes;

public class LogicalOrExpressionNode extends BinaryExpressionNode {
    public LogicalAndExpressionNode left;
    public LogicalAndExpressionNode right;

    public LogicalOrExpressionNode(String operator) {
        super(operator);
    }
}
