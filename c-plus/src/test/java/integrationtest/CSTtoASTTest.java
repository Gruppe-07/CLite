package integrationtest;

import com.myparser.parser.CLiteLexer;
import com.myparser.parser.CLiteParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.example.Main;
import org.example.PrettyPrinter;
import org.example.astnodes.BuildASTVisitor;
import org.example.astnodes.TranslationUnitNode;
import org.example.codegeneration.CodeGenerator;
import org.example.typechecking.ScopeChecker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSTtoASTTest {
    @Test
    public void testCSTtoAST() {
        CLiteParser.CompilationUnitContext cst = getCST();
        TranslationUnitNode ast = new BuildASTVisitor().visitCompilationUnit(cst);

        assertEquals(1, ast.getFunctionDefinitionNodeList().size());
        assertEquals("int", ast.getFunctionDefinitionNodeList().get(0).getTypeSpecifierNode().getType());
        assertEquals("main", ast.getFunctionDefinitionNodeList().get(0).getIdentifierNode().getName());
        assertEquals(null, ast.getFunctionDefinitionNodeList().get(0).getParameter());
        assertEquals(0, ast.getFunctionDefinitionNodeList().get(0).getBody().getBlockItemNodeList().size());
    }

    public CLiteParser.CompilationUnitContext getCST() {
        String fileName = "integrationtest2.txt";
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
            CLiteParser.CompilationUnitContext cst = parser.compilationUnit(); 

            return cst;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
