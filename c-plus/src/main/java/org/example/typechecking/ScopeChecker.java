package org.example.typechecking;

import org.example.AstVisitor;
import org.example.astnodes.*;
import org.example.typechecking.symbols.FunctionDefinitionSymbol;
import org.example.typechecking.symbols.Symbol;
import org.example.typechecking.symbols.VariableSymbol;

import java.util.Stack;

public class ScopeChecker extends AstVisitor {
    private final Stack<SymbolTable> tableStack;
    private Type currentType;
    private Type functionType;

    public ScopeChecker() {
        this.currentType = null;
        this.functionType = null;
        this.tableStack = new Stack<>();
        //Add global scope to stack
        tableStack.push(new SymbolTable(null));
    }

    public void enterScope() {
        tableStack.push(new SymbolTable(tableStack.peek()));
    }

    public void exitScope() {
        tableStack.pop();
    }

    public SymbolTable getCurrentScope() {
        return tableStack.peek();
    }

    public void setCurrentType(Type type) {
        this.currentType = type;
    }

    public Type getCurrentType() {
        return this.currentType;
    }
    public void checkScope(TranslationUnitNode node) {
        visitTranslationUnitNode(node);
    }


    @Override
    public void visitAdditiveExpressionNode(AdditiveExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitAssignmentExpressionNode(AssignmentExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visitCharacterConstantNode(CharacterConstantNode node) {
        if (getCurrentType() != Type.STRING) {
            throw new RuntimeException("Operand does not match type: " + getCurrentType());
        }
    }

    @Override
    public void visitCompoundStatementNode(CompoundStatementNode node) {
        enterScope();

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

        exitScope();
    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        String name = node.getIdentifierNode().getName();
        Type type = Type.valueOf(node.getTypeSpecifierNode().getType().toUpperCase());

        setCurrentType(type);

        node.getValue().accept(this);

        if (getCurrentScope().lookupSymbol(name) == null) {
            VariableSymbol variableSymbol = new VariableSymbol(name, type);
            getCurrentScope().addSymbol(name, variableSymbol);
        }
        else {throw new RuntimeException("Variable already defined");}

        setCurrentType(null);
    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitExternalDeclarationNode(ExternalDeclarationNode node) {
        node.getFuncDefOrDecl().accept(this);
    }

    @Override
    public void visitFloatConstantNode(FloatConstantNode node) {
        if (getCurrentType() != Type.DOUBLE) {
            throw new RuntimeException("Operand does not match type: " + getCurrentType());
        }
    }


    @Override
    public void visitFunctionCallNode(FunctionCallNode node) {

        String name = node.getIdentifierNode().getName();

        if (getCurrentScope().lookupSymbol(name) == null) {
            throw new RuntimeException(name + " is not a defined function.");
        }
        else {
            FunctionDefinitionSymbol symbol = (FunctionDefinitionSymbol) getCurrentScope().lookupSymbol(name);
            if (symbol.getType() != Type.FUNCTION) {
                throw new RuntimeException(name + " is not a defined function.");
            }
            if (symbol.getTypeSpecifier() != getCurrentType()) {
                throw new RuntimeException(getCurrentType().name().toLowerCase() + " does not match function return type");
            }
        }

        if (node.getCallValue() != null) {
            node.getCallValue().accept(this);
        }
    }

    @Override
    public void visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        Type typeSpecifier = Type.valueOf(node.getTypeSpecifierNode().getType().toUpperCase());
        String functionName = node.getIdentifierNode().getName();

        setFunctionType(typeSpecifier);

        //Throw an error if function name has already been declared
        if (getCurrentScope().lookupSymbol(functionName) != null) {
            throw new RuntimeException("Variable: " + functionName + " already declared");
        }

        //Create parameter symbol if function has parameter
        VariableSymbol paramSymbol = null;
        if (node.getParameter() != null) {
            String paramName = node.getParameter().getName().getName();
            Type parmType = Type.valueOf(node.getParameter().getType().getType().toUpperCase());

            paramSymbol = new VariableSymbol(paramName, parmType);

            if (getCurrentScope().lookupSymbol(paramName) != null) {
                throw new RuntimeException("Variable: " + paramName + " already declared");
            }
        }

        FunctionDefinitionSymbol funcSymbol = new FunctionDefinitionSymbol(functionName, Type.FUNCTION, typeSpecifier, paramSymbol);
        getCurrentScope().addSymbol(functionName, funcSymbol);

        visitFunctionBody(node.getBody(), paramSymbol);

        setFunctionType(null);

    }

    public void visitFunctionBody(CompoundStatementNode node, VariableSymbol paramSymbol) {
        enterScope();

        if (paramSymbol != null) {
            getCurrentScope().addSymbol(paramSymbol.getName(), paramSymbol);
        }

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

        exitScope();
    }


    @Override
    public void visitIdentifierNode(IdentifierNode node) {
        String name = node.getName();

        if (getCurrentScope().lookupSymbol(name) == null) {
            throw new RuntimeException("Variable \"" + name + "\" is undefined.");
        }

        if (getCurrentType() == null) {
            Symbol symbol = getCurrentScope().lookupSymbol(name);
            Type thisType = symbol.getType();
            setCurrentType(thisType);
        }
    }

    @Override
    public void visitIfElseNode(IfElseNode node) {
        node.getCondition().accept(this);

        node.getIfBranch().accept(this);

        if (node.getElseBranch() != null) {
            node.getElseBranch().accept(this);
        }
    }

    @Override
    public void visitIntegerConstantNode(IntegerConstantNode node) {
        if (getCurrentType() != Type.INT) {
            throw new RuntimeException("Operand does not match type: " + getCurrentType());
        }
    }

    @Override
    public void visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitNegationNode(NegationNode node) {
        node.getInnerExpressionNode().accept(this);
    }

    @Override
    public void visitParameterDeclarationNode(ParameterDeclarationNode node) {
        Type type = Type.valueOf(node.getType().getType().toUpperCase());
        String name = node.getName().getName();
    }

    @Override
    public void visitParensExpressionNode(ParensExpressionNode node) {
        node.getInnerExpressionNode().accept(this);
    }

    @Override
    public void visitPostFixExpressionNode(PostFixExpressionNode node) {
        node.getIdentifierOrConstant().accept(this);
    }

    @Override
    public void visitRelationalExpressionNode(RelationalExpressionNode node) {
        for (ExpressionNode expressionNode : node.getOperands()) {
            expressionNode.accept(this);
        }
    }

    @Override
    public void visitReturnStatementNode(ReturnStatementNode node) {
        if (node.getReturnValue() != null) {
            setCurrentType(functionType);
            node.getReturnValue().accept(this);
            setCurrentType(null);
        }
    }

    @Override
    public void visitTranslationUnitNode(TranslationUnitNode node) {
        for (ExternalDeclarationNode externalDeclarationNode : node.getExternalDeclarationNodeList()) {
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
        node.getCondition().accept(this);

        node.getBody().accept(this);
    }

    @Override
    public void visitExpressionStatementNode(ExpressionStatementNode node) {
        node.getExpressionNode().accept(this);
    }

    @Override
    public void visitExpressionNode(ExpressionNode node) {
        node.accept(this);
    }

    @Override
    public void visitConstantNode(ConstantNode node) {

    }

    public Type getFunctionType() {
        return functionType;
    }

    public void setFunctionType(Type functionType) {
        this.functionType = functionType;
    }
}
