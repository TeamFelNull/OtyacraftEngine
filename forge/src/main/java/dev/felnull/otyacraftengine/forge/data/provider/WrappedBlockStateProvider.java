package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.model.BlockStateAndModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.WrappedBlockStateBuilder;
import dev.felnull.otyacraftengine.forge.data.model.BlockMutableFileModelImpl;
import dev.felnull.otyacraftengine.forge.data.model.FileModelImpl;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WrappedBlockStateProvider extends BlockStateProvider {
    private final BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper;

    public WrappedBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper, BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper) {
        super(output, modid, exFileHelper);
        this.blockStateAndModelProviderWrapper = blockStateAndModelProviderWrapper;
    }

    @Override
    protected void registerStatesAndModels() {
        this.blockStateAndModelProviderWrapper.generateStatesAndModels(new BlockStateAndModelProviderAccessImpl());
    }

    private class BlockStateAndModelProviderAccessImpl implements BlockStateAndModelProviderAccess {
        @Override
        public void simpleCubeBlockStateModelAndItemModel(@NotNull Block block) {
            ModelFile model = cubeAll(block);
            simpleBlock(block, model);
            simpleBlockItem(block, model);
        }

        private FileModel of(ModelFile modelFile) {
            return new FileModelImpl(modelFile);
        }

        private MutableFileModel of(BlockModelBuilder blockModelBuilder) {
            return new BlockMutableFileModelImpl(blockModelBuilder);
        }

        @Override
        public @NotNull FileModel cubeAllBlockModel(@NotNull String fileName, @NotNull ResourceLocation texture) {
            return of(models().cubeAll(fileName, texture));
        }

        @Override
        public @NotNull FileModel cubeBlockModel(@NotNull String fileName, @NotNull ResourceLocation down, @NotNull ResourceLocation up, @NotNull ResourceLocation north, @NotNull ResourceLocation south, @NotNull ResourceLocation east, @NotNull ResourceLocation west) {
            return of(models().cube(fileName, down, up, north, south, east, west));
        }

        @Override
        public @NotNull MutableFileModel parentedBlockModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
            return of(models().withExistingParent(name(block), parentLocation));
        }

        @Override
        public @NotNull FileModel existingModel(@NotNull ResourceLocation location) {
            return of(models().getExistingFile(location));
        }

        @Override
        public @NotNull FileModel particleOnlyModel(@NotNull Block block, @NotNull ResourceLocation particleLocation) {
            return of(models().getBuilder(name(block)).texture("particle", particleLocation));
        }

        @Override
        public void simpleBlockState(@NotNull Block block, @NotNull FileModel model) {
            simpleBlock(block, FileModelImpl.getModelFile(model));
        }

        @Override
        public void simpleBlockItemModel(@NotNull Block block, @NotNull FileModel model) {
            simpleBlockItem(block, FileModelImpl.getModelFile(model));
        }

        @Override
        public void horizontalBlockState(@NotNull Block block, @NotNull FileModel model) {
            horizontalBlock(block, FileModelImpl.getModelFile(model));
        }

        @Override
        public void parentedBlockItemModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
            var name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(block.asItem()));
            itemModels().getBuilder(name.toString()).parent(new ModelFile.UncheckedModelFile(parentLocation));
        }

        @Override
        public void addBlockStateGenerator(@NotNull BlockStateGenerator blockStateGenerator) {
            Block block = blockStateGenerator.getBlock();
            if (registeredBlocks.containsKey(block))
                throw new IllegalStateException("Duplicate registration");

            registeredBlocks.put(block, new WrappedBlockStateBuilder(blockStateGenerator));
        }

        private String name(Block block) {
            return this.key(block).getPath();
        }

        private ResourceLocation key(Block block) {
            return ForgeRegistries.BLOCKS.getKey(block);
        }
    }
}
