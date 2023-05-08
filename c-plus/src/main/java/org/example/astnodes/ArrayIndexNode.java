package org.example.astnodes;

import org.example.AstVisitor;

public class ArrayIndexNode extends ExpressionNode {
    public IdentifierNode name;
    public ExpressionNode expressionNode;

    public ArrayIndexNode(IdentifierNode name, ExpressionNode expressionNode) {
        this.name = name;
        this.expressionNode = expressionNode;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitArrayIndexNode(this);
    }
}
