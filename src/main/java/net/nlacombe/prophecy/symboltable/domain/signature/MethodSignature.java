package net.nlacombe.prophecy.symboltable.domain.signature;

import net.nlacombe.prophecy.symboltable.domain.Type;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MethodSignature implements SymbolSignature {

    private final String methodName;
    private final List<Type> parameterTypes;

    public MethodSignature(String methodName, List<Type> parameterTypes) {
        if (parameterTypes == null || parameterTypes.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("parameterTypes: " + parameterTypes);

        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    public int getNumberOfParameters() {
        return parameterTypes.size();
    }

    public void addParameter(Type type) {
        parameterTypes.add(type);
    }

    public List<String> getParameterTypeNames() {
        return parameterTypes.stream()
            .map(Type::getName)
            .collect(Collectors.toList());
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        var parameterTypeNames = parameterTypes.stream()
            .map(Type::getName)
            .collect(Collectors.joining(", "));

        return methodName + "(" + parameterTypeNames + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;

        if (object == this)
            return true;

        if (!(object instanceof MethodSignature))
            return false;

        MethodSignature signature = (MethodSignature) object;

        if (!StringUtils.equals(signature.getMethodName(), getMethodName()))
            return false;

        if (signature.getParameterTypeNames().size() != parameterTypes.size())
            return false;

        String thisParameter;
        String otherParameter;

        for (int i = 0; i < signature.getParameterTypeNames().size(); i++) {
            thisParameter = parameterTypes.get(i).getName();
            otherParameter = signature.getParameterTypeNames().get(i);

            if (!thisParameter.equals(otherParameter))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
