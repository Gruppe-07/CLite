package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;
import java.util.Objects;

public class CompoundStatementNode extends StatementNode {
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

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitCompoundStatementNode(this);
    }
}
