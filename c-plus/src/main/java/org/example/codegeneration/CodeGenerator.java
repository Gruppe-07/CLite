package org.example.codegeneration;

import org.example.AstVisitor;
import org.example.astnodes.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;


public class CodeGenerator extends AstVisitor {
    private StringBuilder assemblyCode;
    private Stack<String> registerStack;
    private VariableTable currentTable;




    public void enterScope() {

        setCurrentTable(getCurrentTable().enterScope());
    }

    public void exitScope() {

        setCurrentTable(getCurrentTable().getParent());
    }
    public void generateCode(TranslationUnitNode node) {
        assemblyCode = new StringBuilder();
        registerStack = new Stack<>();
        currentTable = new VariableTable(null);

        for (int i = 30; i >= 0; i--)  {
            registerStack.push("X" + i);
            //Reserved registers are popped from the stack
            if (i == 18 || i == 29 || i == 0) { registerStack.pop();}
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
        String register1 = registerStack.pop();
        String register2 = registerStack.pop();

        String resultRegister = registerStack.pop();

        String operand1 = (String) node.getLeft().accept(this);
        String operand2 = (String) node.getRight().accept(this);


        assemblyCode.append("   MOV " + register1 + ", " + operand1 + "\n");
        assemblyCode.append("   MOV " + register2 + ", " + operand2 + "\n");

        assemblyCode.append("   " + operation + " " + resultRegister + ", " + register1 + ", " + register2 +"\n\n");

        if (operand1.contains("X")) { registerStack.push( operand1); }
        if (operand2.contains("X")) { registerStack.push( operand2); }

        registerStack.push( register2);
        registerStack.push( register1);

        return resultRegister;
    }



    @Override
    public Object visitAssignmentExpressionNode(AssignmentExpressionNode node) {
        IdentifierNode identifierNode = (IdentifierNode) node.getLeft();
        String name = identifierNode.getName();

        String address = getCurrentTable().lookupVariable(name);

        String resultRegister = (String) node.getRight().accept(this);

        assemblyCode.append("   STR " + resultRegister + ", [FP, #-" + address + "]\n");

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

        String result = (String) node.getValue().accept(this);
        if (!(result.contains("X"))) {
            String varRegister = registerStack.pop();

            assemblyCode.append("   MOV " + varRegister + ", " + result + "\n");
            result = varRegister;
        } else if (result.contains("function return value")) {
            String varRegister = registerStack.pop();

            assemblyCode.append("   MOV " + varRegister + ", " + result + "\n");
            result = varRegister;
        }

        int address = -1 * currentTable.getVariableCount() * 8;

        assemblyCode.append("   STR " + result +", [FP, #" + address + "]\n\n");
        currentTable.addVariable(identifier, String.valueOf(address));

        registerStack.push(result);

        return null;
    }

    @Override
    public String visitEqualityExpressionNode(EqualityExpressionNode node) {
        return switch (node.getOperator()) {
            case "==" -> writeEqualityExpressionInstructions(node, "EQ");
            case "!=" -> writeEqualityExpressionInstructions(node, "NE");
            default -> null;
        };
    }

    private String writeEqualityExpressionInstructions(BinaryExpressionNode node, String condition) {
        String register1 = registerStack.pop();
        String register2 = registerStack.pop();

        String operand1 = (String) node.getLeft().accept(this);
        String operand2 = (String) node.getRight().accept(this);

        assemblyCode.append("   MOV " + register1 + ", " + operand1 + "\n");
        assemblyCode.append("   MOV " + register2 + ", " + operand2 + "\n");

        if (operand1.contains("X")) { registerStack.push( operand1); }
        if (operand2.contains("X")) { registerStack.push( operand2); }

        registerStack.push( register2);
        registerStack.push( register1);

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
            int varCount = getCurrentTable().getVariableCount();
            int printfParamAddress = -1 * ((varCount) * 8);
            assemblyCode.append("""
                              
                              // Setup for printf
                              ADRP X0, ptfStr@PAGE
                              ADD X0, X0, ptfStr@PAGEOFF
                           """);
            assemblyCode.append("   MOV X10, " + resultRegister + "\n");
            int address = currentTable.getVariableCount() * 8;
            //assemblyCode.append("   SUB FP, SP, #16\n");
            //assemblyCode.append("   SUB SP, SP, #16\n\n");
            assemblyCode.append("   STR X10, [SP, #-16]!\n");
            assemblyCode.append("\n   BL _printf\n\n");
            assemblyCode.append("   ADD SP, SP, #16\n");
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

        if (node.getParameter() != null) { node.getParameter().accept(this); localVariableCount++;}

        int spaceToAdd = getSpaceToAdd(localVariableCount);

        if (Objects.equals(name, "main")) {

            assemblyCode.append("_" + name + ": STP LR, FP, [SP, #-16]! //Push LR, FP onto stack\n");
            assemblyCode.append("   // Make room for local variables and potential parameter\n");
            assemblyCode.append("   SUB FP, SP, #" + spaceToAdd +"\n");
            assemblyCode.append("   SUB SP, SP, #" + spaceToAdd + "\n\n");

            node.getBody().accept(this);
            assemblyCode.append("   ADD SP, SP, #" + spaceToAdd + "\n");
            assemblyCode.append("   LDP LR, FP, [SP], #16 // Restore LR, FP\n");
            assemblyCode.append("""
                                    MOV X0, #0
                                    MOV X16, #1
                                    svc #0x80
                                 """);
        } else {
            assemblyCode.append(name + ": STP LR, FP, [SP, #-16]! //Push LR, FP onto stack\n");
            assemblyCode.append("   // Make room for local variables and potential parameter\n");
            assemblyCode.append("   SUB FP, SP, #" + spaceToAdd +"\n");
            assemblyCode.append("   SUB SP, SP, #" + spaceToAdd + "\n\n");

            if (node.getParameter() != null) {

                assemblyCode.append("   STR X0, [FP, #0] // Push parameter to stack\n");
                String parameterName = node.getParameter().getIdentifierNode().getName();
                getCurrentTable().addVariable(parameterName, "0");
            }
            node.getBody().accept(this);
            assemblyCode.append("   ADD SP, SP, #" + spaceToAdd + "\n");
            assemblyCode.append("   LDP LR, FP, [SP], #16 // Restore LR, FP\n");
            assemblyCode.append("   RET\n\n");
        }


        exitScope();

        return null;
    }

    public int getSpaceToAdd(int variables) {
        // One variable requires 8 bytes. Stack must be 16 byte aligned.
        // Divide variables by 2, round up to the nearest integer, and multiply by 16.
        return ((variables + 1) / 2) * 16;
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

    public int getAddress(String name) {
        int tableAddress = Integer.parseInt(getCurrentTable().lookupVariable(name));

        System.out.println(name + tableAddress);

        return tableAddress;
    }
    @Override
    public String visitIdentifierNode(IdentifierNode node) {
        String name = node.getName();

        int address = getAddress(name);

        String resultRegister = registerStack.pop();

        assemblyCode.append("   LDR " + resultRegister + ", [FP, #" + address + "] // Load "+ name +"\n");

        return resultRegister;
    }

    @Override
    public Object visitIfElseNode(IfElseNode node) {
        int hash = node.hashCode();
        int localIfVariableCount = getBodyLocalVariables(node.getIfBranch());

        int spaceToAddIf = getSpaceToAdd(localIfVariableCount);

        String ifClause = (String) node.getCondition().accept(this);
        String elseClause = getElseClause(ifClause);


        assemblyCode.append("\n   B." + elseClause + " elseclause"+  hash + "\n\n");

        //assemblyCode.append("   SUB FP, SP, #" + spaceToAddIf +"\n");
        //assemblyCode.append("   SUB SP, SP, #" + spaceToAddIf + " // Space for local variables\n\n");

        enterScope();
        node.getIfBranch().accept(this);
        exitScope();

        //assemblyCode.append("   ADD SP, SP, #" + spaceToAddIf + "\n");

        assemblyCode.append("   B endif"+  hash + "\n\n");
        assemblyCode.append("elseclause"+  hash + ": \n");



        if (node.getElseBranch() != null) {
            int localElseVariables = getBodyLocalVariables(node.getIfBranch());
            int spaceToAddElse = getSpaceToAdd(localIfVariableCount);

            //assemblyCode.append("   SUB FP, SP, #" + spaceToAddElse +"\n");
            //assemblyCode.append("   SUB SP, SP, #" + spaceToAddElse + " // Space for local variables\n\n");


            enterScope();
            node.getElseBranch().accept(this);
            exitScope();


            //assemblyCode.append("   ADD SP, SP, #" + spaceToAddIf + "\n");



        }
        assemblyCode.append("\nendif"+  hash + ":\n");
        return null;
    }

    public String getElseClause(String condition) {
        switch (condition) {
            case "EQ" -> { // Equals
                return "NE"; // Does not equal
            }
            case "GT" -> { // Greater than
                return "LE"; // Less than or equals
            }
            case "GE" -> { // Greater or equals
                return "LT"; // Less than
            }
            case "NE" -> { // Does not equal
                return "EQ"; // Equals
            }
            case "LT" -> { // Less than
                return "GE"; // Greater than or equals
            }
            case "LE" -> { // Less than or Equals
                return "GT"; // Greater than
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
            case "%":
                return writeModuloExpression(node);
        }
        return null;
    }

    public String writeModuloExpression(MultiplicativeExpressionNode node) {
        String register1 = registerStack.pop();
        String register2 = registerStack.pop();

        String resultRegister = registerStack.pop();

        String operand1 = (String) node.getLeft().accept(this);
        String operand2 = (String) node.getRight().accept(this);

        assemblyCode.append("   MOV " + register1 + ", " + operand1 + "\n");
        assemblyCode.append("   MOV " + register2 + ", " + operand2 + "\n");

        assemblyCode.append("   UDIV " + resultRegister + ", " + register1 + ", " + register2 +"\n\n");
        assemblyCode.append("   MSUB " + resultRegister + ", " + resultRegister + ", " + register2 + ", " + register1 +"\n\n");

        if (operand1.contains("X")) { registerStack.push( operand1); }
        if (operand2.contains("X")) { registerStack.push( operand2); }

        registerStack.push( register2);
        registerStack.push( register1);

        return resultRegister;
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
    public String visitPostFixExpressionNode(PostFixExpressionNode node) {
        ExpressionNode operand = node.getIdentifierOrConstant();
        
        if (operand instanceof IdentifierNode) {
            String name = ((IdentifierNode)  operand).getName();
            String address = getCurrentTable().lookupVariable(name);


            String resultRegister = (String) node.getIdentifierOrConstant().accept(this);
            String operator = node.getOperator();
            if (Objects.equals(operator, "++")) {
                assemblyCode.append("   ADD " + resultRegister + ", " + resultRegister + ", #1 // " + resultRegister +"++ \n");
            } else {
                assemblyCode.append("   SUB " + resultRegister + ", " + resultRegister + ", #1 // " + resultRegister +"-- \n");
            }
            assemblyCode.append("   STR " + resultRegister + ", [FP, #"+ address + "]\n");
            return resultRegister;
        }
        else if (operand instanceof IntegerConstantNode) {
            
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
            case "<=":
                return writeEqualityExpressionInstructions(node, "LE");
            case ">=":
                return writeEqualityExpressionInstructions(node, "GE");
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
        int localVariableCount = getBodyLocalVariables(node.getBody());

        int spaceToAdd = getSpaceToAdd(localVariableCount);

        String initRegister = (String) visitDeclarationNode(node.getInitialization());

        assemblyCode.append("loop:\n");
        assemblyCode.append("    SUB FP, SP, #" + spaceToAdd +"\n");
        assemblyCode.append("    SUB SP, SP, #" + spaceToAdd + " // Space for local variables\n\n");
        node.getBody().accept(this);
        String condition = (String) node.getCondition().accept(this);
        assemblyCode.append("\n");

        String resultRegister = (String) node.getUpdate().accept(this);

        assemblyCode.append("    ADD SP, SP, #" + spaceToAdd + "\n");
        assemblyCode.append("   B." + condition + " loop\n");
        assemblyCode.append("\n");

        registerStack.push( initRegister);
        return null;
    }

    public int getBodyLocalVariables(CompoundStatementNode node) {
        int count = 0;
        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            if (blockItemNode instanceof DeclarationNode) {
                count++;
            }
        }
        return count;
    }

    public String visitForLoopInitialization(DeclarationNode node) {
        String register = registerStack.pop();
        String value = (String) node.getValue().accept(this);
        assemblyCode.append("   MOV " + register + ", " + value + " // For loop iterator\n");

        if (value.contains("X")) {
            registerStack.push( value);}

        return register;
    }

    @Override
    public Object visitWhileLoopNode(WhileLoopNode node) {
        enterScope();

        assemblyCode.append("loop:");
        String condition = (String) node.getCondition().accept(this);
        String inverseCondition = getElseClause(condition);

        assemblyCode.append("   B."+ inverseCondition+ " loopdone\n");
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
