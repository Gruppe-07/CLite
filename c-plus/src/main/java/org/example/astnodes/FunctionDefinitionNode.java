package org.example.astnodes;

import org.example.AstVisitor;

public class FunctionDefinitionNode extends AstNode {
    public TypeSpecifierNode typeSpecifierNode;
    public IdentifierNode identifierNode;
    public ParameterDeclarationNode parameter;
    public CompoundStatementNode body;

    public FunctionDefinitionNode(TypeSpecifierNode typeSpecifierNode, IdentifierNode identifierNode, ParameterDeclarationNode parameter, CompoundStatementNode body) {
        this.typeSpecifierNode = typeSpecifierNode;
        this.identifierNode = identifierNode;
        this.parameter = parameter;
        this.body = body;
    }

    public FunctionDefinitionNode(TypeSpecifierNode typeSpecifierNode, IdentifierNode identifierNode, CompoundStatementNode body) {
        this.typeSpecifierNode = typeSpecifierNode;
        this.identifierNode = identifierNode;
        this.body = body;
    }


    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public ParameterDeclarationNode getParameter() {
        return parameter;
    }

    public CompoundStatementNode getBody() {
        return body;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitFunctionDefinitionNode(this);
    }

    public TypeSpecifierNode getTypeSpecifierNode() {
        return typeSpecifierNode;
    }
}


