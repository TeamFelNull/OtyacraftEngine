package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import red.felnull.otyacraftengine.client.gui.components.IkisugiSelectionList;
import red.felnull.otyacraftengine.util.IKSGDokataUtil;

public class TestScreen extends IkisugiScreen {
    private TestList list;

    public TestScreen() {
        super(new TextComponent("test"));
    }


    @Override
    protected void init() {
        super.init();
        this.list = addWidget(new TestList(getMinecraft(), width / 4, height / 4, width / 2, height / 2, 18));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        this.list.render(poseStack, i, j, f);
        super.render(poseStack, i, j, f);
    }

    public class TestList extends IkisugiSelectionList<TestList.Entry> {

        public TestList(Minecraft minecraft, int x, int y, int width, int height, int selectHeight) {
            super(minecraft, x, y, width, height, selectHeight);

            for (IKSGDokataUtil.Dokata value : IKSGDokataUtil.Dokata.values()) {
                addEntry(new Entry(value.getSerializedName()));
            }
        }

        public class Entry extends IkisugiSelectionList.Entry<TestScreen.TestList.Entry> {


            public Entry(String name) {
                super(name);
            }

            @Override
            public void render(PoseStack poseStack, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
                TestScreen.this.font.drawShadow(poseStack, getName(), (float) (TestScreen.TestList.this.width / 2 - TestScreen.this.font.width(getName()) / 2), (float) (j + 1), 16777215, true);
            }

            @Override
            public boolean mouseClicked(double d, double e, int i) {
                if (i == 0) {
                    TestScreen.TestList.this.setSelected(this);
                    return true;
                } else {
                    return false;
                }
            }


        }
    }
}
