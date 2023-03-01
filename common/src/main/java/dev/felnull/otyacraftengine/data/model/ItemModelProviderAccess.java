package dev.felnull.otyacraftengine.data.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public interface ItemModelProviderAccess {
    @NotNull
    MutableFileModel basicFlatItem(@NotNull Item item);

    @NotNull
    MutableFileModel basicFlatItem(@NotNull ResourceLocation itemLocation);

    @NotNull
    MutableFileModel handheldFlatItem(@NotNull Item item);

    @NotNull
    MutableFileModel handheldFlatItem(@NotNull ResourceLocation itemLocation);

    @NotNull
    MutableFileModel builtinEntityItem(@NotNull Item item);

    @NotNull
    MutableFileModel builtinEntityItem(@NotNull ResourceLocation itemLocation);

    @NotNull
    MutableFileModel parentedItem(@NotNull Item item, @NotNull ResourceLocation parentLocation);

    @NotNull
    MutableFileModel parentedItem(@NotNull ResourceLocation itemLocation, @NotNull ResourceLocation parentLocation);
}
