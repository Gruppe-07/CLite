package org.example.astnodes;

import org.example.AstVisitor;

public class FunctionCallNode extends ExpressionNode {
    public IdentifierNode identifierNode;
    public ExpressionNode callValue;

    public FunctionCallNode(IdentifierNode identifierNode, ExpressionNode callValue) {
        this.identifierNode = identifierNode;
        this.callValue = callValue;
    }

    public FunctionCallNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public ExpressionNode getCallValue() {
        return callValue;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitFunctionCallNode(this);
    }
}
