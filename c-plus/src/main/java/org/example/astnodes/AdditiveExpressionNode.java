package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class AdditiveExpressionNode extends ExpressionNode {
    public List<ExpressionNode> Operands;
    public List<String> Operators;

    public AdditiveExpressionNode(List<ExpressionNode> operands, List<String> operators) {
        Operands = operands;
        Operators = operators;
    }


    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitAdditiveExpressionNode(this);
    }
}

