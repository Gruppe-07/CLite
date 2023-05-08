package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

public class TupleDeclarationNode extends DeclarationNode {
    public IdentifierNode name;
    public List<ConstantNode> values;

    public void accept(AstVisitor visitor){
        visitor.visitTupleDeclarationNode(this);
    }
}
