package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.model.FileModel;
import net.minecraft.core.Direction;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class BlockStateAndModelProviderWrapper extends DataProviderWrapper<DataProvider> {
    public static final ResourceLocation OE_BASE_BLOCK_ENTITY_MODEL = new ResourceLocation(OtyacraftEngine.MODID, "base/block_entity");
    public static final ResourceLocation OE_BASE_BLOCK_ENTITY_APPEARANCE_MODEL = new ResourceLocation(OtyacraftEngine.MODID, "base/block_entity_appearance");
    private final DataProvider blockStateAndModelProvider;

    public BlockStateAndModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.blockStateAndModelProvider = crossDataGeneratorAccess.createBlockStateAndModelProvider(packOutput, this);
    }

    @Override
    public DataProvider getProvider() {
        return blockStateAndModelProvider;
    }

    public abstract void generateStatesAndModels(BlockStateAndModelProviderAccess providerAccess);

    /**
     * BlockModelGenerators$createHorizontalFacingDispatchの移植
     *
     * @return PropertyDispatch
     */
    public PropertyDispatch createHorizontalFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, Variant.variant());
    }

    /**
     * BlockModelGenerators$createBooleanModelDispatchの移植
     *
     * @return PropertyDispatch
     */
    public PropertyDispatch createBooleanModelDispatch(BooleanProperty booleanProperty, ResourceLocation trueLoc, ResourceLocation falseLoc) {
        return PropertyDispatch.property(booleanProperty)
                .select(true, Variant.variant().with(VariantProperties.MODEL, trueLoc))
                .select(false, Variant.variant().with(VariantProperties.MODEL, falseLoc));
    }

    public static interface BlockStateAndModelProviderAccess {
        void genSimpleCubeBlockStateModelAndItemModel(Block block);

        FileModel genCubeAllBlockModel(String fileName, ResourceLocation texture);

        FileModel genCubeBlockModel(String fileName, ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south, ResourceLocation east, ResourceLocation west);

        FileModel getExistingModel(ResourceLocation location);

        FileModel genParticleOnlyModel(Block block, ResourceLocation particleLocation);

        void genSimpleBlockState(Block block, FileModel model);

        /**
         * Fabricの場合、アイテムモデル未登録時にステートを登録していれば勝手に自動登録される
         *
         * @param block ブロック
         * @param model モデル
         */
        void genSimpleBlockItemModel(Block block, FileModel model);

        void genHorizontalBlockState(Block block, FileModel model);

        void genBuiltinEntityBlockItemModel(Block block);

        void genParentedBlockItemModel(Block block, ResourceLocation parentLocation);

        void addBlockStateGenerator(BlockStateGenerator blockStateGenerator);
    }
}
