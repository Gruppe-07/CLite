package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class LogicalOrExpressionNode extends BinaryExpressionNode {

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitLogicalOrExpressionNode(this);
    }
}
