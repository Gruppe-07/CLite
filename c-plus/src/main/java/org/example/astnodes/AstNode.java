package org.example.astnodes;

import org.example.AstVisitor;

public abstract class AstNode {

    public Object accept(AstVisitor visitor){ return null;}
}
