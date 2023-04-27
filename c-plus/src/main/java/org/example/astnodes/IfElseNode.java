package org.example.astnodes;

public class IfElseNode extends StatementNode {
    public ExpressionNode condition;
    public BlockNode ifBranch;
    public BlockNode elseBranch;

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public BlockNode getIfBranch() {
        return ifBranch;
    }

    public void setIfBranch(BlockNode ifBranch) {
        this.ifBranch = ifBranch;
    }

    public BlockNode getElseBranch() {
        return elseBranch;
    }

    public void setElseBranch(BlockNode elseBranch) {
        this.elseBranch = elseBranch;
    }
}
