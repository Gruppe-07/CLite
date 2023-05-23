package org.example.typechecking.symbols;

import org.example.typechecking.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTest {

    Symbol NewSymbol = new Symbol("test", Type.INT){};

    @Test
    void TestGetName() {
        assertEquals("test", NewSymbol.getName());
    }

    @Test
    void TestGetType() {
        assertEquals(Type.INT, NewSymbol.getType());
    }

    @Test
    void TestToString() {
        assertEquals("Symbol{" + "name='" + "test" + '\'' + ", type=" + Type.INT + '}', NewSymbol.toString());
    }
}