package org.example.astnodes;

import org.example.AstVisitor;

public abstract class UnaryExpressionNode extends ExpressionNode {
    abstract void accept(AstVisitor visitor);
}