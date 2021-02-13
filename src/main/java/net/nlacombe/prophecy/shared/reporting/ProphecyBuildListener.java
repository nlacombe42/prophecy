package net.nlacombe.prophecy.shared.reporting;

public interface ProphecyBuildListener {

    void buildMessage(BuildMessageLevel level, String message);

    void buildMessage(BuildMessageLevel level, int line, int column, String message);

}
