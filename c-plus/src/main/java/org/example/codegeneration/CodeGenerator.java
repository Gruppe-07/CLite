package org.example.codegeneration;

import org.example.AstVisitor;
import org.example.astnodes.*;

import java.io.FileWriter;
import java.io.IOException;


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
    public String visitAdditiveExpressionNode(AdditiveExpressionNode node) {
        if (node.getLeft() instanceof BinaryExpressionNode || node.getRight() instanceof BinaryExpressionNode) {
            node.getLeft().accept(this);
            node.getRight().accept(this);
        }

        //This code is reached when subexpression is evaluated
        switch (node.getOperator()) {
            case "+" -> {
                return writeAdditionInstructions(node);
            }
            case "-" -> {
                return writeSubtractionInstructions(node);
            }
        }
    }

    private String writeSubtractionInstructions(AdditiveExpressionNode node, String resultRegister) {
        String registerType = "X";

        //Result register is null when the first subexpression to evaluate is reached
        if (resultRegister == null) {
            resultRegister = stack.pop(registerType);

            String operand1 = stack.pop(registerType);
            String operand2 = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand1 + ", #" + getConstant(node.getLeft()) + "\n");
            assemblyCode.append("   MOV " + operand2 + ", #" + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   SUB " + resultRegister + ", " + operand1 + ", " + operand2 +"\n\n");

            stack.push(registerType, operand2);
            stack.push(registerType, operand1);
        } else {
            String operand = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand + ", #" + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   SUB " + resultRegister + ", " + resultRegister + ", " + operand +"\n\n");
            stack.push(registerType, operand);

        }
        return resultRegister;
    }

    @Override
    public Object visitAssignmentExpressionNode(AssignmentExpressionNode node) {

        return null;
    }

    @Override
    public String visitCharacterConstantNode(CharacterConstantNode node) {
        return node.getValue();
    }

    @Override
    public Object visitCompoundStatementNode(CompoundStatementNode node) {


        return null;
    }

    @Override
    public Object visitDeclarationNode(DeclarationNode node) {
        String type = node.getTypeSpecifierNode().getType();
        String identifier = node.getIdentifierNode().getName();

        assemblyCode.append("   // Declare " + identifier + "\n");
        node.getValue().accept(this);

        stack.push("X", resultRegister);
        resultRegister = null;
        return null;
    }

    @Override
    public Object visitEqualityExpressionNode(EqualityExpressionNode node) {

        return null;
    }

    @Override
    public Object visitExternalDeclarationNode(ExternalDeclarationNode node) {
        node.getFuncDefOrDecl().accept(this);
        return null;
    }

    @Override
    public String visitFloatConstantNode(FloatConstantNode node) {
        return "#" + Double.toString(node.getValue());
    }

    @Override
    public Object visitFunctionCallNode(FunctionCallNode node) {

        return null;
    }

    @Override
    public Object visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        String name = node.getIdentifierNode().getName();

        assemblyCode.append(name+ ":\n");
        return null;
    }

    @Override
    public String visitIdentifierNode(IdentifierNode node) {
        //TODO get address of variable and return
        return node.getName();
    }

    @Override
    public Object visitIfElseNode(IfElseNode node) {

        return null;
    }

    @Override
    public String visitIntegerConstantNode(IntegerConstantNode node) {
        return "#" + Integer.toString(node.getValue());
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
    public Object visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {

        return null;
    }
    @Override
    public String visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {

        return null;
    }

    @Override
    public String visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        if (node.getLeft() instanceof BinaryExpressionNode || node.getRight() instanceof BinaryExpressionNode) {
            visitExpressionNode(node.getLeft());
            visitExpressionNode(node.getRight());
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

    public String writeAdditionInstructions(AdditiveExpressionNode node) {
        String registerType = "X";

        //Result register is null when the first subexpression to evaluate is reached
        if (resultRegister == null) {
            resultRegister = stack.pop(registerType);

            String operand1 = stack.pop(registerType);
            String operand2 = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand1 + ", "+ getConstant(node.getLeft()) + "\n");
            assemblyCode.append("   MOV " + operand2 + ", " + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   ADD " + resultRegister + ", " + operand1 + ", " + operand2 +"\n\n");

            stack.push(registerType, operand2);
            stack.push(registerType, operand1);
        } else {
            String operand = stack.pop(registerType);
            assemblyCode.append("   MOV " + operand + ", #" + getConstant(node.getRight()) + "\n");

            assemblyCode.append("   ADD " + resultRegister + ", " + resultRegister + ", " + operand +"\n\n");
            stack.push(registerType, operand);
        }
        return registerType;
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
    public Object visitNegationNode(NegationNode node) {

        return null;
    }

    @Override
    public Object visitParameterDeclarationNode(ParameterDeclarationNode node) {

        return null;
    }

    @Override
    public Object visitParensExpressionNode(ParensExpressionNode node) {

        return null;
    }

    @Override
    public Object visitPostFixExpressionNode(PostFixExpressionNode node) {

        return null;
    }

    @Override
    public Object visitRelationalExpressionNode(RelationalExpressionNode node) {

        return null;
    }

    @Override
    public Object visitReturnStatementNode(ReturnStatementNode node) {

        return null;
    }

    @Override
    public Object visitTranslationUnitNode(TranslationUnitNode node) {
        for(ExternalDeclarationNode externalDeclarationNode : node.getExternalDeclarationNodeList()) {
            externalDeclarationNode.accept(this);
        }
        return null;
    }

    @Override
    public String visitTypeSpecifierNode(TypeSpecifierNode node) {
        return node.getType();
    }

    @Override
    public Object visitUnaryExpressionNode(UnaryExpressionNode node) {

        return null;
    }

    @Override
    public Object visitWhileLoopNode(WhileLoopNode node) {

        return null;
    }

    @Override
    public Object visitExpressionStatementNode(ExpressionStatementNode node) {

        return null;
    }

    @Override
    public Object visitExpressionNode(ExpressionNode node) {

        return null;
    }

    @Override
    public String visitConstantNode(ConstantNode node) {
        return (String) node.accept(this);
    }
}
