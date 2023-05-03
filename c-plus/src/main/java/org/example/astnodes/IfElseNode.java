package org.example.astnodes;

public class IfElseNode extends StatementNode {
    public ExpressionNode condition;
    public CompoundStatementNode ifBranch;
    public CompoundStatementNode elseBranch;


    public IfElseNode(ExpressionNode condition, CompoundStatementNode ifBranch, CompoundStatementNode elseBranch) {
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }

    public IfElseNode(ExpressionNode condition, CompoundStatementNode ifBranch) {
        this.condition = condition;
        this.ifBranch = ifBranch;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public CompoundStatementNode getIfBranch() {
        return ifBranch;
    }

    public void setIfBranch(CompoundStatementNode ifBranch) {
        this.ifBranch = ifBranch;
    }

    public CompoundStatementNode getElseBranch() {
        return elseBranch;
    }

    public void setElseBranch(CompoundStatementNode elseBranch) {
        this.elseBranch = elseBranch;
    }


}
