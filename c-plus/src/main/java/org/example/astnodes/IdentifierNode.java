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

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitIdentifierNode(this);
    }
}
