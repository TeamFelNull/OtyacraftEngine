package dev.felnull.otyacraftengine.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.util.OEModelUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.item.TestItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TestItemRenderer implements BEWLItemRenderer {
    public static void init() {
        ItemRendererRegister.register(TestItem.TEST_ITEM.getOrNull(), new TestItemRenderer());
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, float f, int light, int overlay) {
        //   Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.APPLE), transformType, light, overlay, poseStack, multiBufferSource, 0);
        if (Minecraft.getInstance().player != null) {
            var pos = new BlockPos(Minecraft.getInstance().player.position()).below();
            // var model = OERenderUtil.getBlockModel(Minecraft.getInstance().level.getBlockState(pos));
            // var model = OERenderUtil.getModel(new ModelResourceLocation(new ResourceLocation("apple"), "inventory"));
            var model = OEModelUtil.getModel(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model"));
            //  int col = BiomeColors.getAverageGrassColor(Minecraft.getInstance().level, pos);
            OERenderUtil.renderModel(poseStack, multiBufferSource.getBuffer(Sheets.cutoutBlockSheet()), model, light, overlay);
        }
    }
}
