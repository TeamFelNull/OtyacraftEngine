package dev.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecifyLocation;
import dev.felnull.otyacraftengine.client.gui.components.BetterEditBox;
import dev.felnull.otyacraftengine.client.gui.components.IconButton;
import dev.felnull.otyacraftengine.client.gui.components.SwitchButton;
import dev.felnull.otyacraftengine.client.util.OETextureUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.apache.commons.io.filefilter.FileFilterUtils;
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
        this.addRenderableWidget(new IconButton(10, 10, 20, 20, new TextComponent("TEST Icon Button"), new TextureSpecifyLocation(OETextureUtil.getPlayerSkinTexture(UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03")), 0, 0, 10, 10, 10, 10), n -> {

        }));

        this.addRenderableWidget(new SwitchButton(10, 40, new TextComponent("TEST Switch Button"), n -> {
            System.out.println(n.isEnable());
        }, true));

        var e = this.addRenderableWidget(new BetterEditBox(10, 60, 100, 20, new TextComponent("TEST Better Edit Box")));
        e.setFilter(n -> {
            try {
                Integer.parseInt(n);
                return true;
            } catch (Exception ex) {
                return false;
            }
        });
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int x, int y, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);

        //  OERenderUtil.drawPlayerFace(poseStack, UUID.fromString("0f286fc2-0c86-42d5-8518-c306cad74f03"), 0, 0, height);
    }
}
