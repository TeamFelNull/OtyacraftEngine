package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import red.felnull.otyacraftengine.client.gui.components.TestFixedButtonsList;
import red.felnull.otyacraftengine.util.IKSGDokataUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestScreen extends IkisugiScreen {
    public final List<String> testList = new ArrayList<>();
    private static final UUID uuid = UUID.randomUUID();
    private InputStream stream;
    private String url = "http://www.google.com/logos/doodles/2015/googles-new-logo-5078286822539264.3-hp2x.gif";

    public TestScreen() {
        super(new TextComponent("test"));
        testList.add("ikisugi");
    //    for (IKSGDokataUtil.Dokata value : IKSGDokataUtil.Dokata.values()) {
   //         testList.add(value.getSerializedName().substring(0, 10));
   //     }
      //  testList.add("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲ!!");

    }


    @Override
    protected void init() {
        super.init();
        try {
            //   stream = new FileInputStream("D:\\pcdatas\\pictures\\汚物\\180half_f2.gif");
            //     IKSGTextureUtil.getURLTexture(url, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.addRenderableWidget(new TestFixedButtonsList(10, 10, 30, 100, 10, new TextComponent("test"), testList, TextComponent::new, n -> {
            testList.add("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲ!!");
        }));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);

        // IKSGRenderUtil.drawBindTextuer(IKSGTextureUtil.getURLTexture(url), poseStack, 10, 10, 0, 0, width / 2, height / 2, width / 2, height / 2);
        // IKSGRenderUtil.drawPlayerFase(poseStack, "MoriMori_0317_jp", 0, 0, Math.max(i, j));
        super.render(poseStack, i, j, f);
    }

    @Override
    public void onClose() {
        super.onClose();
        //   IKSGTextureUtil.freeNativeTexture(uuid);
        //    IKSGTextureUtil.freeURLTexture(url);
    }
}
