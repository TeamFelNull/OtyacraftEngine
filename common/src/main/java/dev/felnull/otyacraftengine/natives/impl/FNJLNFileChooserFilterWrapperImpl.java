package dev.felnull.otyacraftengine.natives.impl;

import dev.felnull.otyacraftengine.natives.FNJLNFileChooserFilterWrapper;

public class FNJLNFileChooserFilterWrapperImpl implements FNJLNFileChooserFilterWrapper {
    private final String description;
    private final String[] extension;

    public FNJLNFileChooserFilterWrapperImpl(String description, String... extension) {
        this.description = description;
        this.extension = extension;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String[] getExtension() {
        return extension;
    }
}
