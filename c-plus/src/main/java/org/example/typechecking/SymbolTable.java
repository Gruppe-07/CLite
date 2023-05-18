package org.example.typechecking;

import org.example.typechecking.symbols.Symbol;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> symbols;
    private final SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        this.symbols = new HashMap<>();
        this.parent = parent;
    }

    public void addSymbol(String name, Symbol symbol) {
        symbols.put(name, symbol);
    }

    public Symbol lookupSymbol(String name) {
        Symbol value = symbols.get(name);
        if (value != null) {
            return value;
        } else if (parent != null) {
            return parent.lookupSymbol(name);
        } else {
            return null;
        }
    }

    public SymbolTable enterScope() {
        return new SymbolTable(this);
    }

    public SymbolTable getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "symbols=" + symbols +
                ", parent=" + parent +
                '}';
    }
}
