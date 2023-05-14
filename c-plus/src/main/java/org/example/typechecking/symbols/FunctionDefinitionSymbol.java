package org.example.typechecking.symbols;

import org.example.typechecking.Type;

public class FunctionDefinitionSymbol extends Symbol{

    private Type typeSpecifier;
    private Symbol parameterSymbol;

    public FunctionDefinitionSymbol(String name, Type type, Type typeSpecifier, Symbol parameterSymbol) {
        super(name, type);
        this.typeSpecifier = typeSpecifier;
        this.parameterSymbol = parameterSymbol;
    }

    public FunctionDefinitionSymbol(String name, Type type, Type typeSpecifier) {
        super(name, type);
        this.typeSpecifier = typeSpecifier;
    }


    @Override
    public String toString() {
        return "FunctionDefinitionSymbol{" +
                "parameterSymbol=" + parameterSymbol +
                "} " + super.toString();
    }

    public Type getTypeSpecifier() {
        return typeSpecifier;
    }
}
