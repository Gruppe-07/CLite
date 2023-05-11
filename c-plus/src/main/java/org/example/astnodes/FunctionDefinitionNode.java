package org.example.astnodes;

import org.example.AstVisitor;

public class FunctionDefinitionNode extends AstNode {
    public IdentifierNode identifierNode;
    public ParameterDeclarationNode parameter;
    public CompoundStatementNode body;

    public FunctionDefinitionNode(IdentifierNode identifierNode, ParameterDeclarationNode parameter, CompoundStatementNode body) {
        this.identifierNode = identifierNode;
        this.parameter = parameter;
        this.body = body;
    }

    public FunctionDefinitionNode(IdentifierNode identifierNode, CompoundStatementNode body) {
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
    public void accept(AstVisitor visitor) {
        visitor.visitFunctionDefinitionNode(this);
    }
}


