package org.example.astnodes;

import java.util.List;

public class DeclarationNode extends BlockItemNode {
    public Boolean isConst;
    public TypeSpecifierNode typeSpecifierNode;
    public List<IdentifierNode> declaratorNodeList;
    public List<ExpressionNode> initializerNodeList;

    public DeclarationNode() {
    }

    public Boolean getConst() {
        return isConst;
    }

    public void setConst(Boolean aConst) {
        isConst = aConst;
    }


    public TypeSpecifierNode getTypeSpecifierNode() {
        return typeSpecifierNode;
    }

    public void setTypeSpecifierNode(TypeSpecifierNode typeSpecifierNode) {
        this.typeSpecifierNode = typeSpecifierNode;
    }

    public List<ExpressionNode> getInitializerNodeList() {
        return initializerNodeList;
    }

    public void setInitializerNodeList(List<ExpressionNode> initializerNodeList) {
        this.initializerNodeList = initializerNodeList;
    }
}


