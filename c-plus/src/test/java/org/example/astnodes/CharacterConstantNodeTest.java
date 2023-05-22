package org.example.astnodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterConstantNodeTest {

    CharacterConstantNode CCNodeTest = new CharacterConstantNode("value");

    @Test
    void TestGetValue() {
        assertEquals("value", CCNodeTest.getValue());
    }

    @Test
    void TestSetValue() {
        CCNodeTest.setValue("test2");
        assertEquals("test2", CCNodeTest.getValue());
    }
}