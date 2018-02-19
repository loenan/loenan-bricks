package com.loenan.bricks.ldraw.model;

import static java.util.Objects.requireNonNull;

public class MetaCommand implements CommandLine {

    private final String command;

    private final String parameters;

    public MetaCommand(String command) {
        this(command, null);
    }

    public MetaCommand(String command, String parameters) {
        this.command = requireNonNull(command);
        this.parameters = parameters;
    }

    public String getCommand() {
        return command;
    }

    public String getParameters() {
        return parameters;
    }

    @Override
    public String getLine() {
        return "0 " + command + (parameters != null ? " " + parameters : "");
    }
}
