package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class LogicalOrExpressionNode extends BinaryExpressionNode {

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitLogicalOrExpressionNode(this);
    }
}
