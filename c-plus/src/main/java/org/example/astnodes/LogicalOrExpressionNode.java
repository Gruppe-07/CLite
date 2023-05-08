package org.example.astnodes;
import java.util.List;

public class LogicalOrExpressionNode extends ExpressionNode {
    public List<ExpressionNode> Operands;
    public List<String> Operators;

    public LogicalOrExpressionNode(List<ExpressionNode> operands, List<String> operators) {
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
