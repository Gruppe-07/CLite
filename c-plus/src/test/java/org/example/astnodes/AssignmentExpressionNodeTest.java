package org.example.astnodes;

import org.example.AstVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentExpressionNodeTest {

    ExpressionNode leftTest = new ExpressionNode() {};
    ExpressionNode rightTest = new ExpressionNode() {};

    AssignmentExpressionNode AENode = new AssignmentExpressionNode(leftTest, rightTest);


    @Test
    void SetNewLeftRight(){
        ExpressionNode NewLeft = new ExpressionNode() {};
        ExpressionNode NewRight = new ExpressionNode() {};
        this.leftTest = NewLeft;
        this.rightTest = NewRight;

        assertEquals(this.leftTest, NewLeft);
        assertEquals(this.rightTest, NewRight);
    }

    @Test
    void getLeft() {
        assertEquals(leftTest, AENode.getLeft());
    }

    @Test
    void getRight() {
        assertEquals(rightTest, AENode.getRight() );
    }
}