package org.example.astnodes;

import org.example.AstVisitor;

public abstract class ConstantNode extends ExpressionNode {
    abstract void accept(AstVisitor visitor);

}


