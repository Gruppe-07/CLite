package org.example.astnodes;

import org.example.AstVisitor;

public class IdentifierNode extends ExpressionNode {
    public String name;
    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitIdentifierNode(this);
    }
}
