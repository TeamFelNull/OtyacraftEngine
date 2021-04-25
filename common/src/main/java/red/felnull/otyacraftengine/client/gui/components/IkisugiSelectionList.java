package red.felnull.otyacraftengine.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.ObjectSelectionList;
import org.jetbrains.annotations.Nullable;

public abstract class IkisugiSelectionList<E extends IkisugiSelectionList.Entry<E>> extends ObjectSelectionList<E> {
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

    @Override
    public void setSelected(@Nullable E entry) {
        super.setSelected(entry);
        if (entry != null) {
            NarratorChatListener.INSTANCE.sayNow(entry.getName());
        }
    }

    public abstract static class Entry<E extends IkisugiSelectionList.Entry<E>> extends ObjectSelectionList.Entry<E> {
        private final String name;

        public Entry(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
