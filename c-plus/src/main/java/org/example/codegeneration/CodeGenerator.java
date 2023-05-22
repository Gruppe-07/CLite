package org.example.codegeneration;

import org.example.AstVisitor;
import org.example.astnodes.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;


public class CodeGenerator extends AstVisitor {
    private StringBuilder assemblyCode;
    private Stack<String> stack;
    private VariableTable currentTable;

    public int getStackSpace() {
        return stackSpace;
    }

    private int stackSpace; //Variable to keep track of allocated stack space

    public int getScopeLevel() {
        return scopeLevel;
    }

    private int scopeLevel;
    public void addStackSpace(int amount) {stackSpace+= amount;} //Stack must be 16-byte aligned
    public void removeStackSpace(int amount) {stackSpace-= amount;}

    public void enterScope() {
        scopeLevel++;
        setCurrentTable(getCurrentTable().enterScope());
    }

    public void exitScope() {
        scopeLevel--;
        setCurrentTable(getCurrentTable().getParent());
    }
    public void generateCode(TranslationUnitNode node) {
        assemblyCode = new StringBuilder();
        stack = new Stack<>();
        currentTable = new VariableTable(null);
        stackSpace = 0;
        scopeLevel = 0;

        for (int i = 30; i >= 0; i--)  {

            stack.push("X" + i);
            //Reserved registers are popped from the stack
            if (i == 18 || i == 29 || i == 0) {stack.pop();}

        }

        assemblyCode.append("""
                .global _main
                .align 4
                
                """);

        visitTranslationUnitNode(node);

        assemblyCode.append(
                """
                        .data
                        ptfStr: .asciz	"Value of register: %ld\\n"
                        .align 4
                        .text
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
        String register1 = stack.pop();
        String register2 = stack.pop();

        String resultRegister = stack.pop();

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

        if (operand1.contains("X")) { stack.push( operand1); }
        if (operand2.contains("X")) { stack.push( operand2); }

        stack.push( register2);
        stack.push( register1);

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
            String varRegister = stack.pop();

            assemblyCode.append("   MOV " + varRegister + ", " + resultRegister + "\n");
            resultRegister = varRegister;
        } else if (resultRegister.contains("function return value")) {
            String varRegister = stack.pop();

            assemblyCode.append("   MOV " + varRegister + ", " + resultRegister + "\n");
            resultRegister = varRegister;
        }

        int address = currentTable.getVariableCount() * 8;

        assemblyCode.append("   STR " + resultRegister +", [FP, #-" + address + "]\n\n");
        currentTable.addVariable(identifier, String.valueOf(address));

        stack.push( resultRegister);

        return null;
    }

    @Override
    public String visitEqualityExpressionNode(EqualityExpressionNode node) {
        return switch (node.getOperator()) {
            case "==" -> writeEqualityExpressionInstructions(node, "NE");
            case "!=" -> writeEqualityExpressionInstructions(node, "EQ");
            default -> null;
        };
    }

    private String writeEqualityExpressionInstructions(BinaryExpressionNode node, String condition) {
        String register1 = stack.pop();
        String register2 = stack.pop();

        String operand1 = (String) node.getLeft().accept(this);
        String operand2 = (String) node.getRight().accept(this);

        assemblyCode.append("   MOV " + register1 + ", " + operand1 + "\n");
        assemblyCode.append("   MOV " + register2 + ", " + operand2 + "\n");

        if (operand1.contains("X")) { stack.push( operand1); }
        if (operand2.contains("X")) { stack.push( operand2); }

        stack.push( register2);
        stack.push( register1);

        String comment = " // " + register1 + " " + node.getOperator()+ " " + register2;



        assemblyCode.append("   CMP " + register1 + ", " + register2 + comment + " \n");
        return condition;
    }


    @Override
    public String visitFloatConstantNode(FloatConstantNode node) {
        return "#" + node.getValue();
    }

    @Override
    public Object visitFunctionCallNode(FunctionCallNode node) {
        String name = node.getIdentifierNode().getName();
        String resultRegister = null;

        if (node.getCallValue() != null) {
            resultRegister = (String) node.getCallValue().accept(this);
            assemblyCode.append("   MOV X0, " + resultRegister + " // Load parameter into X0\n");
        }
        if (Objects.equals(node.getIdentifierNode().getName(), "printf")) {
            assemblyCode.append("""
                              // Setup
                              STP X29, LR, [SP, #-16]!     ; Save LR, FR
                              ADRP X0, ptfStr@PAGE 
                              ADD X0, X0, ptfStr@PAGEOFF
                           """);
            assemblyCode.append("   MOV X10, " + resultRegister + "\n");
            int address = currentTable.getVariableCount() * 8;
            assemblyCode.append("   STR X10, [SP, #-32]!\n");
            assemblyCode.append("   BL _printf\n");
            return "#1 // Dummy return value from printf";
        } else {
            assemblyCode.append("   BL " + name + "\n");
        }


        return "X0 // X0 = Register of function return value";
    }

    @Override
    public Object visitFunctionDefinitionNode(FunctionDefinitionNode node) {

        enterScope();

        String name = node.getIdentifierNode().getName();
        int localVariableCount = getLocalVariableCount(node);

        //TODO find a way to count number of printf calls in function to manage stack allocation

        if (node.getParameter() != null) { node.getParameter().accept(this); localVariableCount++;}

        int spaceToAdd = getSpaceToAdd(localVariableCount);

        if (Objects.equals(name, "main")) {
            assemblyCode.append("_" + name + ": STP LR, FP, [SP, #-16]! //Push LR, FP onto stack\n");
            assemblyCode.append("   // Make room for local variables and potential parameter\n");
            assemblyCode.append("   SUB FP, SP, #" + spaceToAdd +"\n");
            assemblyCode.append("   SUB SP, SP, #" + spaceToAdd + "\n\n");
            addStackSpace(getSpaceToAdd(localVariableCount));
            node.getBody().accept(this);
            assemblyCode.append("   ADD SP, SP, #" + spaceToAdd + "\n");
            assemblyCode.append("   LDP LR, FP, [SP], #16 // Restore LR, FP\n");
            assemblyCode.append("   MOV X0, #0\n" +
                    "   MOV X16, #1\n" +
                    "   svc #0x80\n");
        } else {
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
            assemblyCode.append("   RET\n");
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

        String resultRegister = stack.pop();

        assemblyCode.append("   LDR " + resultRegister + ", [FP, #-" + address + "] // Load "+ name +"\n");

        return resultRegister;
    }

    @Override
    public Object visitIfElseNode(IfElseNode node) {
        String ifClause = (String) node.getCondition().accept(this);
        String elseClause = getElseClause(ifClause);

        assemblyCode.append("   B." + elseClause + " elseclause\n");



        node.getIfBranch().accept(this);
        assemblyCode.append("   B endif\n");
        assemblyCode.append("elseclause: \n");
        if (node.getElseBranch() != null) {node.getElseBranch().accept(this);}
        assemblyCode.append("endif:\n");
        return null;
    }

    public String getElseClause(String condition) {
        switch (condition) {
            case "EQ" -> { //Equals
                return "NE"; //Does not equal
            }
            case "GT" -> { //Greater than
                return "LT"; //less than
            }
            case "GE" -> { // Greater or equals
                return "LE"; //Less than or Equals
            }
        }
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
        String resultRegister = (String) node.getIdentifierOrConstant().accept(this);
        String operator = node.getOperator();
        if (Objects.equals(operator, "++")) {
            assemblyCode.append("   ADD " + resultRegister + ", " + resultRegister + ", #1 // " + resultRegister +"++ \n");
        } else {
            assemblyCode.append("   SUB " + resultRegister + ", " + resultRegister + ", #1 // " + resultRegister +"-- \n");
        }

        return null;
    }

    @Override
    public Object visitRelationalExpressionNode(RelationalExpressionNode node) {
        switch (node.getOperator())
        {
            case ">":
                return writeEqualityExpressionInstructions(node, "GT");
            case "<":
                return writeEqualityExpressionInstructions(node, "LT");
        }
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
    public Object visitForLoopNode(ForLoopNode node) {
        int localVariableCount = getForLoopLocalVariables(node);

        int spaceToAdd = getSpaceToAdd(localVariableCount);

        String initRegister = (String) visitDeclarationNode(node.getInitialization());

        assemblyCode.append("loop: STP LR, FP, [SP, #-16]! //Push LR, FP onto stack\n");
        assemblyCode.append("   SUB FP, SP, #" + spaceToAdd +"\n");
        assemblyCode.append("   SUB SP, SP, #" + spaceToAdd + " // Space for local variables\n\n");

        addStackSpace(getSpaceToAdd(localVariableCount));
        node.getBody().accept(this);
        String condition = (String) node.getCondition().accept(this);
        assemblyCode.append("\n");
        String resultRegister = (String) node.getUpdate().accept(this);

        removeStackSpace(getSpaceToAdd(localVariableCount));
        assemblyCode.append("   ADD SP, SP, #" + spaceToAdd + "\n");
        assemblyCode.append("   LDP LR, FP, [SP], #16 // Restore LR, FP\n");
        assemblyCode.append("   B." + condition + " loop\n");
        assemblyCode.append("\n");

        stack.push( initRegister);
        return null;
    }

    public int getForLoopLocalVariables(ForLoopNode node) {
        int count = 0;
        for (BlockItemNode blockItemNode : node.getBody().getBlockItemNodeList()) {
            if (blockItemNode instanceof DeclarationNode) {
                count++;
            }
        }
        return count;
    }

    public String visitForLoopInitialization(DeclarationNode node) {
        String register = stack.pop();
        String value = (String) node.getValue().accept(this);
        assemblyCode.append("   MOV " + register + ", " + value + " // For loop iterator\n");

        if (value.contains("X")) {stack.push( value);}

        return register;
    }

    @Override
    public Object visitWhileLoopNode(WhileLoopNode node) {
        enterScope();

        String condition = (String) node.getCondition().accept(this);
        assemblyCode.append("loop: "+ condition);
        assemblyCode.append("   B.EQ loopdone\n");
        node.getBody().accept(this);
        assemblyCode.append("   B loop\n");
        assemblyCode.append("loopdone:\n");

        exitScope();
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
