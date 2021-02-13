package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2TreeNode;

public class DefaultParserRuleContextHandler<ParserRuleContextType> {
    private final Class<ParserRuleContextType> handledClassType;
    private final ParserRuleContextHandler<ParserRuleContextType> handler;

    private DefaultParserRuleContextHandler(Class<ParserRuleContextType> handledClassType, ParserRuleContextHandler<ParserRuleContextType> handler) {
        this.handledClassType = handledClassType;
        this.handler = handler;
    }

    public static <T> DefaultParserRuleContextHandler<T> from(Class<T> classOfType, ParserRuleContextHandler<T> handler) {
        return new DefaultParserRuleContextHandler<>(classOfType, handler);
    }

    public <T> ProphecyV2TreeNode enter(ProphecyV2TreeContext context, T parserRuleContext) {
        var handledType = getHandledType(parserRuleContext);

        return handler.enter(context, handledType);
    }

    public <T> ProphecyV2TreeNode exit(ProphecyV2TreeContext context, T parserRuleContext) {
        var handledType = getHandledType(parserRuleContext);

        return handler.exit(context, handledType);
    }

    public boolean canHandle(Class<?> type) {
        return handledClassType.isAssignableFrom(type);
    }

    public Class<ParserRuleContextType> getHandledClassType() {
        return handledClassType;
    }

    private <T> ParserRuleContextType getHandledType(T parserRuleContext) {
        if (!canHandle(parserRuleContext.getClass())) {
            var message = "Handler for $handledClassType cannot handle $T"
                .replace("$handledClassType", handledClassType.getName())
                .replace("$T", parserRuleContext.getClass().getName());
            throw new RuntimeException(message);
        }

        return (ParserRuleContextType) parserRuleContext;
    }
}
