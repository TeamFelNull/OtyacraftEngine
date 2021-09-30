package dev.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.client.util.OETextureUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public class TestScreen extends Screen {
    private static final UUID id = UUID.randomUUID();

    private InputStream stream;

    public TestScreen() {
        super(new TextComponent("Test Screen"));
        try {//broken.gif
            stream = new URL("https://cdn.discordapp.com/attachments/887769442019323924/892670356358324264/pinki.gif").openStream();// new FileInputStream("D:\\newpcdatas\\pictures\\汚物\\gabaana_dadhi.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void render(PoseStack poseStack, int x, int y, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);

        //   OERenderUtil.drawText(poseStack, OETextureUtil.getPlayerTexture(MinecraftProfileTexture.Type.SKIN,UUID.fromString("5c751dd1-0882-4f31-ad61-c4ee928c4595")), x, y, 0xFFFFFF);
        ResourceLocation tex = OETextureUtil.getURLTexture("https://cdn.discordapp.com/attachments/887769442019323924/888494682492010519/gabaana_dadhi.png", true, MissingTextureAtlasSprite.getLocation()); //OETextureUtil.getPlayerSkinTexture(UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03"));
        OERenderUtil.drawTexture(tex == null ? MissingTextureAtlasSprite.getLocation() : tex, poseStack, 0, 0, 0, 0, width, height, width, height);
    }
}
