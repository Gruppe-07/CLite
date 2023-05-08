package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

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
    public void accept(AstVisitor visitor) {
        visitor.visitCompoundStatementNode(this);
    }
}
