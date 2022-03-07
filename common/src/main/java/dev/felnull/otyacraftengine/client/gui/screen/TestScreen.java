package dev.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecifyLocation;
import dev.felnull.otyacraftengine.client.gui.components.IconButton;
import dev.felnull.otyacraftengine.client.util.OETextureUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TestScreen extends OEBaseScreen {
    private static final UUID id = UUID.randomUUID();

    public TestScreen(Screen parent) {
        super(new TextComponent("Test Screen"), parent);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new IconButton(0, 0, 20, 20, new TextComponent("TEST"), new TextureSpecifyLocation(OETextureUtil.getPlayerSkinTexture(UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03")), 0, 0, 10, 10, 10, 10), n -> {

        }));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int x, int y, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);

        //  OERenderUtil.drawPlayerFace(poseStack, UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03"), 0, 0, height);
    }
}
