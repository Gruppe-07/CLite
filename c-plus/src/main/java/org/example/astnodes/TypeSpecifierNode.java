package org.example.astnodes;

public class TypeSpecifierNode extends AstNode {
    private String type;

    public TypeSpecifierNode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
