package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.exception.ProphecyCompilerException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;

public class LlvmGeneratorPointerUtil {

    public static LlvmSymbol convert(Writer writer, LlvmContext llvmContext, LlvmSymbol symbol, String targetType) {
        var symbolType = symbol.getType();

        if (!symbolType.endsWith("*") && !targetType.endsWith("*") && !StringUtils.equals(symbolType, targetType))
            throw new ProphecyCompilerException("llvm types fundamentally incompatible: " + symbolType + " and " + targetType);

        var symbolPointerLevel = StringUtils.countMatches(symbolType, "*");
        var targetPointerLevel = StringUtils.countMatches(targetType, "*");

        if (symbolPointerLevel > targetPointerLevel)
            return convert(writer, llvmContext, dereference(writer, llvmContext, symbol), targetType);
        else if (symbolPointerLevel < targetPointerLevel)
            return convert(writer, llvmContext, wrap(writer, llvmContext, symbol, null), targetType);
        else
            return symbol;
    }

    public static LlvmSymbol getPointer(Writer writer, LlvmContext llvmContext, LlvmSymbol symbol) {
        var symbolType = symbol.getType();

        if (symbolType.endsWith("***"))
            throw new ProphecyCompilerException("getting a pointer to this llvm type is unsupported: " + symbol);

        if (symbolType.endsWith("**"))
            return dereference(writer, llvmContext, symbol);
        else if (symbolType.endsWith("*"))
            return symbol;
        else
            return wrap(writer, llvmContext, symbol, null);
    }

    public static LlvmSymbol wrap(Writer writer, LlvmContext llvmContext, LlvmSymbol symbol, String wrapperName) {
        try {
            var symbolType = symbol.getType();
            var allocPointerName = wrapperName == null ? llvmContext.getNewTemporaryLlvmName() : wrapperName;
            var allocPointerType = symbolType + "*";

            var llvmCode = """
                $allocPointerName = alloca $symbolType ; wrap start
                store $symbolType $symbolName, $allocPointerType $allocPointerName ; wrap end
                """
                .replace("$allocPointerType", allocPointerType)
                .replace("$allocPointerName", allocPointerName)
                .replace("$symbolType", symbolType)
                .replace("$symbolName", symbol.getName());

            writer.write(llvmCode);

            return new LlvmSymbol(allocPointerType, allocPointerName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LlvmSymbol dereference(Writer writer, LlvmContext llvmContext, LlvmSymbol symbol) {
        try {
            var symbolType = symbol.getType();

            if (!symbolType.endsWith("*"))
                throw new ProphecyCompilerException("cannot dereference since this is not a llvm pointer: " + symbol);

            var dereferenceType = StringUtils.removeEnd(symbolType, "*");
            var dereferenceName = llvmContext.getNewTemporaryLlvmName();

            var llvmCode = """
                $dereferenceName = load $dereferenceType, $symbolType $symbolName ; dereference
                """
                .replace("$dereferenceType", dereferenceType)
                .replace("$dereferenceName", dereferenceName)
                .replace("$symbolType", symbolType)
                .replace("$symbolName", symbol.getName());

            writer.write(llvmCode);

            return new LlvmSymbol(dereferenceType, dereferenceName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
