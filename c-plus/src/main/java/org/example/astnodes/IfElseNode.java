package org.example.astnodes;

public class IfElseNode extends StatementNode {
    public ExpressionNode condition;
    private BlockNode ifBranch;
    private BlockNode elseBranch;
}
