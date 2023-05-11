package org.example.typechecking;

import org.example.typechecking.symbols.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private SymbolTable parentSymbolTable;
    private Map<String, Symbol> symbols;

    public SymbolTable() {
        symbols = new HashMap<>();
    }

    public void addSymbol(String name, Symbol symbol) {
        symbols.put(name, symbol);
    }

    public Symbol getSymbol(String name) {
        return symbols.get(name);
    }

    public boolean containsSymbol(String name) {
        return symbols.containsKey(name);
    }
}

