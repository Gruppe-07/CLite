package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.myparser.parser.CLiteLexer;
import com.myparser.parser.CLiteParser;

public class Main {
    public static void main(String[] args) {
        String fileName = "example1.txt";
        try {
            URL url = Main.class.getClassLoader().getResource(fileName);
            if (url == null) {
                throw new IOException("Cannot find file: " + fileName);
            }
            InputStream inputStream = url.openStream();
            ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);
            CLiteLexer lexer = new CLiteLexer(antlrInputStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            CLiteParser parser = new CLiteParser(tokenStream);
            ParseTree parseTree = parser.compilationUnit(); // replace "yourStartRule" with the name of your grammar's start rule
            // Traverse the parse tree to perform further processing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

