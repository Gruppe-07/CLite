package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class AdditiveExpressionNode extends BinaryExpressionNode {


    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitAdditiveExpressionNode(this);
    }
}

