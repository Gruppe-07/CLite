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
    public Object visitAdditiveExpressionNode(AdditiveExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitAssignmentExpressionNode(AssignmentExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitCharacterConstantNode(CharacterConstantNode node) {
        if (getCurrentType() != Type.STRING) {
            throw new RuntimeException("Operand does not match type: " + getCurrentType());
        }
        return null;
    }

    @Override
    public Object visitCompoundStatementNode(CompoundStatementNode node) {
        enterScope();

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            blockItemNode.accept(this);
        }

        exitScope();
        return null;
    }

    @Override
    public Object visitDeclarationNode(DeclarationNode node) {
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
        return null;
    }

    @Override
    public Object visitEqualityExpressionNode(EqualityExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }


    @Override
    public Object visitFloatConstantNode(FloatConstantNode node) {
        if (getCurrentType() != Type.DOUBLE && getCurrentType() != null) {
            throw new RuntimeException("Operand does not match type: " + getCurrentType());
        }
        if (getCurrentType() == null) {setCurrentType(Type.DOUBLE);}
        return null;
    }


    @Override
    public Object visitFunctionCallNode(FunctionCallNode node) {
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
            if (symbol.getParameterSymbol() != null && node.getCallValue() != null) {
                Type paramType = symbol.getParameterSymbol().getType();
                node.getCallValue().accept(this);
                if (getCurrentType() != paramType) {
                    throw new RuntimeException("Function parameter does not match call value");
                }
            }
            if (symbol.getParameterSymbol() == null && node.getCallValue() != null) {
                throw new RuntimeException("Function has no parameter");
            }
        }

        if (node.getCallValue() != null) {
            node.getCallValue().accept(this);
        }
        return null;
    }

    @Override
    public Object visitFunctionDefinitionNode(FunctionDefinitionNode node) {
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

        return null;
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
    public Object visitIdentifierNode(IdentifierNode node) {
        String name = node.getName();

        if (getCurrentScope().lookupSymbol(name) == null) {
            throw new RuntimeException("Variable \"" + name + "\" is undefined.");
        }

        if (getCurrentType() == null) {
            Symbol symbol = getCurrentScope().lookupSymbol(name);
            Type thisType = symbol.getType();
            setCurrentType(thisType);
        }
        return null;
    }

    @Override
    public Object visitIfElseNode(IfElseNode node) {
        node.getCondition().accept(this);

        node.getIfBranch().accept(this);

        if (node.getElseBranch() != null) {
            node.getElseBranch().accept(this);
        }
        return null;
    }

    @Override
    public Object visitIntegerConstantNode(IntegerConstantNode node) {
        if (getCurrentType() != Type.INT && getCurrentType() != null) {
            throw new RuntimeException("Operand does not match type: " + getCurrentType());
        }
        if (getCurrentType() == null) {setCurrentType(Type.INT);}
        return null;
    }

    @Override
    public Object visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitNegationNode(NegationNode node) {
        node.getInnerExpressionNode().accept(this);
        return null;
    }

    @Override
    public Object visitParameterDeclarationNode(ParameterDeclarationNode node) {
        return null;
    }

    @Override
    public Object visitParensExpressionNode(ParensExpressionNode node) {
        node.getInnerExpressionNode().accept(this);
        return null;
    }

    @Override
    public Object visitPostFixExpressionNode(PostFixExpressionNode node) {
        node.getIdentifierOrConstant().accept(this);
        return null;
    }

    @Override
    public Object visitRelationalExpressionNode(RelationalExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitReturnStatementNode(ReturnStatementNode node) {
        if (node.getReturnValue() != null) {
            setCurrentType(functionType);
            node.getReturnValue().accept(this);
            setCurrentType(null);
        }
        return null;
    }

    @Override
    public Object visitTranslationUnitNode(TranslationUnitNode node) {
        for (FunctionDefinitionNode functionDefinitionNode : node.getFunctionDefinitionNodeList()) {
            functionDefinitionNode.accept(this);
        }
        return null;
    }

    @Override
    public Object visitTypeSpecifierNode(TypeSpecifierNode node) {

        return null;
    }

    @Override
    public Object visitUnaryExpressionNode(UnaryExpressionNode node) {

        return null;
    }

    @Override
    public Object visitWhileLoopNode(WhileLoopNode node) {
        node.getCondition().accept(this);

        node.getBody().accept(this);
        return null;
    }

    @Override
    public Object visitExpressionStatementNode(ExpressionStatementNode node) {
        setCurrentType(null);
        visitExpressionNode(node.getExpressionNode());
        return null;
    }

    @Override
    public Object visitExpressionNode(ExpressionNode node) {
        setCurrentType(null);
        node.accept(this);

        return null;
    }

    @Override
    public Object visitConstantNode(ConstantNode node) {

        return null;
    }

    public void setFunctionType(Type functionType) {
        this.functionType = functionType;
    }
}
