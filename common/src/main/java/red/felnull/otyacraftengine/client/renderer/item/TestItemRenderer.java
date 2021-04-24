package red.felnull.otyacraftengine.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.item.TestItem;

public class TestItemRenderer implements ICustomBEWLRenderer {
    public static void init() {
        IKSGClientUtil.registerItemRenderer(TestItem.TEST_ITEM, new TestItemRenderer());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
      //  BakedModel model = IKSGRenderUtil.getBlockBakedModel(new ModelResourceLocation("stone", ""));
        // VertexConsumer ivb = multiBufferSource.getBuffer(RenderType.cutout());
     //   VertexConsumer ivb = multiBufferSource.getBuffer(Sheets.solidBlockSheet());
     //   IKSGRenderUtil.renderBakedModel(poseStack, ivb, null, model, combinedLight, combinedOverlay);
    }
}
