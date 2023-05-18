package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class LogicalAndExpressionNode extends BinaryExpressionNode {

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitLogicalAndExpressionNode(this);
    }
}
