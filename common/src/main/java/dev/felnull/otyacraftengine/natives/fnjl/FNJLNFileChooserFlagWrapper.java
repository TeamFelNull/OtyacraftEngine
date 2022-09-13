package dev.felnull.otyacraftengine.natives.fnjl;

import dev.felnull.otyacraftengine.natives.fnjl.impl.FNJLNFileChooserFlagWrapperImpl;

public interface FNJLNFileChooserFlagWrapper {
    static FNJLNFileChooserFlagWrapper create() {
        return new FNJLNFileChooserFlagWrapperImpl();
    }

    FNJLNFileChooserFlagWrapper allowMultiSelect(boolean allow);

    FNJLNFileChooserFlagWrapper explorer(boolean explorer);

    FNJLNFileChooserFlagWrapper creatEPrompt(boolean creatEPrompt);

    FNJLNFileChooserFlagWrapper fileMustExist(boolean fileMustExist);

    FNJLNFileChooserFlagWrapper hideReadOnly(boolean hideReadOnly);

    FNJLNFileChooserFlagWrapper nodeReferenceLinks(boolean nodeReferenceLinks);

    FNJLNFileChooserFlagWrapper readOnly(boolean readOnly);

    boolean isCreatEPrompt();

    boolean isExplorer();

    boolean isFileMustExist();

    boolean isHideReadOnly();

    boolean isMultiSelect();

    boolean isNodeReferenceLinks();

    boolean isReadOnly();
}
