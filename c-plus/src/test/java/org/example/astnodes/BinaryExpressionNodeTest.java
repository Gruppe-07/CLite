package org.example.astnodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryExpressionNodeTest {

    ExpressionNode leftTest = new ExpressionNode() {};
    ExpressionNode rightTest = new ExpressionNode() {};

    BinaryExpressionNode BENode = new BinaryExpressionNode(leftTest, rightTest, "operatorTest"){};

    @Test
    void getLeft() {
    }

    @Test
    void setLeft() {
    }

    @Test
    void getRight() {
    }

    @Test
    void setRight() {
    }

    @Test
    void getOperator() {
    }

    @Test
    void setOperator() {
    }
}