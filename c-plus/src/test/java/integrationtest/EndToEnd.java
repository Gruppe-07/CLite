package integrationtest;

import com.myparser.parser.CLiteLexer;
import com.myparser.parser.CLiteParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.example.astnodes.BuildASTVisitor;
import org.example.astnodes.TranslationUnitNode;
import org.example.codegeneration.CodeGenerator;
import org.example.typechecking.ScopeChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.URL;

public class EndToEnd {
    public TranslationUnitNode getAST(String fileName) {
        try {
            URL url = EndToEnd.class.getClassLoader().getResource(fileName);
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

    public String loadFile(String fileName) {
        try {
            URL url = EndToEnd.class.getClassLoader().getResource(fileName);
            if (url == null) {
                throw new IOException("Cannot find file: " + fileName);
            }
            InputStream inputStream = url.openStream();
            CharStream charStream = CharStreams.fromStream(inputStream);
            return charStream.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGeneratedCode(String fileName) {
        TranslationUnitNode ast = getAST(fileName);
        ScopeChecker scopeChecker = new ScopeChecker();
        scopeChecker.checkScope(ast);

        CodeGenerator codeGenerator = new CodeGenerator();
        File generatedCode = codeGenerator.generateCode(ast, "integrationtest1");

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(generatedCode))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        return content.toString();
    }

    @Test
    public void endToEndTest() {

        String generatedCodeText = getGeneratedCode("integrationtest1.txt");
        String expectedCode = loadFile("integrationtest1_expected.s");

        Assertions.assertEquals(expectedCode, generatedCodeText);
    }

}
