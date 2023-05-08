package org.example.astnodes;

import org.example.AstVisitor;

import java.util.List;

public class InitializerNode extends AstNode {
    List<ExpressionNode> expressionNodeList;

    public InitializerNode(List<ExpressionNode> expressionNodeList) {
        this.expressionNodeList = expressionNodeList;
    }

    public List<ExpressionNode> getExpressionNodeList() {
        return expressionNodeList;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitInitializerNode(this);
    }
}
