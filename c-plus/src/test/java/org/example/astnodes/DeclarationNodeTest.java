package org.example.astnodes;

import org.example.AstVisitor;
import org.example.astnodes.DeclarationNode;
import org.example.astnodes.TypeSpecifierNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeclarationNodeTest {

    DeclarationNode DclNode = new DeclarationNode();
    Boolean isConstant = Boolean.parseBoolean(String.valueOf(1));
    TypeSpecifierNode Jakob = new TypeSpecifierNode("int");

    IdentifierNode IdNode = new IdentifierNode("Id123");
    ExpressionNode value = new ExpressionNode() {
        @Override
        public Object accept(AstVisitor visitor) {
            return super.accept(visitor);
        }
    };


    @Test
    void settingTypeSpecifierNodeTest() {

      DclNode.setTypeSpecifierNode(Jakob);
      Assertions.assertEquals("int", DclNode.getTypeSpecifierNode().getType());
    }

    @Test
    void settingIdentifierNodeTest(){
        DclNode.setIdentifierNode(IdNode);
        Assertions.assertEquals("Id123", DclNode.getIdentifierNode().getName());
    }
    @Test
    void valueTest(){
        DclNode.setValue(value);
        Assertions.assertEquals(DclNode.getValue(),value);
    }
    @Test
    void isConstTest(){
        DclNode.setConst(isConstant);
        Assertions.assertEquals(isConstant,DclNode.getConst());
    }
}
