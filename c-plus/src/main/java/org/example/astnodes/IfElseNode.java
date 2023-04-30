package org.example.astnodes;

public class IfElseNode extends StatementNode {
    public ExpressionNode condition;
    public CompoundStatementNode ifBranch;
    public CompoundStatementNode elseBranch;

}
