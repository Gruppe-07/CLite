package org.example.codegeneration;

import org.example.AstVisitor;
import org.example.astnodes.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class CodeGenerator extends AstVisitor {
    private StringBuilder assemblyCode;
    private RegisterStack stack;
    private VariableTable currentTable;

    public int getStackSpace() {
        return stackSpace;
    }

    private int stackSpace; //Variable to keep track of allocated stack space
    public void addStackSpace(int amount) {stackSpace+= amount;} //Stack must be 16-byte aligned
    public void removeStackSpace(int amount) {stackSpace-= amount;}

    public void enterScope() {
        setCurrentTable(getCurrentTable().enterScope());
    }

    public void exitScope() {
        setCurrentTable(getCurrentTable().getParent());
    }
    public void generateCode(TranslationUnitNode node) {
        assemblyCode = new StringBuilder();
        stack = new RegisterStack();
        currentTable = new VariableTable(null);
        stackSpace = 0;

        for (int i = 30; i >= 0; i--)  {
            stack.push("W",  "W" + i);
            stack.push("X",  "X" + i);
            //Reserved registers are popped from the stack
            if (i == 18 || i == 29 || i == 0) {stack.pop("X");}

        }

        assemblyCode.append("""
                .global _start
                .align 2

                _start:
                """);
        assemblyCode.append("   B main\n\n");

        visitTranslationUnitNode(node);

        assemblyCode.append(
                """
                           MOV X16, #4
                           SVC #0x80

                           MOV     X0, #0
                           MOV     X16, #1
                           SVC     #0x80
                           
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
        //TODO ensure that variables aren't loaded twice and
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public String visitCharacterConstantNode(CharacterConstantNode node) {
        return node.getValue();
    }

    @Override
    public Object visitCompoundStatementNode(CompoundStatementNode node) {
        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

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
        } else if (resultRegister.contains("function return value")) {
            String varRegister = stack.pop("X");

            assemblyCode.append("   MOV " + varRegister + ", " + resultRegister + "\n");
            resultRegister = varRegister;
        }

        int address = currentTable.getVariableCount() * 8;

        assemblyCode.append("   STR " + resultRegister +", [FP, #-" + address + "]\n\n");
        currentTable.addVariable(identifier, String.valueOf(address));

        stack.push("X", resultRegister);

        return null;
    }

    @Override
    public String visitEqualityExpressionNode(EqualityExpressionNode node) {
        switch (node.getOperator()) {
            case "==":
                return writeEqualityExpressionInstructions(node);
            //case "/":
                //return writeBinaryExpressionInstructions(node, "SDIV");
        }
        return null;
    }

    private String writeEqualityExpressionInstructions(EqualityExpressionNode node) {
        String register1 = stack.pop("X");
        String register2 = stack.pop("X");

        String operand1 = (String) node.getLeft().accept(this);
        String operand2 = (String) node.getRight().accept(this);

        assemblyCode.append("   MOV " + register1 + ", " + operand1 + "\n");
        assemblyCode.append("   MOV " + register2 + ", " + operand2 + "\n");

        if (operand1.contains("X")) { stack.push("X", operand1); }
        if (operand2.contains("X")) { stack.push("X", operand2); }

        stack.push("X", register2);
        stack.push("X", register1);

        return ("   CMP " + register1 + ", " + register2 +"\n");
    }


    @Override
    public String visitFloatConstantNode(FloatConstantNode node) {
        return "#" + node.getValue();
    }

    @Override
    public Object visitFunctionCallNode(FunctionCallNode node) {
        String name = node.getIdentifierNode().getName();

        if (node.getCallValue() != null) {
            String resultRegister = (String) node.getCallValue().accept(this);
            assemblyCode.append("   MOV X0, " + resultRegister + " // Load parameter into X0\n");
        }

        assemblyCode.append("   BL " + name + "\n");
        return "X0 // X0 = Register of function return value";
    }

    @Override
    public Object visitFunctionDefinitionNode(FunctionDefinitionNode node) {

        enterScope();

        String name = node.getIdentifierNode().getName();
        int localVariableCount = getLocalVariableCount(node);

        if (node.getParameter() != null) { node.getParameter().accept(this); localVariableCount++;}

        if (Objects.equals(name, "main")) {
            int spaceToAdd = getSpaceToAdd(localVariableCount);
            assemblyCode.append(name + ":\n");
            assemblyCode.append("   // Make room for local variables and potential parameter\n");
            assemblyCode.append("   SUB FP, SP, #" + spaceToAdd +"\n");
            assemblyCode.append("   SUB SP, SP, #" + spaceToAdd + "\n\n");
            node.getBody().accept(this);
            assemblyCode.append("   ADD SP, SP, #" + spaceToAdd + "\n\n");

        } else {
            int spaceToAdd = getSpaceToAdd(localVariableCount);
            assemblyCode.append(name + ": STP LR, FP, [SP, #-16]! //Push LR, FP onto stack\n");
            assemblyCode.append("   // Make room for local variables and potential parameter\n");
            assemblyCode.append("   SUB FP, SP, #" + spaceToAdd +"\n");
            assemblyCode.append("   SUB SP, SP, #" + spaceToAdd + "\n\n");
            addStackSpace(getSpaceToAdd(localVariableCount));

            if (node.getParameter() != null) {
                int address = currentTable.getVariableCount() * 8;
                assemblyCode.append("   STR X0, [FP, #-"+ address +"] // Push parameter to stack\n");
                getCurrentTable().addVariable(node.getParameter().getIdentifierNode().getName(), String.valueOf(address));
            }
            node.getBody().accept(this);
            assemblyCode.append("   ADD SP, SP, #" + spaceToAdd + "\n");
            assemblyCode.append("   LDP LR, FP, [SP], #16 // Restore LR, FP\n");
            assemblyCode.append("   RET\n\n");
        }

        removeStackSpace(getSpaceToAdd(localVariableCount));

        exitScope();

        return null;
    }

    public int getSpaceToAdd(int variables) {
        //One variable requires 8 bytes. Stack must be 16 byte aligned
        return (int) Math.ceil(((double) variables / 2)) * 16;
    }

    public int getLocalVariableCount(FunctionDefinitionNode node) {
        return countDeclarations(node.getBody());
    }

    public int countDeclarations(CompoundStatementNode node) {
        int count = 0;
        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            if (blockItemNode instanceof DeclarationNode) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String visitIdentifierNode(IdentifierNode node) {
        String name = node.getName();
        String address = currentTable.lookupVariable(name);

        String resultRegister = stack.pop("X");

        assemblyCode.append("   LDR " + resultRegister + ", [FP, #-" + address + "] // Load "+ name +"\n");

        return resultRegister;
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
        String value = (String) node.getReturnValue().accept(this);
        assemblyCode.append("   MOV X0, " + value + "// Load return value into X0 register\n");
        return null;
    }

    @Override
    public Object visitTranslationUnitNode(TranslationUnitNode node) {
        for(FunctionDefinitionNode functionDefinitionNode : node.getFunctionDefinitionNodeList()) {
            functionDefinitionNode.accept(this);
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
        String condition = (String) node.getCondition().accept(this);
        assemblyCode.append("loop: "+ condition);
        assemblyCode.append("   B.EQ loopdone\n");
        node.getBody().accept(this);
        assemblyCode.append("   B loop\n");
        assemblyCode.append("loopdone:\n");
        return null;
    }

    @Override
    public Object visitExpressionStatementNode(ExpressionStatementNode node) {
        node.getExpressionNode().accept(this);
        return null;
    }

    @Override
    public Object visitExpressionNode(ExpressionNode node) {
        node.accept(this);
        return null;
    }

    @Override
    public String visitConstantNode(ConstantNode node) {
        return (String) node.accept(this);
    }

    public VariableTable getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(VariableTable currentTable) {
        this.currentTable = currentTable;
    }
}
