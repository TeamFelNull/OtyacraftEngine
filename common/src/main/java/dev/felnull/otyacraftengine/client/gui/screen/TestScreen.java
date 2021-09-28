package dev.felnull.otyacraftengine.client.gui.screen;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.client.util.OETextureUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.network.chat.TextComponent;

import java.util.UUID;

public class TestScreen extends Screen {
    public TestScreen() {
        super(new TextComponent("Test Screen"));
    }


    @Override
    public void render(PoseStack poseStack, int x, int y, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);

        //   OERenderUtil.drawText(poseStack, OETextureUtil.getPlayerTexture(MinecraftProfileTexture.Type.SKIN,UUID.fromString("5c751dd1-0882-4f31-ad61-c4ee928c4595")), x, y, 0xFFFFFF);
        var tex = OETextureUtil.getPlayerTexture(MinecraftProfileTexture.Type.SKIN, UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03"));
        OERenderUtil.drawTexture(tex == null ? MissingTextureAtlasSprite.getLocation() : tex, poseStack, x, y, 0, 0, 100, 100, 100, 100);
    }
}
