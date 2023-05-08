package org.example.astnodes;

import org.example.AstVisitor;

public abstract class AstNode {
    abstract void accept(AstVisitor visitor);
}
