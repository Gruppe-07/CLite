package org.example.astnodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerConstantNodeTest {

    IntegerConstantNode ICNode = new IntegerConstantNode(10);

    @Test
    void TestGetValue() {
        assertEquals(10, ICNode.getValue());
    }

    @Test
    void TestSetValue() {
        ICNode.setValue(25);
        assertEquals(25, this.ICNode.value);
    }
}