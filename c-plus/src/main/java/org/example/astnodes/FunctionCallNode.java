package org.example.astnodes;

import org.example.AstVisitor;

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


    public void accept(AstVisitor visitor) {
        visitor.visitFunctionCallNode(this);
    }
}
