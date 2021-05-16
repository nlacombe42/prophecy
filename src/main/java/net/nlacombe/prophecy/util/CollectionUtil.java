package net.nlacombe.prophecy.util;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtil {

    public static <Old extends New, New> List<New> castToGeneric(List<? extends Old> list, Class<New> newTypeClass) {
        return list.stream()
            .map(element -> (New) element)
            .collect(Collectors.toList());
    }

    public static <Old, New extends Old> List<New> castToSpecificWarn(List<? extends Old> list, Class<New> newTypeClass) {
        return list.stream()
            .map(element -> (New) element)
            .collect(Collectors.toList());
    }

}
