package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class WrappedFabricBlockModelProvider extends FabricModelProvider {
    private final CrossDataGeneratorAccess crossDataGeneratorAccess;
    private final BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper;

    public WrappedFabricBlockModelProvider(FabricDataOutput output, CrossDataGeneratorAccess crossDataGeneratorAccess, BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper) {
        super(output);
        this.crossDataGeneratorAccess = crossDataGeneratorAccess;
        this.blockStateAndModelProviderWrapper = blockStateAndModelProviderWrapper;
    }

    @Override
    public String getName() {
        return "Model Definitions (Block)";
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        this.blockStateAndModelProviderWrapper.generateStatesAndModels(new BlockStateAndModelProviderAccessImpl(blockStateModelGenerator));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {

    }

    private class BlockStateAndModelProviderAccessImpl implements BlockStateAndModelProviderWrapper.BlockStateAndModelProviderAccess {
        private final BlockModelGenerators blockModelGenerators;

        private BlockStateAndModelProviderAccessImpl(BlockModelGenerators blockModelGenerators) {
            this.blockModelGenerators = blockModelGenerators;
        }

        @Override
        public void genSimpleCubeBlockStateModelAndItemModel(Block block) {
            this.blockModelGenerators.createTrivialCube(block);
        }

        private BlockStateAndModelProviderWrapper.FileModel of(ResourceLocation location) {
            return new FileModelImpl(location);
        }

        @Override
        public BlockStateAndModelProviderWrapper.FileModel genCubeAllBlockModel(String fileName, ResourceLocation texture) {
            return of(ModelTemplates.CUBE_ALL.create(new ResourceLocation(crossDataGeneratorAccess.getMod().getModId(), "block/" + fileName), TextureMapping.cube(texture), blockModelGenerators.modelOutput));
        }

        @Override
        public BlockStateAndModelProviderWrapper.FileModel genCubeBlockModel(String fileName, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west) {
            TextureMapping mapping = new TextureMapping();
            mapping.put(TextureSlot.DOWN, down);
            mapping.put(TextureSlot.UP, up);
            mapping.put(TextureSlot.NORTH, north);
            mapping.put(TextureSlot.SOUTH, south);
            mapping.put(TextureSlot.EAST, east);
            mapping.put(TextureSlot.WEST, west);
            mapping.put(TextureSlot.PARTICLE, north);

            return of(ModelTemplates.CUBE.create(new ResourceLocation(crossDataGeneratorAccess.getMod().getModId(), "block/" + fileName), mapping, blockModelGenerators.modelOutput));
        }

        @Override
        public BlockStateAndModelProviderWrapper.FileModel getExistingModel(ResourceLocation location) {
            return of(location);
        }

        @Override
        public void genSimpleBlockState(Block block, BlockStateAndModelProviderWrapper.FileModel model) {
            this.blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model.getLocation()));
        }

        @Override
        public void genSimpleBlockItemModel(Block block, BlockStateAndModelProviderWrapper.FileModel model) {
            this.blockModelGenerators.delegateItemModel(block, model.getLocation());
        }

        @Override
        public void genHorizontalBlockState(Block block, BlockStateAndModelProviderWrapper.FileModel model) {
            this.blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model.getLocation()))
                    .with(BlockModelGenerators.createHorizontalFacingDispatch()));
        }

        @Override
        public void genBuiltinEntityBlockItemModel(Block block) {
            WrappedFabricItemModelProvider.BUILTIN_ENTITY.create(ModelLocationUtils.getModelLocation(block.asItem()), new TextureMapping(), this.blockModelGenerators.modelOutput);
        }

        @Override
        public void genParentedBlockItemModel(Block block, ResourceLocation parentLocation) {
            this.blockModelGenerators.delegateItemModel(block, parentLocation);
        }

        @Override
        public void addBlockStateGenerator(BlockStateGenerator blockStateGenerator) {
            this.blockModelGenerators.blockStateOutput.accept(blockStateGenerator);
        }
    }

    private record FileModelImpl(ResourceLocation location) implements BlockStateAndModelProviderWrapper.FileModel {
        @Override
        public ResourceLocation getLocation() {
            return location;
        }
    }
}
