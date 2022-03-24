package dev.felnull.otyacraftengine.client.gui.screen.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.debug.MotionDebug;
import dev.felnull.otyacraftengine.client.gui.screen.OEBaseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MotionDebugScreen extends OEBaseScreen {
    public MotionDebugScreen(@Nullable Screen parent) {
        super(new TextComponent("Motion Debug"), parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int x, int y, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);
    }


    private MotionDebug getMotionDebug() {
        return MotionDebug.getInstance();
    }
}
