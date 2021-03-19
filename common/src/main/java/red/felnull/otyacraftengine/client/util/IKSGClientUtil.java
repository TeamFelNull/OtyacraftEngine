package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.architectury.registry.BlockEntityRenderers;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import red.felnull.otyacraftengine.client.impl.OEClientExpectPlatform;
import red.felnull.otyacraftengine.client.renderer.CustomBlockEntityWithoutLevelRenderer;
import red.felnull.otyacraftengine.client.renderer.item.ICustomBEWLRenderer;

import java.util.ArrayList;

public class IKSGClientUtil {

    public static boolean isKeyInput(KeyMapping key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), OEClientExpectPlatform.getKey(key).getValue());
    }

    public static void setRenderLayer(Block block, RenderType type) {
        OEClientExpectPlatform.setRenderLayer(block, type);
    }

    public static <T extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<T> tileEntityType, BlockEntityRendererProvider<T> provider) {
        BlockEntityRenderers.registerRenderer(tileEntityType, provider);
    }

    public static void registerItemRenderer(Item item, ICustomBEWLRenderer renderer) {
        if (!CustomBlockEntityWithoutLevelRenderer.ITEMRENDERERS.containsKey(item)) {
            CustomBlockEntityWithoutLevelRenderer.ITEMRENDERERS.put(item, new ArrayList<>());
        }
        CustomBlockEntityWithoutLevelRenderer.ITEMRENDERERS.get(item).add(renderer);
    }

    public static void registerItemRenderer(Block block, ICustomBEWLRenderer renderer) {
        registerItemRenderer(block.asItem(), renderer);
    }
}
