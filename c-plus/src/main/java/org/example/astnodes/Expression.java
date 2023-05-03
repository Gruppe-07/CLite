package org.example.astnodes;

import java.util.List;

public abstract class Expression extends AstNode {
    private String operator;
    private List<Expression> expressionList;

    public Expression(String operator, List<Expression> expressionList) {
        this.operator = operator;
        this.expressionList = expressionList;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }
}

