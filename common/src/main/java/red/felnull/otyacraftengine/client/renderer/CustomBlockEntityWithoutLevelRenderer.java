package red.felnull.otyacraftengine.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.client.renderer.item.ICustomBEWLRenderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomBlockEntityWithoutLevelRenderer {
    public static final Map<Item, List<ICustomBEWLRenderer>> ITEMRENDERERS = new HashMap<>();

    // "parent": "builtin/entity",
    public static void render(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        if (ITEMRENDERERS.containsKey(itemStack.getItem())) {
            ITEMRENDERERS.get(itemStack.getItem()).forEach(n -> {
                n.renderByItem(itemStack, transformType, poseStack, multiBufferSource, combinedLight, combinedOverlay);
            });
        }
    }
}
