package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

public class TranslationUnitNode extends AstNode {
    public List<FunctionDefinitionNode> functionDefinitionNodeList;

    public TranslationUnitNode(List<FunctionDefinitionNode> functionDefinitionNodeList) {
        this.functionDefinitionNodeList = functionDefinitionNodeList;
    }

    public List<FunctionDefinitionNode> getFunctionDefinitionNodeList() {
        return functionDefinitionNodeList;
    }

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitTranslationUnitNode(this);
    }
}

