package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.myparser.parser.CLiteLexer;
import com.myparser.parser.CLiteParser;
import org.example.astnodes.BuildASTVisitor;

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
            var cst = parser.compilationUnit(); // replace "yourStartRule" with the name of your grammar's start rule
            var ast = new BuildASTVisitor().visitCompilationUnit(cst);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


