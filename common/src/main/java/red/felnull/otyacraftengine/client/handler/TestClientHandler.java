package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import red.felnull.otyacraftengine.api.event.client.InputEvent;
import red.felnull.otyacraftengine.client.gui.screen.TestScreen;
import red.felnull.otyacraftengine.client.keys.OEKeyMappings;

public class TestClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onKey(InputEvent.KeyInputEvent e) {
        if (e.getKey() == OEKeyMappings.TEST.getDefaultKey().getValue()) {
            mc.setScreen(new TestScreen());
        }
    }

   /* public static void onRender(TickEvent.RenderTickEvent e) {
        if (mc.level != null) {
            try {
                IKSGRenderUtil.drawBindTextuer(IKSGTextureUtil.getURLTexture("http://www.google.com/logos/doodles/2015/googles-new-logo-5078286822539264.3-hp2x.gif", true), new PoseStack(), 10, 10, 0, 0, mc.getWindow().getGuiScaledWidth() / 2, mc.getWindow().getGuiScaledHeight() / 2, mc.getWindow().getGuiScaledWidth() / 2, mc.getWindow().getGuiScaledHeight() / 2);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SizeOverException ex) {
                ex.printStackTrace();
            }
        }
    }*/
}
