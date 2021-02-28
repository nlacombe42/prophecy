package net.nlacombe.prophecy.v2.compiler;

import net.nlacombe.prophecy.shared.symboltable.domain.scope.GlobalScope;
import net.nlacombe.prophecy.v2.ast.node.ProphecyV2AstNode;

public class ProphecyCompilerResult {

    private final ProphecyV2AstNode astRoot;
    private final GlobalScope globalScope;

    public ProphecyCompilerResult(ProphecyV2AstNode astRoot, GlobalScope globalScope) {
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
