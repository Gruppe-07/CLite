package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class EqualityExpressionNode extends BinaryExpressionNode {


    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitEqualityExpressionNode(this);
    }
}

