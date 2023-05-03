package org.example.astnodes;

public class ReturnStatementNode extends StatementNode {
    public ExpressionNode returnValue;

    public ReturnStatementNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }

    public ReturnStatementNode() {

    }
}
