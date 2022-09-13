package dev.felnull.otyacraftengine.natives.fnjl;

import dev.felnull.otyacraftengine.natives.fnjl.impl.FNJLNFileChooserFilterWrapperImpl;

public interface FNJLNFileChooserFilterWrapper {
    static FNJLNFileChooserFilterWrapper create(String description, String... extension) {
        return new FNJLNFileChooserFilterWrapperImpl(description, extension);
    }

    String getDescription();

    String[] getExtension();
}
