package net.nlacombe.prophecy.symboltable.domain;

public interface Type {

    String getName();

    boolean canAssignTo(Type type);

}
