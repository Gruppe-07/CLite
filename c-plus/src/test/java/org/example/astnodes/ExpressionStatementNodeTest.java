package org.example.astnodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionStatementNodeTest {

    ExpressionNode ENode = new ExpressionNode() {};
    ExpressionStatementNode ESNode = new ExpressionStatementNode(ENode) {};

    @Test
    void getExpressionNode() {
        assertEquals(ENode, ESNode.getExpressionNode());
    }
}