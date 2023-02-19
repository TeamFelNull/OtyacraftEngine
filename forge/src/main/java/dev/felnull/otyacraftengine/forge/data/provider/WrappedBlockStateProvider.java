package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.WrappedBlockStateBuilder;
import dev.felnull.otyacraftengine.forge.data.model.FileModelImpl;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

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

    private class BlockStateAndModelProviderAccessImpl implements BlockStateAndModelProviderWrapper.BlockStateAndModelProviderAccess {
        @Override
        public void genSimpleCubeBlockStateModelAndItemModel(Block block) {
            ModelFile model = cubeAll(block);
            simpleBlock(block, model);
            simpleBlockItem(block, model);
        }

        private FileModel of(ModelFile modelFile) {
            return new FileModelImpl(modelFile);
        }

        @Override
        public FileModel genCubeAllBlockModel(String fileName, ResourceLocation texture) {
            return of(models().cubeAll(fileName, texture));
        }

        @Override
        public FileModel genCubeBlockModel(String fileName, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
            return of(models().cube(fileName, down, up, north, south, east, west));
        }

        @Override
        public FileModel getExistingModel(ResourceLocation location) {
            return of(models().getExistingFile(location));
        }

        @Override
        public void genSimpleBlockState(Block block, FileModel model) {
            simpleBlock(block, FileModelImpl.getModelFile(model));
        }

        @Override
        public void genSimpleBlockItemModel(Block block, FileModel model) {
            simpleBlockItem(block, FileModelImpl.getModelFile(model));
        }

        @Override
        public void genHorizontalBlockState(Block block, FileModel model) {
            horizontalBlock(block, FileModelImpl.getModelFile(model));
        }

        @Override
        public void genBuiltinEntityBlockItemModel(Block block) {
            genParentedBlockItemModel(block, new ResourceLocation("builtin/entity"));
        }

        @Override
        public void genParentedBlockItemModel(Block block, ResourceLocation parentLocation) {
            var name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(block.asItem()));
            itemModels().getBuilder(name.toString()).parent(new ModelFile.UncheckedModelFile(parentLocation));
        }

        @Override
        public void addBlockStateGenerator(BlockStateGenerator blockStateGenerator) {
            Block block = blockStateGenerator.getBlock();
            if (registeredBlocks.containsKey(block))
                throw new IllegalStateException("Duplicate registration");

            registeredBlocks.put(block, new WrappedBlockStateBuilder(blockStateGenerator));
        }
    }
}
