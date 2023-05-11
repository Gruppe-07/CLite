package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.myparser.parser.CLiteLexer;
import com.myparser.parser.CLiteParser;
import org.example.astnodes.AstNode;
import org.example.astnodes.BuildASTVisitor;
import org.example.astnodes.TranslationUnitNode;
import org.example.typechecking.ScopeChecker;
import org.example.typechecking.SymbolTable;
import org.example.typechecking.TypeChecker;

public class Main {
    public static void main(String[] args) {
        String fileName = "example1.txt";
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
            TranslationUnitNode ast = new BuildASTVisitor().visitCompilationUnit(cst);

            PrettyPrinter prettyPrinter = new PrettyPrinter();
            prettyPrinter.visitTranslationUnitNode(ast);

            ScopeChecker scopeChecker = new ScopeChecker(new SymbolTable());
            scopeChecker.visitTranslationUnitNode(ast);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


