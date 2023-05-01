package org.example.astnodes;

public class FunctionCallNode extends PostFixExpression {
    public IdentifierNode name;
    public Expression callValue;

    public IdentifierNode getName() {
        return name;
    }

    public void setName(IdentifierNode name) {
        this.name = name;
    }

    public Expression getCallValue() {
        return callValue;
    }

    public void setCallValue(Expression callValue) {
        this.callValue = callValue;
    }
}
