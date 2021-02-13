package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2TreeNode;

public interface ParserRuleContextHandler<ParserRuleContextType> {

    ProphecyV2TreeNode enter(ProphecyV2TreeContext context, ParserRuleContextType parserRuleContext);

    ProphecyV2TreeNode exit(ProphecyV2TreeContext context, ParserRuleContextType parserRuleContext);

}
