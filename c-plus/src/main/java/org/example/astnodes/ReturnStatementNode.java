package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

public class ReturnStatementNode extends StatementNode {
    public ExpressionNode returnValue;

    public ReturnStatementNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }


    public ExpressionNode getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }

    public void accept(AstVisitor visitor){
        visitor.visitReturnStatementNode(this);
    }
}

