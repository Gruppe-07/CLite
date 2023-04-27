package org.example.astnodes;

public class FunctionCallNode extends PostFixExpressionNode {
    public IdentifierNode name;
    public ExpressionNode callValue;

    public BlockNode body;
}
