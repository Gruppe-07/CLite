package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

public class ArrayDeclarationNode extends DeclarationNode {
    public IdentifierNode name;
    public TypeSpecifierNode generic;
    public List<ExpressionNode> values;


    public void accept(AstVisitor visitor) {
        visitor.visitArrayDeclarationNode(this);
    }
}


