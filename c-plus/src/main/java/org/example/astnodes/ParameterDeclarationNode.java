package org.example.astnodes;

import org.example.AstVisitor;

public class ParameterDeclarationNode extends AstNode {
    public TypeSpecifierNode type;
    public IdentifierNode identifierNode;

    public ParameterDeclarationNode(TypeSpecifierNode type, IdentifierNode name) {
        this.type = type;
        this.identifierNode = name;
    }

    public ParameterDeclarationNode() {
    }

    public TypeSpecifierNode getType() {
        return type;
    }

    public void setType(TypeSpecifierNode type) {
        this.type = type;
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public void setIdentifierNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitParameterDeclarationNode(this);
    }
}
