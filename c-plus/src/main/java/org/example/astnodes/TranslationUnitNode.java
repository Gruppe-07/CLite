package org.example.astnodes;

import java.util.List;

public class TranslationUnitNode extends AstNode {
    public List<ExternalDeclarationNode> externalDeclarationNodeList;

    public TranslationUnitNode(List<ExternalDeclarationNode> externalDeclarationNodeList) {
        this.externalDeclarationNodeList = externalDeclarationNodeList;
    }
}

