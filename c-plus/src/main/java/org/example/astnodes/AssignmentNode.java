package org.example.astnodes;

public class AssignmentNode extends ExpressionNode {
    public ExpressionNode left;
    public ExpressionNode right;

    public AssignmentNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }
}
