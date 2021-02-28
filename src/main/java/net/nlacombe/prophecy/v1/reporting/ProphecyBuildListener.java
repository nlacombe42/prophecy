package net.nlacombe.prophecy.v1.reporting;

public interface ProphecyBuildListener {

    void buildMessage(BuildMessageLevel level, String message);

    void buildMessage(BuildMessageLevel level, int line, int column, String message);

}
