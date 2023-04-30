package org.example.astnodes;

public class IdentifierNode extends ExpressionNode {
    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
}
