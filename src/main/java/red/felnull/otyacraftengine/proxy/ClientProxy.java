package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import red.felnull.otyacraftengine.asm.DeobfNames;
import red.felnull.otyacraftengine.client.data.ClientDataSender;
import red.felnull.otyacraftengine.client.handler.ClientHandler;
import red.felnull.otyacraftengine.client.handler.RenderHandler;
import red.felnull.otyacraftengine.client.keys.OEKeyBindings;
import red.felnull.otyacraftengine.client.renderer.entity.layers.CustomElytraLayer;
import red.felnull.otyacraftengine.data.ReceiveTextureLoder;
import red.felnull.otyacraftengine.util.IKSGReflectionUtil;

import java.util.List;

public class ClientProxy extends CommonProxy {
    public static void clientInit() {
        OEKeyBindings.init();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, GuiScreenEvent.DrawScreenEvent.class, new RenderHandler()::onGuiDraowEvent);

        Minecraft.getInstance().getRenderManager().renderers.forEach((m, n) -> {
            if (n instanceof LivingRenderer) {
                List<LayerRenderer<LivingEntity, EntityModel<LivingEntity>>> layerRenderers = IKSGReflectionUtil.getPrivateField(LivingRenderer.class, ((LivingRenderer) n), DeobfNames.layerRenderers);
                if (layerRenderers.stream().anyMatch(l -> l instanceof ElytraLayer)) {
                    ((LivingRenderer) n).addLayer(new CustomElytraLayer((LivingRenderer) n));
                }
            }
        });
    }

    @Override
    public void preInit() {
        super.preInit();
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, ItemTooltipEvent.class, new ClientHandler()::onToolTip);
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void posInit() {
        super.posInit();
        ReceiveTextureLoder.instance().deleteUnnecessaryCash();
        ClientDataSender.srlogsGziping();
    }

    @Override
    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }
}
