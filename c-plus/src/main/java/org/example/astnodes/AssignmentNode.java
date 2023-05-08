package org.example.astnodes;

import org.example.AstVisitor;

public class AssignmentNode extends ExpressionNode {
    public ExpressionNode left;
    public ExpressionNode right;

    public AssignmentNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitAssignmentNode(this);
    }
}
