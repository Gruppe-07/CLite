package org.example.astnodes;

import org.example.AstVisitor;

public class AssignmentExpressionNode extends ExpressionNode {
    public ExpressionNode left;
    public ExpressionNode right;

    public AssignmentExpressionNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitAssignmentExpressionNode(this);
    }
}
