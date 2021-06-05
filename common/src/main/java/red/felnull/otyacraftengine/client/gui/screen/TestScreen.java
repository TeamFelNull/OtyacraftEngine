package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;
import red.felnull.otyacraftengine.util.IKSGDokataUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestScreen extends IkisugiScreen {
    public final List<String> testList = new ArrayList<>();
    private static final UUID uuid = UUID.randomUUID();
    private InputStream stream;
    private String url = "https://cdn.discordapp.com/attachments/358878159615164416/850625064130969630/microbroken.gif";

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
        try {
            stream = new FileInputStream("D:\\pcdatas\\pictures\\汚物\\180half_f2.gif");
            IKSGTextureUtil.getURLTexture(url, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);

        IKSGRenderUtil.drawBindTextuer(IKSGTextureUtil.getURLTexture(url), poseStack, 10, 10, 0, 0, width / 2, height / 2, width / 2, height / 2);

        super.render(poseStack, i, j, f);
    }

    @Override
    public void onClose() {
        super.onClose();
        IKSGTextureUtil.freeNativeTexture(uuid);
        IKSGTextureUtil.freeURLTexture(url);
    }
}
