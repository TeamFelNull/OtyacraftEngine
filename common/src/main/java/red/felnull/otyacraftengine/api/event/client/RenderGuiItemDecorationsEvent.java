package red.felnull.otyacraftengine.api.event.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import red.felnull.otyacraftengine.api.event.OEEvent;

public class RenderGuiItemDecorationsEvent extends OEEvent {
    private final ItemRenderer itemRenderer;
    private final Font font;
    private final ItemStack itemStack;
    private final int xPosition;
    private final int yPosition;
    private final String string;

    public RenderGuiItemDecorationsEvent(ItemRenderer itemRenderer, Font font, ItemStack itemStack, int xPosition, int yPosition, String string) {
        this.itemRenderer = itemRenderer;
        this.font = font;
        this.itemStack = itemStack;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.string = string;
    }

    public Font getFont() {
        return font;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public String getContString() {
        return string;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemRenderer getItemRenderer() {
        return itemRenderer;
    }
}
