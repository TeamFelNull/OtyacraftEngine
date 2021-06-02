package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import red.felnull.otyacraftengine.client.gui.components.TestFixedButtonsList;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;
import red.felnull.otyacraftengine.util.IKSGColorUtil;
import red.felnull.otyacraftengine.util.IKSGDokataUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestScreen extends IkisugiScreen {
    public final List<String> testList = new ArrayList<>();
    private final UUID uuid = UUID.randomUUID();
    private InputStream stream;

    public TestScreen() {
        super(new TextComponent("test"));
        testList.add("ikisugi");
        for (IKSGDokataUtil.Dokata value : IKSGDokataUtil.Dokata.values()) {
            testList.add(value.getSerializedName().substring(0, 10));
        }
        testList.add("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲ!!");

    }


    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new TestFixedButtonsList(144, 109, 29, 100, 5, new TextComponent("Test List"), testList, TextComponent::new, n -> {
            System.out.println(n);
        }));

        try {
            stream = new FileInputStream("D:\\pcdatas\\pictures\\汚物\\microbroken.gif");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);

        IKSGRenderUtil.drawBindTextuer(IKSGTextureUtil.getNativeTexture(uuid, stream), poseStack, 0, 0, 0, 0, 100, 100, 100, 100);

        super.render(poseStack, i, j, f);
    }

    @Override
    public void onClose() {
        super.onClose();
        IKSGTextureUtil.freeNativeTexture(uuid);
    }
}
