package org.example.astnodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryExpressionNodeTest {

    ExpressionNode leftTest = new ExpressionNode() {};
    ExpressionNode rightTest = new ExpressionNode() {};

    BinaryExpressionNode BENode = new BinaryExpressionNode(leftTest, rightTest, "operatorTest"){};

    @Test
    void TestGetLeft() {
        assertEquals(leftTest, BENode.getLeft());
    }

    @Test
    void TestSetLeft() {
        ExpressionNode NewLeft = new ExpressionNode() {};
        BENode.setLeft(NewLeft);
        this.leftTest = NewLeft;

        assertEquals(this.leftTest, NewLeft);
    }

    @Test
    void TestGetRight() {
        assertEquals(rightTest, BENode.getRight());
    }

    @Test
    void TestSetRight() {
        ExpressionNode NewRight = new ExpressionNode() {};
        BENode.setRight(NewRight);
        this.rightTest = NewRight;

        assertEquals(this.rightTest, NewRight);
    }

    @Test
    void TestGetOperator() {
        assertEquals("operatorTest", BENode.getOperator());
    }

    @Test
    void TestSetOperator() {
        BENode.setOperator("NewOperator");

        assertEquals("NewOperator", BENode.operator);
    }
}