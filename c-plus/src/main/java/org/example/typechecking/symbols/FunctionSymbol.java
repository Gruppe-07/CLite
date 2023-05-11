package org.example.typechecking.symbols;

import org.example.typechecking.Type;

public class FunctionSymbol extends Symbol {
    private ParameterSymbol parameterSymbol;

    public FunctionSymbol(String name, ParameterSymbol parameterSymbol) {
        super(name);
        this.parameterSymbol = parameterSymbol;
    }
}
