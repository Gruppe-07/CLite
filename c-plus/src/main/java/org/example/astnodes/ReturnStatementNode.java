package org.example.astnodes;

import java.util.List;

public class ReturnStatementNode extends StatementNode {
    public ExpressionNode returnValue;

    public ReturnStatementNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }

    public ReturnStatementNode() {
    }


    public ExpressionNode getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }
}

