package red.felnull.otyacraftengine.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import red.felnull.otyacraftengine.client.util.IKSGClientUtil;
import red.felnull.otyacraftengine.item.TestItem;

public class TestItemRenderer implements ICustomBEWLRenderer {
    public static void init() {
        IKSGClientUtil.registerItemRenderer(TestItem.TEST_ITEM, new TestItemRenderer());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        System.out.println("test");
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.APPLE), transformType, combinedLight, combinedOverlay, poseStack, multiBufferSource, 0);
    }
}
