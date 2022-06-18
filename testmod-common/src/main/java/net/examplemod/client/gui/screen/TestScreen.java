package net.examplemod.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftengine.debug.ProcessTimeMeasure;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class TestScreen extends Screen {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation("test", "textures/gui/test.png");
    private final ProcessTimeMeasure processTimeMeasure = new ProcessTimeMeasure();
    private final UUID uuid = UUID.randomUUID();
    private final UUID uuid2 = UUID.randomUUID();
    private final String testURL = "https://cdn.discordapp.com/attachments/887769442019323924/893123973678768148/test.gif";
    private final String testURL2 = "https://cdn.discordapp.com/attachments/358878159615164416/987758500182650910/systemerror_94180.png";
    // private final InputStream nativeStream;
    // private final InputStream nativeGifStream;

    public TestScreen() {
        super(Component.literal("Test Screen"));
        /*try {
            nativeStream = FNURLUtil.getStream(new URL("https://cdn.discordapp.com/attachments/358878159615164416/986538002102755369/bnkn_futomara.png"));
            nativeGifStream = FNURLUtil.getStream(new URL("https://cdn.discordapp.com/attachments/358878159615164416/986538237201907772/broken.gif"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
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

        //  OERenderUtils.drawTexture(OETextureUtils.getAndLoadURLTextureAsync(testURL, true).of(), poseStack, 200, 0, 0, 0, 100, 100, 100, 100);
        //   OERenderUtils.drawTexture(OETextureUtils.getAndLoadURLTextureAsync(testURL2, true).of(), poseStack, 300, 0, 0, 0, 100, 100, 100, 100);
/*
        var r1 = OETextureUtils.getAndLoadNativeTextureAsync(uuid, nativeStream);
        var r2 = OETextureUtils.getAndLoadNativeTextureAsync(uuid2, nativeGifStream);

        OERenderUtils.drawTexture(r1.of(), poseStack, 200, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.drawTexture(r2.of(), poseStack, 300, 0, 0, 0, 100, 100, 100, 100);// 0.009ms 9104.029ns, Count 546

        if (r1.isLoading())
            drawString(poseStack, font, r1.getProgress().getStateName() + " " + FNStringUtil.getPercentage(r1.getProgress().getParent()), 200, 110, 0xFFFFFFFF);

        if (r2.isLoading())
            drawString(poseStack, font, r2.getProgress().getStateName() + " " + FNStringUtil.getPercentage(r2.getProgress().getParent()), 300, 110, 0xFFFFFFFF);
*/
        /*   processTimeMeasure.process(() -> {
        });
        processTimeMeasure.printResult(1000, 10000);*/

       /* var r1 = OETextureUtils.getAndLoadURLTextureAsync(testURL, false);
        var r2 = OETextureUtils.getAndLoadURLTextureAsync(testURL2, false);


        OERenderUtils.drawTextureAlpha(r1.of(OETextureUtils.getErrorIcon(), OETextureUtils.getErrorIcon()), poseStack, 200, 0, 0, 0, 100, 100, 100, 100);
        OERenderUtils.drawTextureAlpha(r2.of(OETextureUtils.getErrorIcon(), OETextureUtils.getErrorIcon()), poseStack, 300, 0, 0, 0, 100, 100, 100, 100);// 0.009ms 9104.029ns, Count 546

        if (r1.isLoading())
            drawString(poseStack, font, r1.getProgress().getStateName() + " " + FNStringUtil.getPercentage(r1.getProgress().getParent()), 200, 110, 0xFFFFFFFF);

        if (r1.isError())
            drawString(poseStack, font, r1.getException().toString(), 200, 110, 0xFFFFFFFF);

        if (r2.isLoading())
            drawString(poseStack, font, r2.getProgress().getStateName() + " " + FNStringUtil.getPercentage(r2.getProgress().getParent()), 300, 110, 0xFFFFFFFF);

        if (r2.isError())
            drawString(poseStack, font, r2.getException().toString(), 300, 110, 0xFFFFFFFF);*/
    }
}
