package org.example.astnodes;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FunctionCallNodeTest {

    ParameterDeclarationNode PDNode = new ParameterDeclarationNode();
    IdentifierNode IdNode = new IdentifierNode("Bob");
    FunctionCallNode FCNode = new FunctionCallNode(IdNode);


    @Test
    void TestFunctionCallNode() {
        //assertEquals();
    }
    @Test
    void getIdentifierNode() {
        assertEquals(IdNode, FCNode.getIdentifierNode());
    }

    @Test
    void getCallValue() {
        assertEquals(FCNode.callValue, FCNode.getCallValue());
    }
}