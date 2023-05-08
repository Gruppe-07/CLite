package org.example.astnodes;

import org.example.AstVisitor;

public class TypeSpecifierNode extends AstNode {
    private String type;

    public TypeSpecifierNode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitTypeSpecifierNode(this);
    }
}
