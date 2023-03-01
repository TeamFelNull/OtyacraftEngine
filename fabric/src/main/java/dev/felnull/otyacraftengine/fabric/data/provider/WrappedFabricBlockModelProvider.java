package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.model.BlockStateAndModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import dev.felnull.otyacraftengine.fabric.data.model.FileModelImpl;
import dev.felnull.otyacraftengine.fabric.data.model.JsonModelInjector;
import dev.felnull.otyacraftengine.fabric.data.model.MutableFileModelImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

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

    private class BlockStateAndModelProviderAccessImpl implements BlockStateAndModelProviderAccess {
        private final BlockModelGenerators blockModelGenerators;

        private BlockStateAndModelProviderAccessImpl(BlockModelGenerators blockModelGenerators) {
            this.blockModelGenerators = blockModelGenerators;
        }

        @Override
        public void simpleCubeBlockStateModelAndItemModel(@NotNull Block block) {
            this.blockModelGenerators.createTrivialCube(block);
        }

        private FileModel of(ResourceLocation location) {
            return new FileModelImpl(location);
        }

        @Override
        public @NotNull FileModel cubeAllBlockModel(@NotNull String fileName, @NotNull ResourceLocation texture) {
            return of(ModelTemplates.CUBE_ALL.create(new ResourceLocation(crossDataGeneratorAccess.getMod().getModId(), "block/" + fileName), TextureMapping.cube(texture), blockModelGenerators.modelOutput));
        }

        @Override
        public @NotNull FileModel cubeBlockModel(@NotNull String fileName, @NotNull ResourceLocation down, @NotNull ResourceLocation up, @NotNull ResourceLocation north, @NotNull ResourceLocation south, @NotNull ResourceLocation east, @NotNull ResourceLocation west) {
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
        public @NotNull MutableFileModel parentedBlockModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
            var ji = new JsonModelInjector(this.blockModelGenerators.modelOutput);
            var loc = ModelLocationUtils.getModelLocation(block);
            ji.injectedModelOutput().accept(loc, new DelegatedModel(parentLocation));
            return new MutableFileModelImpl(loc, ji);
        }

        @Override
        public @NotNull FileModel existingModel(@NotNull ResourceLocation location) {
            return of(location);
        }

        @Override
        public @NotNull FileModel particleOnlyModel(@NotNull Block block, @NotNull ResourceLocation particleLocation) {
            TextureMapping textureMapping = TextureMapping.particle(particleLocation);
            return of(ModelTemplates.PARTICLE_ONLY.create(block, textureMapping, blockModelGenerators.modelOutput));
        }

        @Override
        public void simpleBlockState(@NotNull Block block, @NotNull FileModel model) {
            this.blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model.getLocation()));
        }

        @Override
        public void simpleBlockItemModel(@NotNull Block block, @NotNull FileModel model) {
            this.blockModelGenerators.delegateItemModel(block, model.getLocation());
        }

        @Override
        public void horizontalBlockState(@NotNull Block block, @NotNull FileModel model) {
            this.blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model.getLocation()))
                    .with(BlockModelGenerators.createHorizontalFacingDispatch()));
        }

        @Override
        public void parentedBlockItemModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
            this.blockModelGenerators.delegateItemModel(block, parentLocation);
        }

        @Override
        public void addBlockStateGenerator(@NotNull BlockStateGenerator blockStateGenerator) {
            this.blockModelGenerators.blockStateOutput.accept(blockStateGenerator);
        }
    }
}
