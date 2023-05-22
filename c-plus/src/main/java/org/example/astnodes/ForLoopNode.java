package org.example.astnodes;

import org.example.AstVisitor;

public class ForLoopNode extends StatementNode{
    public DeclarationNode initialization;
    public ExpressionNode condition;
    public PostFixExpressionNode update;

    public CompoundStatementNode body;

    public ForLoopNode(DeclarationNode initialization, ExpressionNode condition, PostFixExpressionNode update, CompoundStatementNode body) {
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    public DeclarationNode getInitialization() {
        return initialization;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public PostFixExpressionNode getUpdate() {
        return update;
    }

    public CompoundStatementNode getBody() {
        return body;
    }

    @Override
    public Object accept(AstVisitor visitor) { return visitor.visitForLoopNode(this); }
}
