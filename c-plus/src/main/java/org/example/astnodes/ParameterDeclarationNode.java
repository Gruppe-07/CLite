package org.example.astnodes;

import org.example.AstVisitor;

public class ParameterDeclarationNode extends DeclarationNode {
    public TypeSpecifierNode type;
    public IdentifierNode name;

    public ParameterDeclarationNode(TypeSpecifierNode type, IdentifierNode name) {
        this.type = type;
        this.name = name;
    }

    public ParameterDeclarationNode() {
    }

    public TypeSpecifierNode getType() {
        return type;
    }

    public void setType(TypeSpecifierNode type) {
        this.type = type;
    }

    public IdentifierNode getName() {
        return name;
    }

    public void setName(IdentifierNode name) {
        this.name = name;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitParameterDeclarationNode(this);
    }
}
