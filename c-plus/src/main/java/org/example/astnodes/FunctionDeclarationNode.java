package org.example.astnodes;

public class FunctionDeclarationNode extends DeclarationNode {
    public IdentifierNode name;
    public ParameterNode parameter;
    public BlockNode body;

    public IdentifierNode getName() {
        return name;
    }

    public void setName(IdentifierNode name) {
        this.name = name;
    }

    public ParameterNode getParameter() {
        return parameter;
    }

    public void setParameter(ParameterNode parameter) {
        this.parameter = parameter;
    }

    public BlockNode getBody() {
        return body;
    }

    public void setBody(BlockNode body) {
        this.body = body;
    }
}
