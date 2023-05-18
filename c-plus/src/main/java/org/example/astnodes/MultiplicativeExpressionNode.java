package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class MultiplicativeExpressionNode extends BinaryExpressionNode {

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitMultiplicativeExpressionNode(this);
    }
}

