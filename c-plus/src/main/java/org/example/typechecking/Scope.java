package org.example.typechecking;

import org.example.typechecking.symbols.Symbol;

import java.util.*;

public class Scope {
    private Map<String, Symbol> variables;
    private Scope parentScope;
    private SymbolTable symbolTable;

    public Scope(SymbolTable symbolTable) {
        this.variables = new HashMap<>();
        this.parentScope = null;
        this.symbolTable = symbolTable;
    }

    public Scope(Scope parentScope, SymbolTable symbolTable) {
        this.variables = new HashMap<>();
        this.parentScope = parentScope;
        this.symbolTable = symbolTable;
    }

    public void addSymbol(String name, Symbol symbol) {
        this.variables.put(name, symbol);
    }

    public Symbol lookupSymbol(String name) {
        Symbol symbol = this.variables.get(name);
        if (symbol != null) {
            return symbol;
        } else if (this.parentScope != null) {
            return this.parentScope.lookupSymbol(name);
        } else {
            return null;
        }
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }
}
