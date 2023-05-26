package integrationtest;

import com.myparser.parser.CLiteLexer;
import com.myparser.parser.CLiteParser;
import org.antlr.v4.runtime.*;
import org.example.Main;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerAndParserTest {
    @Test
    public void testLexerParserIntegration() {
        String fileName = "integrationtest2.txt";
        try {
            URL url = Main.class.getClassLoader().getResource(fileName);
            if (url == null) {
                throw new IOException("Cannot find file: " + fileName);
            }
            // Define expected lexer tokens
            int[] expectedTokenTypes = {
                    CLiteLexer.Int,
                    CLiteLexer.Identifier,
                    CLiteLexer.LeftParen,
                    CLiteLexer.RightParen,
                    CLiteLexer.LeftBrace,
                    CLiteLexer.RightBrace,
                    CLiteLexer.EOF,
            };

            InputStream inputStream = url.openStream();
            CharStream charStream = CharStreams.fromStream(inputStream);

            CLiteLexer lexer = new CLiteLexer(charStream);

            // Verify lexer output
            int index = 0;
            Token token;
            do {
                token = lexer.nextToken();
                assertEquals(expectedTokenTypes[index], token.getType());
                index++;
            } while (token.getType() != Token.EOF);

            // Create parser with lexer tokens
            lexer.reset();
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            CLiteParser parser = new CLiteParser(tokenStream);

            CLiteParser.CompilationUnitContext cst = parser.compilationUnit();

            assertEquals(1, cst.translationUnit().functionDefinition().size());
            assertEquals("int", cst.translationUnit().functionDefinition(0).typeSpecifier().getText());
            assertEquals("main", cst.translationUnit().functionDefinition(0).Identifier().getText());
            assertEquals(null, cst.translationUnit().functionDefinition(0).parameterDeclaration());
            assertEquals(2, cst.translationUnit().functionDefinition(0).compoundStatement().getChildCount());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
