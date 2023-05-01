package org.example.astnodes;

public class ForLoopNode extends Statement {
    private DeclarationNode initialization;
    private Expression condition;
    private Expression update;
    private CompoundStatementNode body;

    public DeclarationNode getInitialization() {
        return initialization;
    }

    public void setInitialization(DeclarationNode initialization) {
        this.initialization = initialization;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public Expression getUpdate() {
        return update;
    }

    public void setUpdate(Expression update) {
        this.update = update;
    }

    public CompoundStatementNode getBody() {
        return body;
    }

    public void setBody(CompoundStatementNode body) {
        this.body = body;
    }
}

