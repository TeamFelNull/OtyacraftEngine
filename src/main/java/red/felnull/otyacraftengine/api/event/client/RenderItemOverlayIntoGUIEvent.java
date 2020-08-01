package red.felnull.otyacraftengine.api.event.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class RenderItemOverlayIntoGUIEvent extends Event {
    private FontRenderer fontRenderer;
    private ItemStack stack;
    private int x;
    private int y;
    private String text;

    public RenderItemOverlayIntoGUIEvent(FontRenderer fr, ItemStack stack, int x, int y, String text) {
        this.fontRenderer = fr;
        this.stack = stack;
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return text;
    }

    public float getZLevel() {
        return Minecraft.getInstance().getItemRenderer().zLevel;
    }
}

