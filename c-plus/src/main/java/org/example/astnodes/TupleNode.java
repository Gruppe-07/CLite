package org.example.astnodes;

import org.example.AstVisitor;

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

    public void accept(AstVisitor visitor){
        visitor.visitTupleNode(this);
    }
}
