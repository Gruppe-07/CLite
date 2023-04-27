package org.example.astnodes;

public class VariableDeclarationNode extends DeclarationNode {
    public TypeSpecifierNode type;
    public IdentifierNode name;
    public ConstantNode value;

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

    public ConstantNode getValue() {
        return value;
    }

    public void setValue(ConstantNode value) {
        this.value = value;
    }
}
