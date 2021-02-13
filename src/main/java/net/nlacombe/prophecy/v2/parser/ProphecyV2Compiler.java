package net.nlacombe.prophecy.v2.parser;

import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import net.nlacombe.prophecy.v2.parser.tree.ProphecyV2TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;

public class ProphecyV2Compiler {

    private static final Logger logger = LoggerFactory.getLogger(ProphecyV2Compiler.class);

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ProphecyV2Compiler(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void compile() {
        var parser = new ProphecyV2TreeBuilder(inputStream);
        ProphecyV2TreeNode treeRoot;

        try {
            logger.info("Building AST...");
            treeRoot = parser.parse();
        } catch (ProphecyCompilerException e) {
            logger.error(e.getMessage());
            return;
        }

        logger.info("tree root: " + treeRoot);
    }
}
