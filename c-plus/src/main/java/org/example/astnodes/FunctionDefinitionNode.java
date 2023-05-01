package org.example.astnodes;

public class FunctionDefinitionNode extends AstNode {
    public IdentifierNode name;
    public ParameterDeclarationNode parameter;
    public CompoundStatementNode body;

    public FunctionDefinitionNode(IdentifierNode name, ParameterDeclarationNode parameter, CompoundStatementNode body) {
        this.name = name;
        this.parameter = parameter;
        this.body = body;
    }
}


