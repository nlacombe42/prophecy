package net.nlacombe.prophecy.test.mirror.algorithm;

import net.nlacombe.prophecy.algorithm.MostSpecificCommonType;
import net.nlacombe.prophecy.builtintypes.BootstrapTypeSymbols;
import net.nlacombe.prophecy.symboltable.domain.Type;
import net.nlacombe.prophecy.symboltable.domain.symbol.ClassSymbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MostSpecificCommonTypeTest {

    @Test
    void most_specific_common_type_for_string_and_uint8_is_object() {
        var bootstrapTypeSymbols = BootstrapTypeSymbols.getInstance();
        var stringClass = bootstrapTypeSymbols.getStringClass();
        var uInt8Class = bootstrapTypeSymbols.getUInt8Class();

        var mostSpecificCommonType = MostSpecificCommonType.getMostSpecificCommonType(stringClass, uInt8Class);

        assertEquals(bootstrapTypeSymbols.getObjectClass(), mostSpecificCommonType);
    }

    @Test
    void most_specific_common_type_for_custom_classes_that_inherit_the_same_class_directly() {
        var objectClass = BootstrapTypeSymbols.getInstance().getObjectClass();
        var commonClass = ClassSymbol.newFromClassDefinition("Common", objectClass, null);
        var aClass = ClassSymbol.newFromClassDefinition("A", commonClass, null);
        var bClass = ClassSymbol.newFromClassDefinition("B", commonClass, null);

        var mostSpecificCommonType = MostSpecificCommonType.getMostSpecificCommonType(aClass, bClass);

        assertEquals(commonClass, mostSpecificCommonType);
    }

    @Test
    void most_specific_common_type_for_custom_classes_that_inherit_2_same_parents() {
        var objectClass = BootstrapTypeSymbols.getInstance().getObjectClass();
        var grandParentClass = ClassSymbol.newFromClassDefinition("GrandParent", objectClass, null);
        var parentClass = ClassSymbol.newFromClassDefinition("Parent", grandParentClass, null);
        var aClass = ClassSymbol.newFromClassDefinition("A", parentClass, null);
        var bClass = ClassSymbol.newFromClassDefinition("B", parentClass, null);

        var mostSpecificCommonType = MostSpecificCommonType.getMostSpecificCommonType(aClass, bClass);

        assertEquals(parentClass, mostSpecificCommonType);
    }

    @Test
    void most_specific_common_type_for_custom_cousin_classes() {
        var objectClass = BootstrapTypeSymbols.getInstance().getObjectClass();
        var grandParentClass = ClassSymbol.newFromClassDefinition("GrandParent", objectClass, null);
        var parentAClass = ClassSymbol.newFromClassDefinition("ParentA", grandParentClass, null);
        var parentBClass = ClassSymbol.newFromClassDefinition("ParentB", grandParentClass, null);
        var aClass = ClassSymbol.newFromClassDefinition("A", parentAClass, null);
        var bClass = ClassSymbol.newFromClassDefinition("B", parentBClass, null);

        var mostSpecificCommonType = MostSpecificCommonType.getMostSpecificCommonType(aClass, bClass);

        assertEquals(grandParentClass, mostSpecificCommonType);
    }

    @Test
    void most_specific_common_type_for_custom_classes_where_one_class_is_grand_parent_of_other_class() {
        var objectClass = BootstrapTypeSymbols.getInstance().getObjectClass();
        var grandParentClass = ClassSymbol.newFromClassDefinition("GrandParent", objectClass, null);
        var parentClass = ClassSymbol.newFromClassDefinition("Parent", grandParentClass, null);
        var aClass = ClassSymbol.newFromClassDefinition("A", parentClass, null);

        var mostSpecificCommonType = MostSpecificCommonType.getMostSpecificCommonType(aClass, grandParentClass);

        assertEquals(grandParentClass, mostSpecificCommonType);
    }
}
