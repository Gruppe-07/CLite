package org.example.astnodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeSpecifierNodeTest {

    TypeSpecifierNode TestTypeSpecifierNode = new TypeSpecifierNode("TestType");

    @Test
    void TypeIntIsInt() {
        assertEquals("TestType", TestTypeSpecifierNode.getType());
    }
}