package org.example.astnodes;

import java.util.List;

public class ReturnStatementNode extends StatementNode {
    public List<ExpressionNode> returnValue;

    public ReturnStatementNode(List<ExpressionNode> returnValue) {
        this.returnValue = returnValue;
    }

    public List<ExpressionNode> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ExpressionNode> returnValue) {
        this.returnValue = returnValue;
    }
}

