package red.felnull.otyacraftengine.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.BeaconScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.config.ClientConfig;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;


public class RenderHandler {
    private static final ResourceLocation BEACON_GUI_TEXTURES = new ResourceLocation("textures/gui/container/beacon.png");
    private static final ResourceLocation BEACON_OVERLAY_TEXTURES = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/beacon_overlay.png");

    protected static int beaconCount = 0;
    protected static int beaconMaxCount = 0;

    @SubscribeEvent
    public void onGuiDraowEvent(GuiScreenEvent.DrawScreenEvent e) {
        if (e.getGui() instanceof BeaconScreen) {
            if (ClientConfig.BeaconOverlay.get() && ItemTags.BEACON_PAYMENT_ITEMS.getAllElements().stream().anyMatch(n -> n != Items.IRON_INGOT && n != Items.GOLD_INGOT && n != Items.DIAMOND && n != Items.EMERALD && n != Items.NETHERITE_INGOT)) {

                int ietmcont = Math.toIntExact(ItemTags.BEACON_PAYMENT_ITEMS.getAllElements().stream().filter(n -> n != Items.IRON_INGOT && n != Items.GOLD_INGOT && n != Items.DIAMOND && n != Items.EMERALD && n != Items.NETHERITE_INGOT).count());

                beaconMaxCount = ietmcont - 1;

                if (beaconCount > beaconMaxCount) {
                    beaconCount = 0;
                }

                ItemStack st = new ItemStack((IItemProvider) ItemTags.BEACON_PAYMENT_ITEMS.getAllElements().stream().filter(n -> n != Items.IRON_INGOT && n != Items.GOLD_INGOT && n != Items.DIAMOND && n != Items.EMERALD && n != Items.NETHERITE_INGOT).toArray()[beaconCount]);

                MatrixStack matx = e.getMatrixStack();
                int i = (e.getGui().width - ((BeaconScreen) e.getGui()).getXSize()) / 2;
                int j = (e.getGui().height - ((BeaconScreen) e.getGui()).getYSize()) / 2;
                IKSGRenderUtil.guiBindAndBlit(BEACON_OVERLAY_TEXTURES, matx, i - 3, j + 104, 0, 0, 6, 24, 24, 24);
                IKSGRenderUtil.guiBindAndBlit(BEACON_GUI_TEXTURES, matx, i + 14, j + 108, 36, 108, 5, 18, 256, 256);
                ItemRenderer ir = OtyacraftEngine.proxy.getMinecraft().getItemRenderer();
                ir.zLevel = 100.0F;
                ir.renderItemAndEffectIntoGUI(st, i + -1, j + 109);
                ir.zLevel = 0.0F;
            }

        }

    }

}
