package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class RelationalExpressionNode extends BinaryExpressionNode {

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitRelationalExpressionNode(this);
    }
}
