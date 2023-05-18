package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class EqualityExpressionNode extends BinaryExpressionNode {


    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitEqualityExpressionNode(this);
    }
}

