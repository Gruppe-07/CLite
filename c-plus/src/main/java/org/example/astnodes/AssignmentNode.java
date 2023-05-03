package org.example.astnodes;

public class AssignmentNode extends StatementNode {

    public TypeSpecifierNode typeSpecifier;
    public IdentifierNode identifierNode;
    public ConstantNode constant;

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

    public ConstantNode getConstant() {
        return constant;
    }

    public void setConstant(ConstantNode constant) {
        this.constant = constant;
    }
}
