package org.example.astnodes;

public class IdentifierNode extends ExpressionNode {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
}
