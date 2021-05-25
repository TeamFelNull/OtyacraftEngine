package red.felnull.otyacraftengine.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public abstract class IkisugiSelectionList<E extends IkisugiSelectionList.Entry<E>> extends ObjectSelectionList<E> implements IIkisugibleWidget {
    private int selectWidth;
    private int scrollbarPosition;

    public IkisugiSelectionList(Minecraft minecraft, int x, int y, int width, int height, int inStartHeight, int inEndHeight, int selectHeight, int selectWidth) {
        super(minecraft, width, height, inStartHeight, inEndHeight, selectHeight);
        this.selectWidth = selectWidth;
        this.scrollbarPosition = this.width / 2 + 124;
        setLeftPos(x);
        setTopPos(y);
    }

    public IkisugiSelectionList(Minecraft minecraft, int x, int y, int width, int height, int selectHeight) {
        this(minecraft, x, y, width, height, 0, height, selectHeight, width - 6);
        this.x0 -= 3;
        this.scrollbarPosition = x + width - 6;
        setRenderTopAndBottom(false);
    }

    @Override
    public int getRowWidth() {
        return selectWidth;
    }

    public void setTopPos(int i) {
        this.y0 = i;
        this.y1 = i + this.height;
    }

    @Override
    protected int getScrollbarPosition() {
        return scrollbarPosition;
    }

    public abstract static class Entry<E extends IkisugiSelectionList.Entry<E>> extends ObjectSelectionList.Entry<E> {
        private final String name;

        public Entry(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public Component getNarration() {
            return new TextComponent(name);
        }
    }
}
