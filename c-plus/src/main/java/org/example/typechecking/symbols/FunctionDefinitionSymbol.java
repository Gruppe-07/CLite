package org.example.typechecking.symbols;

import org.example.typechecking.Type;

public class FunctionDefinitionSymbol extends Symbol{
    private Symbol parameterSymbol;

    public FunctionDefinitionSymbol(String name, Type type, Symbol parameterSymbol) {
        super(name, type);
        this.parameterSymbol = parameterSymbol;
    }

    public FunctionDefinitionSymbol(String name, Type type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "FunctionDefinitionSymbol{" +
                "parameterSymbol=" + parameterSymbol +
                "} " + super.toString();
    }
}
