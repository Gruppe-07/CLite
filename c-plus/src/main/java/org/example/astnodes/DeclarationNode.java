package org.example.astnodes;

import java.util.List;

public class DeclarationNode extends BlockItemNode {
    public Boolean isConst;
    public TypeSpecifierNode typeSpecifierNode;
    public List<IdentifierNode> declaratorNodeList;
    public InitializerNode initializerNode;

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

    public InitializerNode getInitializerNode() {
        return initializerNode;
    }

    public void setInitializerNode(InitializerNode initializerNode) {
        this.initializerNode = initializerNode;
    }

    public List<IdentifierNode> getDeclaratorNodeList() {
        return declaratorNodeList;
    }

    public void setDeclaratorNodeList(List<IdentifierNode> declaratorNodeList) {
        this.declaratorNodeList = declaratorNodeList;
    }
}


