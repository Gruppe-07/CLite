package org.example.typechecking;

import com.myparser.parser.CLiteLexer;
import com.myparser.parser.CLiteParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.example.Main;
import org.example.astnodes.BuildASTVisitor;
import org.example.astnodes.TranslationUnitNode;
import org.example.typechecking.ScopeChecker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScopeCheckerTest {

    public TranslationUnitNode getAST(String fileName) {
        try {
            URL url = Main.class.getClassLoader().getResource(fileName);
            if (url == null) {
                throw new IOException("Cannot find file: " + fileName);
            }
            InputStream inputStream = url.openStream();
            CharStream charStream = CharStreams.fromStream(inputStream);

            CLiteLexer lexer = new CLiteLexer(charStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            CLiteParser parser = new CLiteParser(tokenStream);
            CLiteParser.CompilationUnitContext cst = parser.compilationUnit(); // replace "yourStartRule" with the name of your grammar's start rule
            return new BuildASTVisitor().visitCompilationUnit(cst);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCorrect() {
        TranslationUnitNode ast = getAST("scopetest1.txt");
        ScopeChecker scopeChecker = new ScopeChecker();
        // assert that checkScope does not throw any runtime exceptions
        assertDoesNotThrow(() -> scopeChecker.checkScope(ast));
    }

    @Test
    public void testIncorrectScope() {
        TranslationUnitNode ast = getAST("scopetest2.txt");
        ScopeChecker scopeChecker = new ScopeChecker();
        // assert that checkScope throws a runtime exception
        assertThrows(RuntimeException.class, () -> scopeChecker.checkScope(ast));
    }

    @Test
    public void testDuplicateFunctionDeclaration() {
        TranslationUnitNode ast = getAST("scopetest3.txt");
        ScopeChecker scopeChecker = new ScopeChecker();
        // assert that checkScope throws a runtime exception
        assertThrows(RuntimeException.class, () -> scopeChecker.checkScope(ast));
    }
}