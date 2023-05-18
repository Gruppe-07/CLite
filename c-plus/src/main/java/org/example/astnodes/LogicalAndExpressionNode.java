package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class LogicalAndExpressionNode extends BinaryExpressionNode {

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitLogicalAndExpressionNode(this);
    }
}
