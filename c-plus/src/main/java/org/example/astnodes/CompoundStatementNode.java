package org.example.astnodes;

import java.util.List;

public class CompoundStatementNode extends AstNode {
    public List<BlockItemNode> blockItemList;

    public CompoundStatementNode(List<BlockItemNode> blockItemList) {
        this.blockItemList = blockItemList;
    }


    public List<BlockItemNode> getBlockItemNodeList() {
        return blockItemList;
    }

    public void setBlockItemNodeList(List<BlockItemNode> blockItemList) {
        this.blockItemList = blockItemList;
    }
}
