package org.example.astnodes;

public class FunctionCallNode extends ExpressionNode {
    public IdentifierNode name;
    public ExpressionNode callValue;

    public FunctionCallNode(IdentifierNode name, ExpressionNode callValue) {
        this.name = name;
        this.callValue = callValue;
    }

    public FunctionCallNode(IdentifierNode name) {
        this.name = name;
    }

    public IdentifierNode getName() {
        return name;
    }

    public ExpressionNode getCallValue() {
        return callValue;
    }
}
