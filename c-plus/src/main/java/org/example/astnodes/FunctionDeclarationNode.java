package org.example.astnodes;

public class FunctionDeclarationNode extends DeclarationNode {
    public IdentifierNode name;
    public ParameterNode parameter;
    public CompoundStatementNode body;

    public FunctionDeclarationNode(IdentifierNode name, ParameterNode parameter, CompoundStatementNode body) {
        this.name = name;
        this.parameter = parameter;
        this.body = body;
    }
}


