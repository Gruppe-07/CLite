package org.example.astnodes;

public class ParameterNode extends DeclarationNode {
    public TypeSpecifierNode type;
    public IdentifierNode name;

    public ParameterNode(TypeSpecifierNode type, IdentifierNode name) {
        this.type = type;
        this.name = name;
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
}
