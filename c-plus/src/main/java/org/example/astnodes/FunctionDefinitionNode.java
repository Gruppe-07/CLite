package org.example.astnodes;

public class FunctionDefinitionNode extends DeclarationNode {
    public IdentifierNode name;
    public ParameterNode parameter;
    public CompoundStatementNode body;

    public FunctionDefinitionNode(IdentifierNode name, ParameterNode parameter, CompoundStatementNode body) {
        this.name = name;
        this.parameter = parameter;
        this.body = body;
    }
}


