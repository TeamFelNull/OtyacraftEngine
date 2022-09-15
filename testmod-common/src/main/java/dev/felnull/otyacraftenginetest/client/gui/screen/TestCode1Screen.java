package dev.felnull.otyacraftenginetest.client.gui.screen;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftengine.client.util.OETextureUtils;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class TestCode1Screen extends Screen {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation(OtyacraftEngineTest.MODID, "textures/gui/test.png");
    private static final String testURL = "https://i.imgur.com/zcFCxfT.png";
    private static final String testURL2 = "https://i.imgur.com/yRuYCNI.gif";
    private static final UUID morimoriUUID = UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03");
    private static final String morimoriName = "MoriMori_0317_jp";

    public TestCode1Screen() {
        super(Component.literal("Test code1"));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);

        float sizeW = (float) width / 6f;
        float sizeH = (float) height / 3f;

        OERenderUtils.drawTexture(TEST_TEXTURE, poseStack, 0, 0, 0, 0, sizeW, sizeH, sizeW, sizeH);
        OERenderUtils.drawTextureAlpha(OETextureUtils.getErrorIcon(), poseStack, 0, sizeH, 0, 0, sizeW, sizeH, sizeW, sizeH);
        OERenderUtils.drawTextureAlpha(OETextureUtils.getLoadingIcon(), poseStack, 0, sizeH * 2, 0, 0, sizeW, sizeH, sizeW, sizeH);


        OERenderUtils.drawTexture(OETextureUtils.getPlayerTexture(MinecraftProfileTexture.Type.SKIN, morimoriUUID), poseStack, sizeW, 0, 0, 0, sizeW, sizeH, sizeW, sizeH);
        OERenderUtils.drawTexture(OETextureUtils.getPlayerTexture(MinecraftProfileTexture.Type.SKIN, morimoriName), poseStack, sizeW, sizeH, 0, 0, sizeW, sizeH, sizeW, sizeH);
        OERenderUtils.drawTexture(OETextureUtils.getAndLoadURLTextureAsync(testURL, false).of(), poseStack, sizeW, sizeH * 2, 0, 0, sizeW, sizeH, sizeW, sizeH);

        OERenderUtils.drawTexture(OETextureUtils.getAndLoadURLTextureAsync(testURL2, true).of(), poseStack, sizeW * 2, 0, 0, 0, sizeW, sizeH, sizeW, sizeH);

        super.render(poseStack, i, j, f);
    }
}
