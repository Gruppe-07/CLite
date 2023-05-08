package org.example.astnodes;
import org.example.AstVisitor;

public abstract class StatementNode extends BlockItemNode {
    abstract void accept(AstVisitor visitor);
}

