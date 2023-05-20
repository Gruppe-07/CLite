package org.example.codegeneration;

import org.example.AstVisitor;
import org.example.astnodes.*;

import java.io.FileWriter;
import java.io.IOException;


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

        //This code is reached when subexpression is evaluated
        switch (node.getOperator()) {
            case "+" -> {
                return writeBinaryExpressionInstructions(node, "ADD");
            }
            case "-" -> {
                return writeBinaryExpressionInstructions(node, "SUB");
            }
        }
        return null;
    }

    private String writeBinaryExpressionInstructions(BinaryExpressionNode node, String operation) {
        String register1 = stack.pop("X");
        String register2 = stack.pop("X");

        String resultRegister = stack.pop("X");

        String operand1 = (String) node.getLeft().accept(this);
        String operand2 = (String) node.getRight().accept(this);

        //If operand1 is a variable
        if (!(operand1.contains("#")) && !(operand1.contains("X"))) {
            //TODO lookup address and set operand1 as variable address
            operand1 = "address";
            assemblyCode.append("   LDR \n");
        }

        //If operand2 is a variable
        if (!(operand2.contains("#")) && !(operand2.contains("X"))) {
            //TODO lookup address and set operand2 as variable address
            operand2 = "address";
            assemblyCode.append("   LDR , \n");
        }


        assemblyCode.append("   MOV " + register1 + ", " + operand1 + "\n");
        assemblyCode.append("   MOV " + register2 + ", " + operand2 + "\n");

        assemblyCode.append("   " + operation + " " + resultRegister + ", " + register1 + ", " + register2 +"\n\n");

        if (operand1.contains("X")) { stack.push("X", operand1); }
        if (operand2.contains("X")) { stack.push("X", operand2); }

        stack.push("X", register2);
        stack.push("X", register1);

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
        String identifier = node.getIdentifierNode().getName();

        assemblyCode.append("   // Declare " + identifier + "\n");

        String resultRegister = (String) node.getValue().accept(this);
        if (!(resultRegister.contains("X"))) {
            String varRegister = stack.pop("X");

            assemblyCode.append("   MOV " + varRegister + ", " + resultRegister + "\n");
            resultRegister = varRegister;
        }
        assemblyCode.append("   SUB FP, SP, #16\n");
        assemblyCode.append("   SUB SP, SP, #16\n");
        assemblyCode.append("   STR " + resultRegister +", [FP]\n\n");


        stack.push("X", resultRegister);

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
        return "#" + node.getValue();
    }

    @Override
    public Object visitFunctionCallNode(FunctionCallNode node) {

        return null;
    }

    @Override
    public Object visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        String name = node.getIdentifierNode().getName();

        assemblyCode.append(name+ ":\n");

        node.getBody().accept(this);
        return null;
    }

    @Override
    public String visitIdentifierNode(IdentifierNode node) {
        return node.getName();
    }

    @Override
    public Object visitIfElseNode(IfElseNode node) {

        return null;
    }

    @Override
    public String visitIntegerConstantNode(IntegerConstantNode node) {
        return "#" + node.getValue();
    }

    public String visitBinaryExpression(BinaryExpressionNode node) {
        return (String) node.accept(this);
    }

    public String getIntegerConstant(IntegerConstantNode node) {
        return "#" + node.getValue();
    }

    public String getDoubleConstant(FloatConstantNode node) {
        return "#" + node.getValue();
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

        switch (node.getOperator()) {
            case "*":
                return writeBinaryExpressionInstructions(node, "MUL");
            case "/":
                return writeBinaryExpressionInstructions(node, "SDIV");
        }
        return null;
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
    public String visitParensExpressionNode(ParensExpressionNode node) {

        return (String) node.getInnerExpressionNode().accept(this);
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
