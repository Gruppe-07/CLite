package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

public class TranslationUnitNode extends AstNode {
    public List<ExternalDeclarationNode> externalDeclarationNodeList;

    public TranslationUnitNode(List<ExternalDeclarationNode> externalDeclarationNodeList) {
        this.externalDeclarationNodeList = externalDeclarationNodeList;
    }

    public List<ExternalDeclarationNode> getExternalDeclarationNodeList() {
        return externalDeclarationNodeList;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitTranslationUnitNode(this);
    }
}

