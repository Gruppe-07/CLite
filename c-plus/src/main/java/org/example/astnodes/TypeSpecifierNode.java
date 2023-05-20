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
    public Object accept(AstVisitor visitor) {
        return visitor.visitTypeSpecifierNode(this);
    }
}
