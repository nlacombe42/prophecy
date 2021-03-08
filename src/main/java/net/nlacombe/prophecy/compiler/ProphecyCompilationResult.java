package net.nlacombe.prophecy.compiler;

import net.nlacombe.prophecy.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.ast.node.ProphecyV2AstNode;

public class ProphecyCompilationResult {

    private final ProphecyV2AstNode astRoot;
    private final GlobalScope globalScope;

    public ProphecyCompilationResult(ProphecyV2AstNode astRoot, GlobalScope globalScope) {
        this.astRoot = astRoot;
        this.globalScope = globalScope;
    }

    public ProphecyV2AstNode getAstRoot() {
        return astRoot;
    }

    public GlobalScope getGlobalScope() {
        return globalScope;
    }
}
