package org.example.astnodes;

import org.example.AstVisitor;

public class ForLoopNode extends StatementNode {
    private DeclarationNode initialization;
    private ExpressionNode condition;
    private ExpressionNode update;
    private CompoundStatementNode body;

    public DeclarationNode getInitialization() {
        return initialization;
    }

    public void setInitialization(DeclarationNode initialization) {
        this.initialization = initialization;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public ExpressionNode getUpdate() {
        return update;
    }

    public void setUpdate(ExpressionNode update) {
        this.update = update;
    }

    public CompoundStatementNode getBody() {
        return body;
    }

    public void setBody(CompoundStatementNode body) {
        this.body = body;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitForLoopNode(this);
    }
}

