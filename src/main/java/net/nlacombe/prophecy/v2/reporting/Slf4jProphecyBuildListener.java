package net.nlacombe.prophecy.v2.reporting;

import net.nlacombe.prophecy.shared.reporting.BuildMessageLevel;
import net.nlacombe.prophecy.shared.reporting.ProphecyBuildListener;
import net.nlacombe.prophecy.v2.exception.ProphecyCompilerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jProphecyBuildListener implements ProphecyBuildListener {
    private static final Logger logger = LoggerFactory.getLogger(Slf4jProphecyBuildListener.class);

    @Override
    public void buildMessage(BuildMessageLevel level, String buildMessage) {
        var message = level.getText() + " " + buildMessage;

        if (BuildMessageLevel.ERROR.equals(level)) {
            logger.error(message);
        } else if (BuildMessageLevel.WARNING.equals(level)) {
            logger.warn(message);
        } else if (BuildMessageLevel.INFO.equals(level)) {
            logger.warn(message);
        } else {
            throw new ProphecyCompilerException("Unimplemented build message level: " + level);
        }
    }

    @Override
    public void buildMessage(BuildMessageLevel level, int line, int column, String buildMessage) {
        var message = "at $line:$column : $buildMessage"
            .replace("$line", String.valueOf(line))
            .replace("$column", String.valueOf(column))
            .replace("$buildMessage", buildMessage);
        buildMessage(level, message);
    }
}
