package org.example.astnodes;

import org.example.astnodes.DeclarationNode;
import org.example.astnodes.TypeSpecifierNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeclarationNodeTest {

    DeclarationNode DclNode = new DeclarationNode();
    TypeSpecifierNode Jakob = new TypeSpecifierNode("int");



    @Test
    void typeSpecifierNodeTest() {


      DclNode.setTypeSpecifierNode(Jakob);
        Assertions.assertEquals("int", DclNode.getTypeSpecifierNode().getType());
    }

    //more tests

}
