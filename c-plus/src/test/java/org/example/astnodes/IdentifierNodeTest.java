package org.example.astnodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierNodeTest {

    IdentifierNode IdNode = new IdentifierNode("Jakob");
    



    @Test
    void IdNodeTest() {

        Assertions.assertEquals("Jakob", IdNode.getName());

    }

}