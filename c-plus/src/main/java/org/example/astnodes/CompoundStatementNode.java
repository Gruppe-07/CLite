package org.example.astnodes;

import java.util.List;

public class CompoundStatementNode extends AstNode {
    public List<BlockItem> blockItemList;

    public CompoundStatementNode(List<BlockItem> blockItemList) {
        this.blockItemList = blockItemList;
    }


    public List<BlockItem> getBlockItemNodeList() {
        return blockItemList;
    }

    public void setBlockItemNodeList(List<BlockItem> blockItemList) {
        this.blockItemList = blockItemList;
    }
}
