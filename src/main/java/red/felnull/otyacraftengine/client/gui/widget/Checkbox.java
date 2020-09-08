package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class Checkbox extends IkisugiWidget {
    private final ResourceLocation location;
    private final int texturStartX;
    private final int texturStartY;
    private final int texturSizeX;
    private final int texturSizeY;

    private boolean checked;

    public Checkbox(int x, int y) {
        this(x, y, 10, 10, 28, 0, 256, 256, IkisugiWidget.OE_WIDGET);
    }

    public Checkbox(int x, int y, int sizeX, int sizeY, int texStartX, int texStartY, int texSizeX, int texSizeY, ResourceLocation location) {
        super(x, y, sizeX, sizeY, new TranslationTextComponent("gui.narrate.checkbox"));
        this.location = location;
        this.texturStartX = texStartX;
        this.texturStartY = texStartY;
        this.texturSizeX = texSizeX;
        this.texturSizeY = texSizeY;
    }

    public boolean isCheck() {
        return checked;
    }

    public void setCheck(boolean checked) {
        this.checked = checked;
    }

    protected ResourceLocation getTexturLocation() {
        return location;
    }

    @Override
    public void renderBgByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        int tx = this.texturStartX;
        int ty = this.texturStartY;

        if (isHoveredByIKSG())
            tx += getXSize();

        if (checked)
            ty += getYSize();

        IKSGRenderUtil.guiBindAndBlit(getTexturLocation(), matrix, getX(), getY(), tx, ty, getXSize(), getYSize(), texturSizeX, texturSizeY);

    }

    @Override
    public void onClickByIKSG(double mouseX, double mouseY) {
        setCheck(!isCheck());
    }
}
