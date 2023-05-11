package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

public class DeclarationNode extends BlockItemNode {
    public Boolean isConst;
    public TypeSpecifierNode typeSpecifierNode;
    public IdentifierNode identifierNode;
    public ExpressionNode value;

    public Boolean getConst() {
        return isConst;
    }

    public TypeSpecifierNode getTypeSpecifierNode() {
        return typeSpecifierNode;
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public ExpressionNode getValue() {
        return value;
    }

    public void setConst(Boolean aConst) {
        isConst = aConst;
    }

    public void setTypeSpecifierNode(TypeSpecifierNode typeSpecifierNode) {
        this.typeSpecifierNode = typeSpecifierNode;
    }

    public void setIdentifierNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
    }

    public void setValue(ExpressionNode value) {
        this.value = value;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitDeclarationNode(this);
    }
}


