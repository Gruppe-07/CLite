package org.example.codegeneration;

import org.example.AstVisitor;
import org.example.astnodes.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class CodeGenerator extends AstVisitor {
    private StringBuilder assemblyCode;
    private RegisterStack stack;
    public String resultRegister;
    public void generateCode(TranslationUnitNode node) {
        assemblyCode = new StringBuilder();
        stack = new RegisterStack();
        resultRegister = null;

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
                           svc     #0x80
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
        if (node.getLeft() instanceof BinaryExpressionNode || node.getRight() instanceof BinaryExpressionNode) {
            node.getLeft().accept(this);
            node.getRight().accept(this);
        }

        //This code is reached when subexpression is evaluated
        switch (node.getOperator()) {
            case "+":
                writeAdditionInstructions(node);
                break;
            case "-":
                break;
        }
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

        assemblyCode.append("   // Declare " + identifier + "\n");
        node.getValue().accept(this);

        stack.push("X", resultRegister);
        resultRegister = null;
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

    public String getConstant(ExpressionNode node) {
        if (node instanceof IntegerConstantNode) {
            return getIntegerConstant((IntegerConstantNode) node);
        } else if (node instanceof FloatConstantNode) {
            return getDoubleConstant((FloatConstantNode) node);
        } else if (node instanceof IdentifierNode) {
            return getVariableAddress((IdentifierNode) node);
        }
        return null;
    }

    public String getIntegerConstant(IntegerConstantNode node) {
        return Integer.toString(node.getValue());
    }

    public String getDoubleConstant(FloatConstantNode node) {
        return Double.toString(node.getValue());
    }

    public String getVariableAddress(IdentifierNode node) {
        return "test";
    }

    @Override
    public void visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {

    }
    @Override
    public void visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {

    }

    @Override
    public void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        if (!(node.getLeft() instanceof ConstantNode)) {
            node.getLeft().accept(this);
            node.getRight().accept(this);
        }

        //This code is reached when subexpression is evaluated
        switch (node.getOperator()) {
            case "*":
                writeMultiplicationInstructions(node);
                break;
            case "/":
                break;
        }
    }

    public void writeAdditionInstructions(AdditiveExpressionNode node) {
        String registerType = "X";

        //Result register is null when the first subexpression to evaluate is reached
        if (resultRegister == null) {
            resultRegister = stack.pop(registerType);

            String operand1 = stack.pop(registerType);
            String operand2 = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand1 + ", #"+ getConstant(node.getLeft()) + "\n");
            assemblyCode.append("   MOV " + operand2 + ", #" + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   ADD " + resultRegister + ", " + operand1 + ", " + operand2 +"\n\n");

            stack.push(registerType, operand2);
            stack.push(registerType, operand1);
        } else {
            String operand = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand + ", #" + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   ADD " + resultRegister + ", " + resultRegister + ", " + operand +"\n\n");
            stack.push(registerType, operand);

        }
    }

    public void writeMultiplicationInstructions(MultiplicativeExpressionNode node) {
        String registerType = "X";

        //Result register is null when the first subexpression to evaluate is reached
        if (resultRegister == null) {
            resultRegister = stack.pop(registerType);

            String operand1 = stack.pop(registerType);
            String operand2 = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand1 + ", #" + getConstant(node.getLeft()) + "\n");
            assemblyCode.append("   MOV " + operand2 + ", #" + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   MUL " + resultRegister + ", " + operand1 + ", " + operand2 +"\n\n");

            stack.push(registerType, operand2);
            stack.push(registerType, operand1);
        }
        else {
            String operand = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand + ", #" + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   MUL " + resultRegister + ", " + resultRegister + ", " + operand +"\n\n");
            stack.push(registerType, operand);

        }
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
