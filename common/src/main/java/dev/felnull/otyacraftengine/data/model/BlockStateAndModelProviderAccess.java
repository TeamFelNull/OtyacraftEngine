package dev.felnull.otyacraftengine.data.model;

import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public interface BlockStateAndModelProviderAccess {
    void simpleCubeBlockStateModelAndItemModel(@NotNull Block block);

    @NotNull
    FileModel cubeAllBlockModel(@NotNull String fileName, @NotNull ResourceLocation texture);

    @NotNull
    FileModel cubeBlockModel(@NotNull String fileName, @NotNull ResourceLocation down, @NotNull ResourceLocation up, @NotNull ResourceLocation north, @NotNull ResourceLocation south, @NotNull ResourceLocation east, @NotNull ResourceLocation west);

    @NotNull
    MutableFileModel parentedBlockModel(@NotNull Block block, @NotNull ResourceLocation parentLocation);

    @NotNull
    FileModel existingModel(@NotNull ResourceLocation location);

    @NotNull
    FileModel particleOnlyModel(@NotNull Block block, @NotNull ResourceLocation particleLocation);

    void simpleBlockState(@NotNull Block block, @NotNull FileModel model);

    /**
     * Fabricの場合、ステートを登録していればアイテムモデル未登録でも勝手に自動登録される。
     * でもForgeでは自動登録されないのでこれを利用してください。
     *
     * @param block ブロック
     * @param model モデル
     */
    void simpleBlockItemModel(@NotNull Block block, @NotNull FileModel model);

    void horizontalBlockState(@NotNull Block block, @NotNull FileModel model);

    default void builtinEntityBlockItemModel(@NotNull Block block) {
        parentedBlockItemModel(block, new ResourceLocation("builtin/entity"));
    }

    void parentedBlockItemModel(@NotNull Block block, @NotNull ResourceLocation parentLocation);

    void addBlockStateGenerator(@NotNull BlockStateGenerator blockStateGenerator);
}
