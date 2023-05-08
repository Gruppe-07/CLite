package org.example.astnodes;

import org.example.AstVisitor;

public abstract class ExpressionNode extends AstNode {
    abstract void accept(AstVisitor visitor);
}

