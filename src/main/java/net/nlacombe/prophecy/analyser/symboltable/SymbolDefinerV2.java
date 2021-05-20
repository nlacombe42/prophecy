package net.nlacombe.prophecy.analyser.symboltable;

import net.nlacombe.prophecy.ast.node.ProphecyVariableDeclarationAstNode;
import net.nlacombe.prophecy.reporting.BuildMessageService;
import net.nlacombe.prophecy.symboltable.domain.SymbolSignatureAlreadyDefined;
import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.symboltable.domain.scope.LocalScope;
import net.nlacombe.prophecy.symboltable.domain.scope.Scope;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.ast.ProphecyAstVisitor;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;
import net.nlacombe.prophecy.ast.node.ProphecyFileAstNode;
import net.nlacombe.prophecy.symboltable.domain.symbol.VariableSymbol;

public class SymbolDefinerV2 extends ProphecyAstVisitor<Void> {

    private final GlobalScope globalScope;
    private final MethodSymbol mainMethodSymbol;
    private final BuildMessageService buildMessageService;

    private Scope currentScope;

    public SymbolDefinerV2(GlobalScope globalScope, MethodSymbol mainMethodSymbol, BuildMessageService buildMessageService) {
        this.globalScope = globalScope;
        this.mainMethodSymbol = mainMethodSymbol;
        this.buildMessageService = buildMessageService;

        currentScope = globalScope;
    }

    @Override
    protected void enterEveryAstNode(ProphecyAstNode node) {
        node.setEnclosingScope(currentScope);
    }

    @Override
    protected Void visitFileAstNode(ProphecyFileAstNode node) {
        node.setGlobalScope(globalScope);
        mainMethodSymbol.setDefinitionAstNode(node);

        currentScope = mainMethodSymbol.getMethodBodyScope();

        return visitChildren(node);
    }

    @Override
    protected Void visitVariableDeclarationAstNode(ProphecyVariableDeclarationAstNode node) {
        try {
            var variableSymbol = new VariableSymbol(node.getVariableName());

            currentScope.define(variableSymbol);

            variableSymbol.setDefinitionAstNode(node);
            node.setVariableSymbol(variableSymbol);
        } catch (SymbolSignatureAlreadyDefined exception) {
            var errorMessage = "identifier \"$identifier\" already defined at: $previousLocation"
                .replace("$identifier", node.getVariableName())
                .replace("$previousLocation", exception.getAlreadyDefinedSymbol().getDefinitionAstNode().getDefinitionSourceCodeLocation().toString());

            buildMessageService.error(node.getDefinitionSourceCodeLocation(), errorMessage);
        }

        return visitChildren(node);
    }

    @Override
    protected Void defaultForNonImplementedNodeTypes(ProphecyAstNode node) {
        return null;
    }
}
