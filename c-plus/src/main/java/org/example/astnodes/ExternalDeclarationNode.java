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
    public void accept(AstVisitor visitor) {
        visitor.visitExternalDeclarationNode(this);
    }
}
