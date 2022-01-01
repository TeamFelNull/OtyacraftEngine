package dev.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import dev.felnull.otyacraftengine.util.FNJLNativeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

public class TestScreen extends Screen {
    private static final UUID id = UUID.randomUUID();

    //  private InputStream stream;

    public TestScreen() {
        super(new TextComponent("Test Screen"));
        try {
            //   stream = new URL("https://cdn.discordapp.com/attachments/887769442019323924/887770177381478530/test.gif").openStream();// new FileInputStream("D:\\newpcdatas\\pictures\\汚物\\gabaana_dadhi.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new Button(0, 0, 10, 10, new TextComponent("TEST"), n -> {
            FNJLNativeWrapper.FileChooserFlagWrapper flagWrapper = new FNJLNativeWrapper.FileChooserFlagWrapper().fileMustExist(true).readOnly(true);
            File[] files = FNJLNativeWrapper.openNativeFileChooser("TEST", Paths.get("."), flagWrapper, new FNJLNativeWrapper.FileChooserFilterWrapper("All file"));
            if (files != null) {
                for (File file : files) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }));
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);

        //   OERenderUtil.drawText(poseStack, OETextureUtil.getPlayerTexture(MinecraftProfileTexture.Type.SKIN,UUID.fromString("5c751dd1-0882-4f31-ad61-c4ee928c4595")), x, y, 0xFFFFFF);
  /*      var tex = OETextureUtil.getURLTextureAsyncLoad("https://cdn.discordapp.com/attachments/887769442019323924/888494682492010519/gabaana_dadhi.png", true);
        var sc = OETextureUtil.getTextureScale(null);
        float w = 100;
        float h = 100;
        OERenderUtil.drawTexture(tex == null ? MissingTextureAtlasSprite.getLocation() : tex, poseStack, 0, 0, 0, 0, w, h, w, h);
*/
        float siz = Minecraft.getInstance().font.width("TESTTEST");
        OERenderUtil.drawFixedWidthText(poseStack, "F.C.O.H", 10, 10, 0xFF000000, siz * ((float) (System.currentTimeMillis() % 5000) / 5000f));
        //ResourceLocation tex = OETextureUtil.getNativeTextureAsyncLoad(id, stream);//OETextureUtil.getURLTextureAsyncLoad("https://cdn.discordapp.com/attachments/887769442019323924/893111745927856128/broken.gif", true, MissingTextureAtlasSprite.getLocation()); //OETextureUtil.getPlayerSkinTexture(UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03"));
    }
}
