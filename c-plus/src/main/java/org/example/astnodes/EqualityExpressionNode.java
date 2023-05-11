package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class EqualityExpressionNode extends ExpressionNode {
    public List<ExpressionNode> Operands;
    public List<String> Operators;

    public EqualityExpressionNode(List<ExpressionNode> operands, List<String> operators) {
        Operands = operands;
        Operators = operators;
    }

    public List<ExpressionNode> getOperands() {
        return Operands;
    }

    public List<String> getOperators() {
        return Operators;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visitEqualityExpressionNode(this);
    }
}

