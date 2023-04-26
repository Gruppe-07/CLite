package org.example.astnodes;

public class ForLoopNode extends StatementNode {
    private AssignmentNode initialization;
    private ExpressionNode condition;
    private ExpressionNode update;
    private BlockNode body;
}

