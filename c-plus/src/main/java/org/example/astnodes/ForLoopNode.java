package org.example.astnodes;

public class ForLoopNode extends StatementNode {
    private AssignmentNode initialization;
    private ExpressionNode condition;
    private ExpressionNode update;
    private CompoundStatementNode body;

    public AssignmentNode getInitialization() {
        return initialization;
    }

    public void setInitialization(AssignmentNode initialization) {
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
}

