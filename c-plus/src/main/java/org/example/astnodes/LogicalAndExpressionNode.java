package org.example.astnodes;
import java.util.List;

public class LogicalAndExpressionNode extends ExpressionNode {
    public List<ExpressionNode> Operands;
    public List<String> Operators;

    public LogicalAndExpressionNode(List<ExpressionNode> operands, List<String> operators) {
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
