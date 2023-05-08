package org.example.astnodes;

public class IdentifierNode extends ExpressionNode {
    public String name;
    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
