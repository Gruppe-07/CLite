package org.example.astnodes;

import org.example.AstVisitor;

public abstract class BlockItemNode extends AstNode {

    abstract void accept(AstVisitor visitor);
}
