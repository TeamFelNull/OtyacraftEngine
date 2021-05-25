package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import red.felnull.otyacraftengine.client.gui.components.TestFixedButtonsList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestScreen extends IkisugiScreen {
    public final List<String> testList = new ArrayList<>();

    public TestScreen() {
        super(new TextComponent("test"));
        testList.add("Ikisugi");
    }


    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new TestFixedButtonsList(10, 10, 29, 100, 5, new TextComponent("Test List"), testList, TextComponent::new, n -> {
            Random r = new Random();
            testList.add(r.nextInt()+"");
        }));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);

        super.render(poseStack, i, j, f);
    }

}
