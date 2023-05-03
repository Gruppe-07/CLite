package org.example.astnodes;

public class ArrayIndexNode extends ExpressionNode {
    public IdentifierNode name;
    public ExpressionNode expressionNode;

    public ArrayIndexNode(IdentifierNode name, ExpressionNode expressionNode) {
        this.name = name;
        this.expressionNode = expressionNode;
    }
}
