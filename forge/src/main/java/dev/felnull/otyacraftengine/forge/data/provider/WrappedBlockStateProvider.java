package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.WrappedBlockStateBuilder;
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

        private BlockStateAndModelProviderWrapper.FileModel of(ModelFile modelFile) {
            return new FileModelImpl(modelFile);
        }

        private ModelFile getModelFile(BlockStateAndModelProviderWrapper.FileModel fileModel) {
            if (fileModel instanceof FileModelImpl fmi)
                return fmi.modelFile();

            throw new IllegalStateException("Not forge impl model file");
        }

        @Override
        public BlockStateAndModelProviderWrapper.FileModel genCubeAllBlockModel(String fileName, ResourceLocation texture) {
            return of(models().cubeAll(fileName, texture));
        }

        @Override
        public BlockStateAndModelProviderWrapper.FileModel genCubeBlockModel(String fileName, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
            return of(models().cube(fileName, down, up, north, south, east, west));
        }

        @Override
        public BlockStateAndModelProviderWrapper.FileModel getExistingModel(ResourceLocation location) {
            return of(models().getExistingFile(location));
        }

        @Override
        public void genSimpleBlockState(Block block, BlockStateAndModelProviderWrapper.FileModel model) {
            simpleBlock(block, getModelFile(model));
        }

        @Override
        public void genSimpleBlockItemModel(Block block, BlockStateAndModelProviderWrapper.FileModel model) {
            simpleBlockItem(block, getModelFile(model));
        }

        @Override
        public void genHorizontalBlockState(Block block, BlockStateAndModelProviderWrapper.FileModel model) {
            horizontalBlock(block, getModelFile(model));
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

    private record FileModelImpl(ModelFile modelFile) implements BlockStateAndModelProviderWrapper.FileModel {
        @Override
        public ResourceLocation getLocation() {
            return modelFile.getLocation();
        }
    }
}
