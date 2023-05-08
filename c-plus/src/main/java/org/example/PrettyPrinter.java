package org.example;
import org.example.astnodes.*;


public class PrettyPrinter extends AstVisitor{
    private int indentLevel = 0;

    @Override
    public void visitAdditiveExpressionNode(AdditiveExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }
    }

    @Override
    public void visitArrayDeclarationNode(ArrayDeclarationNode node) {

    }

    @Override
    public void visitArrayIndexNode(ArrayIndexNode node) {

    }

    @Override
    public void visitAssignmentNode(AssignmentExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
    }

    @Override
    public void visitCharacterConstantNode(CharacterConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
    }

    @Override
    public void visitCompoundStatementNode(CompoundStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (BlockItemNode blockItemNode : node.getBlockItemNodeList()) {
            if (blockItemNode instanceof DeclarationNode) {
                visitDeclarationNode((DeclarationNode) blockItemNode);
            } else if (blockItemNode instanceof StatementNode) {

                if (blockItemNode instanceof ReturnStatementNode) {
                    visitReturnStatementNode((ReturnStatementNode) blockItemNode);
                }
                if (blockItemNode instanceof ForLoopNode) {
                    visitForLoopNode((ForLoopNode) blockItemNode);
                }
                if (blockItemNode instanceof ForEachLoopNode) {
                    visitForEachLoopNode((ForEachLoopNode) blockItemNode);
                }
                if (blockItemNode instanceof ExpressionStatementNode) {
                    visitExpressionStatementNode((ExpressionStatementNode) blockItemNode);
                }
                if (blockItemNode instanceof WhileLoopNode) {
                    visitWhileLoopNode((WhileLoopNode) blockItemNode);
                }
                if (blockItemNode instanceof CompoundStatementNode) {
                    visitCompoundStatementNode((CompoundStatementNode) blockItemNode);
                }
                if (blockItemNode instanceof IfElseNode) {
                    visitIfElseNode((IfElseNode) blockItemNode);
                }
            }
        }

    }

    @Override
    public void visitExpressionStatementNode(ExpressionStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();
        visitExpressionNode(node.getExpressionNode());
    }

    @Override
    public void visitDeclarationNode(DeclarationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": isConst " + node.getConst());

        indent();

        visitTypeSpecifierNode(node.getTypeSpecifierNode());

        for (IdentifierNode identifierNode : node.getDeclaratorNodeList()) {
            visitIdentifierNode(identifierNode);
        }

        visitInitializerNode(node.getInitializerNode());

    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {

    }

    @Override
    public void visitExternalDeclarationNode(ExternalDeclarationNode node) {

        System.out.println(getIndentString() + node.getClass().getSimpleName());
        indent();

        if (node.getFuncDefOrDecl() instanceof FunctionDefinitionNode) {
            visitFunctionDefinitionNode((FunctionDefinitionNode) node.getFuncDefOrDecl());
        }
        else if (node.getFuncDefOrDecl() instanceof DeclarationNode) {
            visitDeclarationNode((DeclarationNode) node.getFuncDefOrDecl());
        }

        dedent();;
    }

    @Override
    public void visitFloatConstantNode(FloatConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
    }

    @Override
    public void visitForEachLoopNode(ForEachLoopNode node) {

    }

    @Override
    public void visitForLoopNode(ForLoopNode node) {

    }

    @Override
    public void visitFunctionCallNode(FunctionCallNode node) {

    }

    @Override
    public void visitFunctionDefinitionNode(FunctionDefinitionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitIdentifierNode(node.getName());
        visitParameterDeclarationNode(node.getParameter());
        visitCompoundStatementNode(node.getBody());

        dedent();
    }

    @Override
    public void visitIdentifierNode(IdentifierNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getName());

    }

    @Override
    public void visitIfElseNode(IfElseNode node) {

    }

    @Override
    public void visitIntegerConstantNode(IntegerConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
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
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitTypeSpecifierNode(node.getType());
        visitIdentifierNode(node.getName());

        dedent();

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
        System.out.println(node.getClass().getSimpleName());
        indent();

        for (ExternalDeclarationNode child : node.getExternalDeclarationNodeList()) {
            visitExternalDeclarationNode(child);
        }
        dedent();

    }

    private void indent() {
        indentLevel++;
    }

    private void dedent() {
        indentLevel--;
    }

    private String getIndentString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    @Override
    public void visitTupleDeclarationNode(TupleDeclarationNode node) {

    }

    @Override
    public void visitTypeSpecifierNode(TypeSpecifierNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getType());

    }

    @Override
    public void visitUnaryExpressionNode(UnaryExpressionNode node) {

    }

    @Override
    public void visitVariableDeclarationNode(VariableDeclarationNode node) {

    }

    @Override
    public void visitWhileLoopNode(WhileLoopNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getCondition());
        visitCompoundStatementNode(node.getBody());
    }

    @Override
    public void visitExpressionNode(ExpressionNode node) {
        //System.out.println(getIndentString() + "ExpressionNode debug");
        //System.out.println(getIndentString() + node.getClass().getSimpleName());
        if (node instanceof ConstantNode) {
            visitConstantNode((ConstantNode) node);
        } else if (node instanceof AssignmentExpressionNode) {
            visitAssignmentNode((AssignmentExpressionNode) node);
        } else if (node instanceof LogicalAndExpressionNode) {
            visitLogicalAndExpressionNode((LogicalAndExpressionNode) node);
        } else if (node instanceof LogicalOrExpressionNode) {
            visitLogicalOrExpressionNode((LogicalOrExpressionNode) node);
        } else if (node instanceof MultiplicativeExpressionNode) {
            visitMultiplicativeExpressionNode((MultiplicativeExpressionNode) node);
        } else if (node instanceof NegationNode) {
            visitNegationNode((NegationNode) node);
        } else if (node instanceof ParensExpressionNode) {
            visitParensExpressionNode((ParensExpressionNode) node);
        } else if (node instanceof PostFixExpressionNode) {
            visitPostFixExpressionNode((PostFixExpressionNode) node);
        } else if (node instanceof RelationalExpressionNode) {
            visitRelationalExpressionNode((RelationalExpressionNode) node);
        } else if (node instanceof AdditiveExpressionNode) {
            visitAdditiveExpressionNode((AdditiveExpressionNode) node);
        }
    }

    @Override
    public void visitInitializerNode(InitializerNode node) {

    }

    @Override
    public void visitConstantNode(ConstantNode node) {
        if (node instanceof IntegerConstantNode) {
            visitIntegerConstantNode((IntegerConstantNode) node);
        } else if (node instanceof CharacterConstantNode) {
            visitCharacterConstantNode((CharacterConstantNode) node);
        } else if (node instanceof FloatConstantNode) {
            visitFloatConstantNode((FloatConstantNode) node);
        }
    }
}
