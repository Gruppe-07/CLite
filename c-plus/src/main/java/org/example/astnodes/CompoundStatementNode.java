package org.example.astnodes;

import java.util.List;

public class CompoundStatementNode extends AstNode {
    public List<BlockItemNode> blockItemNodeList;

    public List<BlockItemNode> getBlockItemNodeList() {
        return blockItemNodeList;
    }

    public void setBlockItemNodeList(List<BlockItemNode> blockItemNodeList) {
        this.blockItemNodeList = blockItemNodeList;
    }
}
