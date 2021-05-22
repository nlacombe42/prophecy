package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;

import java.io.IOException;
import java.io.Writer;

public class UInt8ArrayLlvmGenerator {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();

    public static LlvmSymbol generateUInt8ArrayGet(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, LlvmSymbol arrayPointerLlvmSymbol, LlvmSymbol indexLlvmSymbol) {
        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(bootstrapTypeSymbols.getUInt8Class());
            var indexOffsetName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var indexPointerName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();

            var llvmCode = """
                $indexOffsetName = add i8 $indexName, 1 ; get value from UInt8 array start
                $indexPointerName = getelementptr i8, i8* $arrayPointerName, i8 $indexOffsetName
                $returnValueName = load i8, i8* $indexPointerName ; get value from UInt8 array end
                """
                .replace("$indexOffsetName", indexOffsetName)
                .replace("$arrayPointerName", arrayPointerLlvmSymbol.getName())
                .replace("$indexPointerName", indexPointerName)
                .replace("$returnValueName", returnValueName)
                .replace("$indexName", indexLlvmSymbol.getName());

            writer.write(llvmCode);

            return new LlvmSymbol(llvmType, returnValueName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LlvmSymbol generateUInt8ArraySize(Writer writer, LlvmTemporaryNameGenerator llvmTemporaryNameGenerator, LlvmSymbol arrayPointerLlvmSymbol) {
        try {
            var llvmType = LlvmGeneratorUtil.getLlvmType(bootstrapTypeSymbols.getUInt8Class());
            var indexPointerName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();
            var returnValueName = llvmTemporaryNameGenerator.getNewTemporaryLlvmName();

            var llvmCode = """
                $indexPointerName = getelementptr i8, i8* $arrayPointerName, i8 0 ; get size from UInt8 array start
                $returnValueName = load i8, i8* $indexPointerName ; get size from UInt8 array end
                """
                .replace("$arrayPointerName", arrayPointerLlvmSymbol.getName())
                .replace("$indexPointerName", indexPointerName)
                .replace("$returnValueName", returnValueName);

            writer.write(llvmCode);

            return new LlvmSymbol(llvmType, returnValueName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
