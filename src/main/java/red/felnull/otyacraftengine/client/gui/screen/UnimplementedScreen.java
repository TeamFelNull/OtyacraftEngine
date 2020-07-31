package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import red.felnull.otyacraftengine.client.util.IKSGScreenUtil;

public class UnimplementedScreen extends IkisugiScreen {
    protected final ITextComponent name;
    private final Screen lastScreen;

    public UnimplementedScreen(ITextComponent name) {
        this(null, name);
    }

    public UnimplementedScreen(Screen lastScreen, ITextComponent name) {
        super(new TranslationTextComponent("unimplemented.title"));
        this.name = name;
        this.lastScreen = lastScreen;
    }

    @Override
    public void initByIKSG() {
        this.addWidgetByIKSG(new Button(this.getWidthByIKSG() / 2 - 100, this.getHeightByIKSG() / 6 + 168, 200, 20, new TranslationTextComponent("gui.back"),
                (widget) -> {
                    if (lastScreen != null) {
                        this.getMinecraft().displayGuiScreen(this.lastScreen);
                    } else {
                        this.onCloseByIKSG();
                    }
                }));
    }

    @Override
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderBackgroundByIKSG(matrix);
        this.drawCenterStringByIKSG(matrix, new TranslationTextComponent("unimplemented.unimscreen", name), this.getWidthByIKSG() / 2, this.getHeightByIKSG() / 2 - 9, 16777215);
        super.renderByIKSG(matrix, mouseX, mouseY, parTick);
    }

    @Override
    public boolean isPauseScreenByIKSG() {
        return lastScreen != null ? IKSGScreenUtil.isPauseScreen(lastScreen) : false;
    }
}
