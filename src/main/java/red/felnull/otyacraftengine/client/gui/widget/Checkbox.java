package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

public class Checkbox extends IkisugiWidget {
    private final ResourceLocation location;
    private final int texturStartX;
    private final int texturStartY;
    private final int texturSizeX;
    private final int texturSizeY;
    private final int dff;

    private boolean checked;

    public Checkbox(int x, int y) {
        this(x, y, 10, 10, 0, 0, 256, 256, 10, new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/checkbox.png"));
    }

    public Checkbox(int x, int y, int sizeX, int sizeY, int texStartX, int texStartY, int texSizeX, int texSizeY, int dff, ResourceLocation location) {
        super(x, y, sizeX, sizeY, new TranslationTextComponent("gui.narrate.checkbox"));
        this.location = location;
        this.texturStartX = texStartX;
        this.texturStartY = texStartY;
        this.texturSizeX = texSizeX;
        this.texturSizeY = texSizeY;
        this.dff = dff;
    }

    public void setCheck(boolean checked) {
        this.checked = checked;
    }

    public boolean isCheck() {
        return checked;
    }

    protected ResourceLocation getTexturLocation() {
        return location;
    }

    @Override
    public void renderBgByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        if (checked) {
            IKSGRenderUtil.guiBindAndBlit(getTexturLocation(), matrix, getX(), getY(), texturStartX, texturStartY + 10, texturStartX + getXSize(), texturStartY + getYSize(), texturSizeX, texturSizeY);
        } else {
            IKSGRenderUtil.guiBindAndBlit(getTexturLocation(), matrix, getX(), getY(), texturStartX, texturStartY, texturStartX + getXSize(), texturStartY + getYSize(), texturSizeX, texturSizeY);
        }
    }

    @Override
    public void onClickByIKSG(double mouseX, double mouseY) {
        setCheck(!isCheck());
    }
}
