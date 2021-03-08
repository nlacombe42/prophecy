package net.nlacombe.prophecy.symboltable.domain.signature;

import org.apache.commons.lang3.StringUtils;

public class NameOnlySymbolSignature implements SymbolSignature {

    private final String name;

    public NameOnlySymbolSignature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof NameOnlySymbolSignature))
            return false;

        var nameOnlySymbolSignature = (NameOnlySymbolSignature) obj;

        return StringUtils.equals(nameOnlySymbolSignature.getName(), name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
