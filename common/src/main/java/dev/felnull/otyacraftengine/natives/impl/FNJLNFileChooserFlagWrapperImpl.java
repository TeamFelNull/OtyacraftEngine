package dev.felnull.otyacraftengine.natives.impl;

import dev.felnull.otyacraftengine.natives.FNJLNFileChooserFlagWrapper;

public class FNJLNFileChooserFlagWrapperImpl implements FNJLNFileChooserFlagWrapper {
    private boolean multiSelect;
    private boolean explorer;
    private boolean creatEPrompt;
    private boolean fileMustExist;
    private boolean hideReadOnly;
    private boolean nodeReferenceLinks;
    private boolean readOnly;

    @Override
    public FNJLNFileChooserFlagWrapper allowMultiSelect(boolean allow) {
        this.multiSelect = allow;
        return this;
    }

    @Override
    public FNJLNFileChooserFlagWrapper explorer(boolean explorer) {
        this.explorer = explorer;
        return this;
    }

    @Override
    public FNJLNFileChooserFlagWrapper creatEPrompt(boolean creatEPrompt) {
        this.creatEPrompt = creatEPrompt;
        return this;
    }

    @Override
    public FNJLNFileChooserFlagWrapper fileMustExist(boolean fileMustExist) {
        this.fileMustExist = fileMustExist;
        return this;
    }

    @Override
    public FNJLNFileChooserFlagWrapper hideReadOnly(boolean hideReadOnly) {
        this.hideReadOnly = hideReadOnly;
        return this;
    }

    @Override
    public FNJLNFileChooserFlagWrapper nodeReferenceLinks(boolean nodeReferenceLinks) {
        this.nodeReferenceLinks = nodeReferenceLinks;
        return this;
    }

    @Override
    public FNJLNFileChooserFlagWrapper readOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    @Override
    public boolean isCreatEPrompt() {
        return creatEPrompt;
    }

    @Override
    public boolean isExplorer() {
        return explorer;
    }

    @Override
    public boolean isFileMustExist() {
        return fileMustExist;
    }

    @Override
    public boolean isHideReadOnly() {
        return hideReadOnly;
    }

    @Override
    public boolean isMultiSelect() {
        return multiSelect;
    }

    @Override
    public boolean isNodeReferenceLinks() {
        return nodeReferenceLinks;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }
}
