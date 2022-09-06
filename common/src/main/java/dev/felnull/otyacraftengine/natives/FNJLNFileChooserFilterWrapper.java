package dev.felnull.otyacraftengine.natives;

import dev.felnull.otyacraftengine.natives.impl.FNJLNFileChooserFilterWrapperImpl;

public interface FNJLNFileChooserFilterWrapper {
    static FNJLNFileChooserFilterWrapper create(String description, String... extension) {
        return new FNJLNFileChooserFilterWrapperImpl(description, extension);
    }

    String getDescription();

    String[] getExtension();
}
