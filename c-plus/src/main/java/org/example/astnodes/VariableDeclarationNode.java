package org.example.astnodes;

public class VariableDeclarationNode extends DeclarationNode {
    public Boolean isConst;
    public TypeSpecifierNode type;
    public IdentifierNode name;
    public Constant value;

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

    public Constant getValue() {
        return value;
    }

    public void setValue(Constant value) {
        this.value = value;
    }

    public Boolean getConst() {
        return isConst;
    }

    public void setConst(Boolean aConst) {
        isConst = aConst;
    }
}
