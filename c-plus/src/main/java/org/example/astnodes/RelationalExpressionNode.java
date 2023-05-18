package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class RelationalExpressionNode extends BinaryExpressionNode {

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitRelationalExpressionNode(this);
    }
}
