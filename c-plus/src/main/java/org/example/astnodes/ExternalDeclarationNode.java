package org.example.astnodes;

public class ExternalDeclarationNode extends AstNode {
    public AstNode funcDefOrDecl;
    public ExternalDeclarationNode(AstNode funcDefOrDecl) {
        this.funcDefOrDecl = funcDefOrDecl;
    }
}
