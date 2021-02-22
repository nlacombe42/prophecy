package net.nlacombe.prophecy.shared.reporting;

public enum BuildMessageLevel {

    INFO("Info"),
    WARNING("Warning"),
    ERROR("Error");

    private final String text;

    BuildMessageLevel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
