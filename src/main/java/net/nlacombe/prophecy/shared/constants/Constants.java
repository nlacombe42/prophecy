package net.nlacombe.prophecy.shared.constants;

import net.nlacombe.prophecy.shared.symboltable.domain.MethodSignature;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;

import java.util.List;

public class Constants {
    public static final String STRING_CLASS_NAME = "String";

    public static final String SYSTEM_CLASS_NAME = "System";
    public static final MethodSignature SYSTEM_PRINTLN_METHODSIGNATURE = new MethodSignature("println", List.of(BuiltInTypeSymbol.tInt));
    public static final String SYSTEM_PRINTLN_PARAM_NAME = "i";

    public static final String MAIN_METHOD_NAME = "main";
}
