package org.example.astnodes;

import java.util.List;

public class TupleNode extends ExpressionNode {
    List<ExpressionNode> values;

    public TupleNode(List<ExpressionNode> values) {
        this.values = values;
    }

    public List<ExpressionNode> getValues() {
        return values;
    }

    public void setValues(List<ExpressionNode> values) {
        this.values = values;
    }
}
