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

        dedent();
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
    dedent();
    }

    @Override
    public void visitExpressionStatementNode(ExpressionStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();
        visitExpressionNode(node.getExpressionNode());
        dedent();
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

        dedent();

    }

    @Override
    public void visitEqualityExpressionNode(EqualityExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
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
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitTypeSpecifierNode(node.getTypeSpecifierNode());

        visitIdentifierNode(node.getElementIdentifier());
        visitIdentifierNode(node.getArrayIdentifier());
        visitCompoundStatementNode(node.getBody());

        dedent();
    }

    @Override
    public void visitForLoopNode(ForLoopNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
    }

    @Override
    public void visitFunctionCallNode(FunctionCallNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
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
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getCondition());
        visitCompoundStatementNode(node.getIfBranch());

        if (node.getElseBranch() != null) {
            visitCompoundStatementNode(node.getElseBranch());
        }

        dedent();
    }

    @Override
    public void visitIntegerConstantNode(IntegerConstantNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName() + ": " + node.getValue());
    }

    @Override
    public void visitLogicalAndExpressionNode(LogicalAndExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitLogicalOrExpressionNode(LogicalOrExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitMultiplicativeExpressionNode(MultiplicativeExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitNegationNode(NegationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getInnerExpressionNode());

        dedent();
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
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getInnerExpressionNode());

        dedent();
    }

    @Override
    public void visitPostFixExpressionNode(PostFixExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getIdentifierOrConstant());
        System.out.println(getIndentString() + "Operator: " + node.getOperator());

        dedent();
    }

    @Override
    public void visitRelationalExpressionNode(RelationalExpressionNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        for (ExpressionNode expressionNode : node.getOperands()) {
            visitExpressionNode(expressionNode);
        }

        dedent();
    }

    @Override
    public void visitReturnStatementNode(ReturnStatementNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getReturnValue());

        dedent();
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
        System.out.println(getIndentString() + node.getClass().getSimpleName());
    }

    @Override
    public void visitVariableDeclarationNode(VariableDeclarationNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
    }

    @Override
    public void visitWhileLoopNode(WhileLoopNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());

        indent();

        visitExpressionNode(node.getCondition());
        visitCompoundStatementNode(node.getBody());

        dedent();
    }

    @Override
    public void visitExpressionNode(ExpressionNode node) {
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
        } else if (node instanceof IdentifierNode) {
            visitIdentifierNode((IdentifierNode) node);
        } else if (node instanceof EqualityExpressionNode) {
            visitEqualityExpressionNode((EqualityExpressionNode) node);
        }
    }

    @Override
    public void visitInitializerNode(InitializerNode node) {
        System.out.println(getIndentString() + node.getClass().getSimpleName());
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
