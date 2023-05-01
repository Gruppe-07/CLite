package org.example.astnodes;

public abstract class BinaryExpression extends Expression {

    private String operator;

    public BinaryExpression(String operator) {
        this.operator = operator;
    }
}

