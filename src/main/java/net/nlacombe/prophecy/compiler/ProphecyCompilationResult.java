package net.nlacombe.prophecy.compiler;

import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.ast.node.ProphecyAstNode;

public class ProphecyCompilationResult {

    private final ProphecyAstNode astRoot;
    private final GlobalScope globalScope;

    public ProphecyCompilationResult(ProphecyAstNode astRoot, GlobalScope globalScope) {
        this.astRoot = astRoot;
        this.globalScope = globalScope;
    }

    public ProphecyAstNode getAstRoot() {
        return astRoot;
    }

    public GlobalScope getGlobalScope() {
        return globalScope;
    }
}
