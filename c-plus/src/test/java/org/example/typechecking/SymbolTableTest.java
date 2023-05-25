package org.example.typechecking;

import org.example.typechecking.SymbolTable;
import org.example.typechecking.symbols.FunctionDefinitionSymbol;
import org.example.typechecking.symbols.Symbol;
import org.example.typechecking.Type;
import org.example.typechecking.symbols.VariableSymbol;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SymbolTableTest {
    private SymbolTable symbolTable;

    @Before
    public void setUp() {
        symbolTable = new SymbolTable(null);
    }

    @Test
    public void testAddSymbolAndLookupSymbol() {
        Symbol symbol = new VariableSymbol("x", Type.INT);
        symbolTable.addSymbol("x", symbol);

        Symbol result = symbolTable.lookupSymbol("x");

        assertEquals(symbol, result);
    }

    @Test
    public void testLookupSymbolInParentSymbolTable() {
        Symbol parentSymbol = new VariableSymbol("y", Type.DOUBLE);
        SymbolTable parentTable = new SymbolTable(null);
        parentTable.addSymbol("y", parentSymbol);

        symbolTable = new SymbolTable(parentTable);

        Symbol result = symbolTable.lookupSymbol("y");

        assertEquals(parentSymbol, result);
    }

    @Test
    public void testEnterScopeAndGetParent() {
        SymbolTable childTable = symbolTable.enterScope();

        SymbolTable parentTable = childTable.getParent();

        assertEquals(symbolTable, parentTable);
    }

    @Test
    public void testFunctionDefinitionSymbol() {
        Symbol parameterSymbol = new VariableSymbol("param", Type.STRING);
        FunctionDefinitionSymbol functionSymbol = new FunctionDefinitionSymbol("func", Type.INT, Type.INT, parameterSymbol);

        assertEquals("func", functionSymbol.getName());
        assertEquals(Type.INT, functionSymbol.getType());
        assertEquals(Type.INT, functionSymbol.getTypeSpecifier());
        assertEquals(parameterSymbol, functionSymbol.getParameterSymbol());
    }
}
