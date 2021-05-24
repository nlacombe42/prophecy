package net.nlacombe.prophecy.generator;

import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.builtintypes.ProphecySpecialTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.symbol.MethodSymbol;
import net.nlacombe.prophecy.util.WriterUtil;

import java.io.Writer;
import java.util.List;

public class ArrayLlvmGenerator {

    private static final BootstrapTypeSymbols bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
    private static final ProphecySpecialTypeSymbols specialTypeSymbols = ProphecySpecialTypeSymbols.getInstance();

    public static LlvmSymbol generateUInt8ArrayGet(Writer writer, LlvmContext llvmContext, LlvmSymbol arrayPointerLlvmSymbol, LlvmSymbol indexLlvmSymbol) {
        var llvmType = LlvmGeneratorUtil.getLlvmType(bootstrapTypeSymbols.getUInt8Class());
        var indexOffsetName = llvmContext.getNewTemporaryLlvmName();
        var indexPointerName = llvmContext.getNewTemporaryLlvmName();
        var returnValueName = llvmContext.getNewTemporaryLlvmName();

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

        WriterUtil.writeRuntimeException(writer, llvmCode);

        return new LlvmSymbol(llvmType, returnValueName);
    }

    public static LlvmSymbol generateUInt8ArraySize(Writer writer, LlvmContext llvmContext, LlvmSymbol arrayPointerLlvmSymbol) {
        var llvmType = LlvmGeneratorUtil.getLlvmType(bootstrapTypeSymbols.getUInt8Class());
        var indexPointerName = llvmContext.getNewTemporaryLlvmName();
        var returnValueName = llvmContext.getNewTemporaryLlvmName();

        var llvmCode = """
            $indexPointerName = getelementptr i8, i8* $arrayPointerName, i8 0 ; get size from UInt8 array start
            $returnValueName = load i8, i8* $indexPointerName ; get size from UInt8 array end
            """
            .replace("$arrayPointerName", arrayPointerLlvmSymbol.getName())
            .replace("$indexPointerName", indexPointerName)
            .replace("$returnValueName", returnValueName);

        WriterUtil.writeRuntimeException(writer, llvmCode);

        return new LlvmSymbol(llvmType, returnValueName);
    }

    public static LlvmSymbol generateArrayRange(Writer writer, LlvmContext llvmContext, LlvmSymbol startLlvmSymbol, LlvmSymbol endLlvmSymbol) {
        var subTmpName = llvmContext.getNewTemporaryLlvmName();
        var allocSizeTmpName = llvmContext.getNewTemporaryLlvmName();
        var arraySizeTmpName = llvmContext.getNewTemporaryLlvmName();
        var arrayLlvmType = LlvmGeneratorUtil.getLlvmType(specialTypeSymbols.getUInt8Array());
        var arrayLlvmName = llvmContext.getNewTemporaryLlvmName();
        var arrayLlvmSymbol = new LlvmSymbol(arrayLlvmType, arrayLlvmName);


        var llvmCode = """
            $subTmpName = sub i8 $endLlvmName, $startLlvmName ; Array.range() start
            $allocSizeTmpName = add i8 $subTmpName, 1
            $arraySizeTmpName = sub i8 $allocSizeTmpName, 1
            $arrayTmpName = alloca i8, i8 $allocSizeTmpName
            store i8 $arraySizeTmpName, i8* $arrayTmpName ; Array.range() ends after next call
            """
            .replace("$startLlvmName", startLlvmSymbol.getName())
            .replace("$endLlvmName", endLlvmSymbol.getName())
            .replace("$subTmpName", subTmpName)
            .replace("$allocSizeTmpName", allocSizeTmpName)
            .replace("$arraySizeTmpName", arraySizeTmpName)
            .replace("$arrayTmpName", arrayLlvmName)
            ;

        WriterUtil.writeRuntimeException(writer, llvmCode);

        var internalArrayRangeMethodSignature = specialTypeSymbols.getInternalArrayRangeMethodSignature();
        var internalArrayRangeMethodSymbol = (MethodSymbol) bootstrapTypeSymbols.getArrayClass().resolve(internalArrayRangeMethodSignature);
        var arguments = List.of(arrayLlvmSymbol, startLlvmSymbol, endLlvmSymbol);

        LlvmGeneratorCallUtil.generateCallToProphecyMethod(writer, llvmContext, internalArrayRangeMethodSymbol, arguments);

        return new LlvmSymbol(arrayLlvmType, arrayLlvmName);
    }
}
