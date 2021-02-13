package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.parser.antlr4.ProphecyV2BaseListener;
import net.nlacombe.prophecy.parser.antlr4.ProphecyV2Parser.FileContext;
import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2TreeNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProphecyV2TreeBuilderAntlAstWalker extends ProphecyV2BaseListener {

    private static class GenericContainerHandler implements ParserRuleContextHandler<ParserRuleContext> {
        @Override
        public ProphecyV2TreeNode enter(ProphecyV2TreeContext context, ParserRuleContext parserRuleContext) {
            return null;
        }

        @Override
        public ProphecyV2TreeNode exit(ProphecyV2TreeContext context, ParserRuleContext parserRuleContext) {
            return null;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ProphecyV2TreeBuilderAntlAstWalker.class);

    private List<DefaultParserRuleContextHandler<?>> handlers;
    private ProphecyV2TreeContext treeContext;

    public ProphecyV2TreeBuilderAntlAstWalker() {
        handlers = new ArrayList<>();

        handlers.add(DefaultParserRuleContextHandler.from(FileContext.class, new ParserRuleContextHandler<>() {
            @Override
            public ProphecyV2TreeNode enter(ProphecyV2TreeContext context, FileContext parserRuleContext) {
                return null;
            }

            @Override
            public ProphecyV2TreeNode exit(ProphecyV2TreeContext context, FileContext parserRuleContext) {
                return null;
            }
        }));
    }

    public ProphecyV2TreeNode getTreeRoot() {
        return null;
    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
