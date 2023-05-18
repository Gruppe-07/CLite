package org.example.astnodes;

import org.example.AstVisitor;

public class ExternalDeclarationNode extends AstNode {
    public AstNode funcDefOrDecl;
    public ExternalDeclarationNode(AstNode funcDefOrDecl) {
        this.funcDefOrDecl = funcDefOrDecl;
    }

    public AstNode getFuncDefOrDecl() {
        return funcDefOrDecl;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitExternalDeclarationNode(this);
    }
}
