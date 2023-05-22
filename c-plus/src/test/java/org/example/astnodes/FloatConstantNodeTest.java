package org.example.astnodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloatConstantNodeTest {

    FloatConstantNode FloatTest = new FloatConstantNode(1.77);

    @Test
    void TestGetValue() {
        assertEquals(1.77, FloatTest.getValue());
    }

    @Test
    void TestSetValue() {
        FloatTest.setValue(5.66);
        assertEquals(5.66, FloatTest.getValue());
    }
}