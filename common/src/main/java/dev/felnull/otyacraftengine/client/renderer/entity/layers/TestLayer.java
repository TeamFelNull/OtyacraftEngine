package dev.felnull.otyacraftengine.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TestLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public TestLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        poseStack.pushPose();
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.APPLE), ItemTransforms.TransformType.FIXED, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, 0);
        poseStack.popPose();
    }
}
