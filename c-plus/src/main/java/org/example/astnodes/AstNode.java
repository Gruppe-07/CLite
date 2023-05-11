package org.example.astnodes;

import org.example.AstVisitor;

public abstract class AstNode {

    public void accept(AstVisitor visitor){}
}
