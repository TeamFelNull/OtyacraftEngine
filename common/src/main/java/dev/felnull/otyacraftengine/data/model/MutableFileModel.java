package dev.felnull.otyacraftengine.data.model;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MutableFileModel extends FileModel {
    void override(@NotNull FileModel model, @NotNull List<OverridePredicate> predicates);

    void texture(@NotNull String id, @NotNull ResourceLocation location);
}
