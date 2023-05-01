package org.example.astnodes;

import java.util.List;

public class DeclarationNode extends BlockItem {
    public Boolean isConst;
    public TypeSpecifierNode typeSpecifierNode;
    public List<IdentifierNode> declaratorNodeList;
    public List<Constant> initializerNodeList;

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

    public List<IdentifierNode> getDeclaratorNodeList() {
        return declaratorNodeList;
    }

    public void setDeclaratorNodeList(List<IdentifierNode> declaratorNodeList) {
        this.declaratorNodeList = declaratorNodeList;
    }

    public List<Constant> getInitializerNodeList() {
        return initializerNodeList;
    }

    public void setInitializerNodeList(List<Constant> initializerNodeList) {
        this.initializerNodeList = initializerNodeList;
    }
}


