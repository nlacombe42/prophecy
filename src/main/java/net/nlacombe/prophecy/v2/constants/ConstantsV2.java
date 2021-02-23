package net.nlacombe.prophecy.v2.constants;

import net.nlacombe.prophecy.shared.symboltable.domain.MethodSignature;
import net.nlacombe.prophecy.shared.symboltable.domain.symbol.BuiltInTypeSymbol;

import java.util.List;

public class ConstantsV2 {

    public static final MethodSignature PRINTLN_INT_SYSTEM_METHOD_SIGNATURE = new MethodSignature("println", List.of(BuiltInTypeSymbol.tInt));
    public static final MethodSignature MAIN_METHOD_SIGNATURE = new MethodSignature("main", List.of());

}
