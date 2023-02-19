package dev.felnull.otyacraftengine.data.model;

import java.util.List;

public interface MutableFileModel extends FileModel {
    void addOverride(FileModel model, List<OverridePredicate> predicates);
}
