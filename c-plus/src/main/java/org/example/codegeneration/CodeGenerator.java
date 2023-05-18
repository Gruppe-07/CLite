package org.example.codegeneration;

import org.example.AstVisitor;
import org.example.astnodes.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class CodeGenerator extends AstVisitor {
    private StringBuilder assemblyCode;
    private RegisterStack stack;
    public void generateCode(TranslationUnitNode node) {
        assemblyCode = new StringBuilder();
        stack = new RegisterStack();


        for (int i = 30; i >= 0; i--)  {
            stack.push("W",  "W" + i);
            stack.push("X",  "X" + i);

        }
        System.out.println(stack);

        assemblyCode.append("""
                .global _start
                .align 2

                _start:
                """);

        visitTranslationUnitNode(node);

        assemblyCode.append(
                """
                           mov X16, #4
                           svc #0x80

                           mov     X0, #0
                           mov     X16, #1
                           svc     #0x80\
                        """);

        writeToFile("assembly/output.s", assemblyCode.toString());
    }

    private void writeToFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
            System.out.println("Successfully wrote content to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

    @Override
    public void visitAdditiveExpressionNode(AdditiveExpressionNode node) {

    }

    @Override
    public void visitAssignmentExpressionNode(AssignmentExpressionNode node) {

    }

    @Override
    public void visitCharacterConstantNode(CharacterConstantNode node) {

    }

    @Override
    public void visitCompoundStatementNode(CompoundStatementNode node) {


    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        String type = node.getTypeSpecifierNode().getType();
        String identifier = node.getIdentifierNode().getName();

        if (Objects.equals(type, "int")) {
            String register = stack.pop("X");

            assemblyCode.append("""
                       SUB SP, SP #4\n
                    """);
        }
    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {

    }

    @Override
    public void visitExternalDeclarationNode(ExternalDeclarationNode node) {
        node.getFuncDefOrDecl().accept(this);
    }

    @Override
    public void visitFloatConstantNode(FloatConstantNode node) {

    }

    @Override
    public void visitFunctionCallNode(FunctionCallNode node) {

    }

    @Override
    public void visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        String name = node.getIdentifierNode().getName();

        assemblyCode.append(name+ ":\n");
    }

    @Override
    public void visitIdentifierNode(IdentifierNode node) {

    }

    @Override
    public void visitIfElseNode(IfElseNode node) {

    }

    @Override
    public void visitIntegerConstantNode(IntegerConstantNode node) {

    }

    @Override
    public void visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {

    }
    @Override
    public void visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {

    }

    @Override
    public void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {

    }

    @Override
    public void visitNegationNode(NegationNode node) {

    }

    @Override
    public void visitParameterDeclarationNode(ParameterDeclarationNode node) {

    }

    @Override
    public void visitParensExpressionNode(ParensExpressionNode node) {

    }

    @Override
    public void visitPostFixExpressionNode(PostFixExpressionNode node) {

    }

    @Override
    public void visitRelationalExpressionNode(RelationalExpressionNode node) {

    }

    @Override
    public void visitReturnStatementNode(ReturnStatementNode node) {

    }

    @Override
    public void visitTranslationUnitNode(TranslationUnitNode node) {
        for(ExternalDeclarationNode externalDeclarationNode : node.getExternalDeclarationNodeList()) {
            externalDeclarationNode.accept(this);
        }
    }

    @Override
    public void visitTypeSpecifierNode(TypeSpecifierNode node) {

    }

    @Override
    public void visitUnaryExpressionNode(UnaryExpressionNode node) {

    }

    @Override
    public void visitWhileLoopNode(WhileLoopNode node) {

    }

    @Override
    public void visitExpressionStatementNode(ExpressionStatementNode node) {

    }

    @Override
    public void visitExpressionNode(ExpressionNode node) {

    }

    @Override
    public void visitConstantNode(ConstantNode node) {

    }
}
