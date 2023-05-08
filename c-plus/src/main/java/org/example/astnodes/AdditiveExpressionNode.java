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


    public List<ExpressionNode> getOperands() {
        return Operands;
    }

    public List<String> getOperators() {
        return Operators;
    }
}

