package dev.felnull.otyacraftengine.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.mixin.client.ScreenAccessor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class GuiDebugRenderer {

    public static void onScreenRender(Screen screen, PoseStack posestack, int mouseX, int mouseY, float delta) {
        screen.renderComponentTooltip(posestack, createDebugTexts(screen, mouseX, mouseY), mouseX, mouseY);
    }

    private static List<Component> createDebugTexts(Screen screen, int x, int y) {
        List<Component> txs = new ArrayList<>();
        ScreenAccessor sa = (ScreenAccessor) screen;
        var rs = sa.getRenderables().stream().filter(n -> n instanceof AbstractWidget).map(n -> (AbstractWidget) n).filter(n -> x >= n.x && y >= n.y && x < (n.x + n.getWidth()) && y < (double) (n.y + n.getHeight())).toList();
        if (rs.isEmpty()) return txs;
        if (rs.size() == 1) {
            appendWidgetDebugText(txs, rs.get(0));
        } else {
            txs.add(new TextComponent("-------------"));
            for (AbstractWidget renderable : rs) {
                appendWidgetDebugText(txs, renderable);
                txs.add(new TextComponent("-------------"));
            }
        }
        return txs;
    }

    private static void appendWidgetDebugText(List<Component> txs, AbstractWidget widget) {
        txs.add(new TextComponent("Message: ").append(widget.getMessage()));
        txs.add(new TextComponent("Class: ").append(widget.getClass().toString()));
    }
}
