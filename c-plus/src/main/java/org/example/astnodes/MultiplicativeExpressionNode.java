package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class MultiplicativeExpressionNode extends BinaryExpressionNode {

    @Override
    public Object accept(AstVisitor visitor) {
        return visitor.visitMultiplicativeExpressionNode(this);
    }
}

