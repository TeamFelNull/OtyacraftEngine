package dev.felnull.otyacraftengine.forge.data.model;

import dev.felnull.otyacraftengine.data.model.BlockStateAndModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.ItemModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.forge.data.WrappedBlockStateBuilder;
import dev.felnull.otyacraftengine.forge.mixin.data.BlockStateProviderAccessor;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlockStateAndModelProviderAccessImpl implements BlockStateAndModelProviderAccess {
    private final BlockStateProvider blockStateProvider;
    private final ItemModelProviderAccess itemModelProviderAccess;

    public BlockStateAndModelProviderAccessImpl(BlockStateProvider blockStateProvider) {
        this.blockStateProvider = blockStateProvider;
        this.itemModelProviderAccess = new ItemModelProviderAccessImpl(blockStateProvider.itemModels());
    }

    @Override
    public void simpleCubeBlockStateModelAndItemModel(@NotNull Block block) {
        ModelFile model = this.blockStateProvider.cubeAll(block);
        this.blockStateProvider.simpleBlock(block, model);
        this.blockStateProvider.simpleBlockItem(block, model);
    }

    private FileModel of(ModelFile modelFile) {
        return new FileModelImpl(modelFile);
    }

    private MutableFileModel of(BlockModelBuilder blockModelBuilder) {
        return new BlockMutableFileModelImpl(blockModelBuilder);
    }

    @Override
    public @NotNull FileModel cubeAllBlockModel(@NotNull String fileName, @NotNull ResourceLocation texture) {
        return of(this.blockStateProvider.models().cubeAll(fileName, texture));
    }

    @Override
    public @NotNull FileModel cubeBlockModel(@NotNull String fileName, @NotNull ResourceLocation down, @NotNull ResourceLocation up, @NotNull ResourceLocation north, @NotNull ResourceLocation south, @NotNull ResourceLocation east, @NotNull ResourceLocation west) {
        return of(this.blockStateProvider.models().cube(fileName, down, up, north, south, east, west));
    }

    @Override
    public @NotNull MutableFileModel parentedBlockModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
        return of(this.blockStateProvider.models().withExistingParent(name(block), parentLocation));
    }

    @Override
    public @NotNull FileModel existingModel(@NotNull ResourceLocation location) {
        return of(this.blockStateProvider.models().getExistingFile(location));
    }

    @Override
    public @NotNull FileModel particleOnlyModel(@NotNull Block block, @NotNull ResourceLocation particleLocation) {
        return of(this.blockStateProvider.models().getBuilder(name(block)).texture("particle", particleLocation));
    }

    @Override
    public void simpleBlockState(@NotNull Block block, @NotNull FileModel model) {
        this.blockStateProvider.simpleBlock(block, FileModelImpl.getModelFile(model));
    }

    @Override
    public void simpleBlockItemModel(@NotNull Block block, @NotNull FileModel model) {
        this.blockStateProvider.simpleBlockItem(block, FileModelImpl.getModelFile(model));
    }

    @Override
    public void horizontalBlockState(@NotNull Block block, @NotNull FileModel model) {
        this.blockStateProvider.horizontalBlock(block, FileModelImpl.getModelFile(model));
    }

    @Override
    public void parentedBlockItemModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
        var name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(block.asItem()));
        this.blockStateProvider.itemModels().getBuilder(name.toString()).parent(new ModelFile.UncheckedModelFile(parentLocation));
    }

    @Override
    public void addBlockStateGenerator(@NotNull BlockStateGenerator blockStateGenerator) {
        Block block = blockStateGenerator.getBlock();
        if (((BlockStateProviderAccessor) this.blockStateProvider).getRegisteredBlocks().containsKey(block))
            throw new IllegalStateException("Duplicate registration");

        ((BlockStateProviderAccessor) this.blockStateProvider).getRegisteredBlocks().put(block, new WrappedBlockStateBuilder(blockStateGenerator));
    }

    @Override
    public ItemModelProviderAccess itemModelProviderAccess() {
        return this.itemModelProviderAccess;
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}