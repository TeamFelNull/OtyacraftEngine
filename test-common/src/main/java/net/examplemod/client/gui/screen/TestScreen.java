package net.examplemod.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.fnjl.util.FNURLUtil;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftengine.client.util.OETextureUtils;
import dev.felnull.otyacraftengine.debug.ProcessTimeMeasure;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public class TestScreen extends Screen {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation("test", "textures/gui/test.png");
    private final ProcessTimeMeasure processTimeMeasure = new ProcessTimeMeasure();
    private final UUID uuid = UUID.randomUUID();
    private final UUID uuid2 = UUID.randomUUID();
    private final InputStream nativeStream;
    private final InputStream nativeGifStream;

    public TestScreen() {
        super(Component.literal("Test Screen"));
        try {
            nativeStream = FNURLUtil.getStream(new URL("https://cdn.discordapp.com/attachments/358878159615164416/986538002102755369/bnkn_futomara.png"));
            nativeGifStream = FNURLUtil.getStream(new URL("https://cdn.discordapp.com/attachments/358878159615164416/986538237201907772/broken.gif"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);

        //  OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
      /*  OERenderUtils.setPreDraw(TEST_TEXTURE);

        OERenderUtils.floatBlit(poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 150, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 300, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 0, 150, 0, 0, 100, 100, 100, 100);
        OERenderUtils.floatBlit(poseStack, 0, 300, 0, 0, 100, 100, 100, 100);*/

        //  processTimeMeasure.process(() -> {
           /* OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 150, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 300, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 150, 0, 0, 100, 100, 100, 100);
            OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 300, 0, 0, 100, 100, 100, 100);*/

           /* OERenderUtils.setPreDraw(TEST_TEXTURE);

            OERenderUtils.floatBlit(poseStack, 0, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 150, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 300, 0, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 0, 150, 0, 0, 100, 100, 100, 100);
            OERenderUtils.floatBlit(poseStack, 0, 300, 0, 0, 100, 100, 100, 100);
        });
        //36519
        //
        processTimeMeasure.printResult(1000, 10000);*/

        OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 0, 0, 0, 100, 100, 100, 100);//0.014ms 13504.797ns

        OERenderUtils.drawFill(poseStack, 100, 0, 200, 100, 0xFF114514);

        OERenderUtils.drawTexture(OETextureUtils.getAndLoadNativeTextureAsync(uuid, nativeStream).of(), poseStack, 200, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.drawTexture(OETextureUtils.getAndLoadNativeTextureAsync(uuid2, nativeGifStream).of(), poseStack, 300, 0, 0, 0, 100, 100, 100, 100);// 0.009ms 9104.029ns, Count 546

        /*   processTimeMeasure.process(() -> {
        });
        processTimeMeasure.printResult(1000, 10000);*/
    }
}
