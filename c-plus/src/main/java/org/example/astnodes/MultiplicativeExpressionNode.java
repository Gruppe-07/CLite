package org.example.astnodes;
import org.example.AstVisitor;

import java.util.List;

public class MultiplicativeExpressionNode extends ExpressionNode {
    public List<ExpressionNode> Operands;
    public List<String> Operators;

    public MultiplicativeExpressionNode(List<ExpressionNode> operands, List<String> operators) {
        Operands = operands;
        Operators = operators;
    }

    public void accept(AstVisitor visitor){
        visitor.visitMultiplicativeExpressionNode(this);
    }
}

