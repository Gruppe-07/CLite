package org.example.astnodes;

public class FunctionCallNode extends PostFixExpressionNode {
    public IdentifierNode name;
    public ExpressionNode callValue;

    public IdentifierNode getName() {
        return name;
    }

    public void setName(IdentifierNode name) {
        this.name = name;
    }

    public ExpressionNode getCallValue() {
        return callValue;
    }

    public void setCallValue(ExpressionNode callValue) {
        this.callValue = callValue;
    }
}
