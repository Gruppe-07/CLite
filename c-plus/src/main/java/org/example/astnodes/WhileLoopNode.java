package org.example.astnodes;

import org.example.AstVisitor;

public class WhileLoopNode extends StatementNode {

    private ExpressionNode condition;

    private CompoundStatementNode body;


    public WhileLoopNode(ExpressionNode condition, CompoundStatementNode body) {
        this.condition = condition;
        this.body = body;
    }

    public void accept(AstVisitor visitor){
        visitor.visitWhileLoopNode(this);
    }
}
