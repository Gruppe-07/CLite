package org.example.astnodes;

public class AssignmentExpressionNode extends ExpressionNode {
    public ExpressionNode left;
    public ExpressionNode right;

    public AssignmentExpressionNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }
}
