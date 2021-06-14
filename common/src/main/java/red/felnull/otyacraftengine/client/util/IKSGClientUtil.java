package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import red.felnull.otyacraftengine.client.impl.OEClientExpectPlatform;
import red.felnull.otyacraftengine.client.renderer.CustomBlockEntityWithoutLevelRenderer;
import red.felnull.otyacraftengine.client.renderer.item.ICustomBEWLRenderer;
import red.felnull.otyacraftengine.util.IKSGPlayerUtil;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class IKSGClientUtil {
    private static final Minecraft mc = Minecraft.getInstance();

    public static boolean isKeyInput(KeyMapping key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), OEClientExpectPlatform.getKey(key).getValue());
    }

    public static void setRenderLayer(Block block, RenderType type) {
        OEClientExpectPlatform.setRenderLayer(block, type);
    }

    public static <T extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<T> tileEntityType, BlockEntityRendererProvider<T> provider) {
        BlockEntityRendererRegistry.register(tileEntityType, provider);
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

    public static void addSubtitle(Component text, Vec3 location) {
        OEClientExpectPlatform.addSubtitle(null, text, 3000, () -> location);
    }

    public static void addSubtitle(Component text, Supplier<Vec3> location) {
        OEClientExpectPlatform.addSubtitle(null, text, 3000, location);
    }

    public static void addSubtitle(Component text, long time, Supplier<Vec3> location) {
        OEClientExpectPlatform.addSubtitle(null, text, time, location);
    }

    public static void addSubtitle(UUID id, Component text, Vec3 location) {
        OEClientExpectPlatform.addSubtitle(id, text, 3000, () -> location);
    }

    public static void addSubtitle(UUID id, Component text, Supplier<Vec3> location) {
        OEClientExpectPlatform.addSubtitle(id, text, 3000, location);
    }

    public static void addSubtitle(UUID id, Component text, long time, Supplier<Vec3> location) {
        OEClientExpectPlatform.addSubtitle(id, text, time, location);
    }

    public static String getPlayerNameByUUID(UUID uuid) {
        if (mc.player.connection.getPlayerInfo(uuid) != null) {
            return mc.player.connection.getPlayerInfo(uuid).getProfile().getName();
        }
        return IKSGPlayerUtil.getNameByUUIDNoSync(uuid);
    }
}
