package dev.felnull.otyacraftenginetest.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.BlockStateAndModelProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.RegistriesDatapackProviderWrapper;
import dev.felnull.otyacraftenginetest.block.TestBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.registries.RegistriesDatapackGenerator;

public class OEBlockStateAndModelProviderWrapper extends BlockStateAndModelProviderWrapper {
    public OEBlockStateAndModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
    }

    @Override
    public void generateStatesAndModels(BlockStateAndModelProviderAccess providerAccess) {
        // providerAccess.genSimpleCubeBlockStateModelAndItemModel(TestBlocks.TEST_BLOCK.get());

        var testModel2 = providerAccess.genCubeAllBlockModel("test_block_2", modLoc("block/test_block_2"));
        providerAccess.genSimpleBlockState(TestBlocks.TEST_BLOCK.get(), testModel2);
        providerAccess.genSimpleBlockItemModel(TestBlocks.TEST_BLOCK.get(), testModel2);

        var horizontalModel = providerAccess.genCubeBlockModel("horizontal_block",
                modLoc("block/horizontal/down"),
                modLoc("block/horizontal/up"),
                modLoc("block/horizontal/north"),
                modLoc("block/horizontal/south"),
                modLoc("block/horizontal/east"),
                modLoc("block/horizontal/west"));

//        providerAccess.genSimpleBlockState(TestBlocks.TEST_HORIZONTAL_DIRECTIONAL_BLOCK.get(), horizontalModel);
        providerAccess.addBlockStateGenerator(MultiVariantGenerator.multiVariant(TestBlocks.TEST_HORIZONTAL_DIRECTIONAL_BLOCK.get(),
                Variant.variant().with(VariantProperties.MODEL, horizontalModel.getLocation())).with(createHorizontalFacingDispatch()));
        providerAccess.genSimpleBlockItemModel(TestBlocks.TEST_HORIZONTAL_DIRECTIONAL_BLOCK.get(), horizontalModel);


    }
}
