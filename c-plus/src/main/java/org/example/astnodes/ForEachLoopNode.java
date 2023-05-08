package org.example.astnodes;

import org.example.AstVisitor;

public class ForEachLoopNode extends StatementNode {
    public TypeSpecifierNode typeSpecifierNode;
    public IdentifierNode elementIdentifierNode;
    public IdentifierNode arrayIdentifierNode;

    public CompoundStatementNode body;


    public ForEachLoopNode(TypeSpecifierNode typeSpecifierNode, IdentifierNode elementIdentifierNode, IdentifierNode arrayIdentifierNode, CompoundStatementNode body) {
        this.typeSpecifierNode = typeSpecifierNode;
        this.elementIdentifierNode = elementIdentifierNode;
        this.arrayIdentifierNode = arrayIdentifierNode;
        this.body = body;
    }

    public TypeSpecifierNode getTypeSpecifierNode() {
        return typeSpecifierNode;
    }

    public void setTypeSpecifierNode(TypeSpecifierNode typeSpecifierNode) {
        this.typeSpecifierNode = typeSpecifierNode;
    }

    public IdentifierNode getElementIdentifier() {
        return elementIdentifierNode;
    }

    public void setElementIdentifier(IdentifierNode elementIdentifierNode) {
        this.elementIdentifierNode = elementIdentifierNode;
    }

    public IdentifierNode getArrayIdentifier() {
        return arrayIdentifierNode;
    }

    public void setArrayIdentifier(IdentifierNode arrayIdentifierNode) {
        this.arrayIdentifierNode = arrayIdentifierNode;
    }

    public CompoundStatementNode getBody() {
        return body;
    }

    public void setBody(CompoundStatementNode body) {
        this.body = body;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitForEachLoopNode(this);
    }
}
