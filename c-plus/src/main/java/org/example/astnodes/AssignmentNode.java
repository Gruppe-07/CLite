package org.example.astnodes;

public class AssignmentNode extends Statement {

    public TypeSpecifierNode typeSpecifier;
    public IdentifierNode identifierNode;
    public Constant constant;

    public TypeSpecifierNode getTypeSpecifier() {
        return typeSpecifier;
    }

    public void setTypeSpecifier(TypeSpecifierNode typeSpecifier) {
        this.typeSpecifier = typeSpecifier;
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public void setIdentifierNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
    }

    public Constant getConstant() {
        return constant;
    }

    public void setConstant(Constant constant) {
        this.constant = constant;
    }
}
