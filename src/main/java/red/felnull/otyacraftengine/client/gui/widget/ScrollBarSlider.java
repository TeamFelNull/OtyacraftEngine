package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;
import red.felnull.otyacraftengine.util.IKSGMath;

public class ScrollBarSlider extends IkisugiWidget {
    private static ResourceLocation SCROLL = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/scroll_bar.png");
    protected int x;
    protected int y;
    protected int size;
    protected float MValue;
    protected float NValue;
    protected int selectedRange;
    protected boolean showRange;

    public ScrollBarSlider(int x, int y, int size, float maxValue, float defValue, int rang) {
        super(x, y, 9, size, new TranslationTextComponent("scrollbar"));
        this.x = x;
        this.y = y;
        this.size = size;
        this.MValue = maxValue;
        this.NValue = defValue;
        this.selectedRange = rang;
        this.showRange = false;
    }


    @Override
    public void renderBgByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {

        if (showRange) {
            if (selectedRange < 0) {
                int xsr = IKSGMath.positiveInt(selectedRange);
                int xsrk = xsr / 9;
                int ysr = size - 2;
                int ysrk = ysr / 20;
                int xsra = xsr - xsrk * 9;
                int ysra = ysr - ysrk * 20;

                for (int i = 0; i < xsrk; i++) {
                    IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + i * 9 + selectedRange, y, 18, 20, 9, 1, 256, 256);
                    IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + i * 9 + selectedRange, y + size - 1, 18, 20, 9, 1, 256, 256);
                }


                IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + xsrk * 9 + selectedRange, y, 18, 20, xsra, 1, 256, 256);
                IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + xsrk * 9 + selectedRange, y + size - 1, 18, 20, xsra, 1, 256, 256);
                for (int i = 0; i < ysrk; i++) {
                    IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + selectedRange, y + 1 + 20 * i, 27, 21, 1, 20, 256, 256);
                }
                IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + selectedRange, y + 1 + 20 * ysrk, 27, 21, 1, ysra, 256, 256);

            } else {
                int xsr = selectedRange;
                int xsrk = xsr / 9;
                int ysr = size - 2;
                int ysrk = ysr / 20;
                int xsra = xsr - xsrk * 9;
                int ysra = ysr - ysrk * 20;

                for (int i = 0; i < xsrk; i++) {
                    IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 9 + i * 9, y, 18, 20, 9, 1, 256, 256);
                    IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 9 + i * 9, y + size - 1, 18, 20, 9, 1, 256, 256);
                }


                IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 9 + xsrk * 9, y, 18, 20, xsra, 1, 256, 256);
                IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 9 + xsrk * 9, y + size - 1, 18, 20, xsra, 1, 256, 256);
                for (int i = 0; i < ysrk; i++) {
                    IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 9 + selectedRange - 1, y + 1 + 20 * i, 27, 21, 1, 20, 256, 256);
                }
                IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 9 + selectedRange - 1, y + 1 + 20 * ysrk, 27, 21, 1, ysra, 256, 256);
            }
        }

        int ra = isHoveredRange(mouseX, mouseY) ? 9 : 0;

        IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x, y, ra, 20, 9, 1, 256, 256);
        IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x, y + size - 1, ra, 41, 9, 1, 256, 256);

        int si = size - 2;
        int sik = si / 20;

        for (int i = 0; i < sik; i++) {
            IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x, y + 1 + 20 * i, ra, 21, 9, 20, 256, 256);
        }
        int sia = si - sik * 20;

        IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x, y + 1 + 20 * sik, ra, 21, 9, sia, 256, 256);

        int hsp = 0;

        if (this.isHoveredByIKSG())
            hsp += 7;
        if (isHoveredButton(mouseX, mouseY))
            hsp += 7;

        int nowPoint = 1 + (int) ((size - getSelectButtonSize()) * getValueProportion());

        IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 1, y + nowPoint, 0 + hsp, 0, 7, 2, 256, 256);
        IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 1, y + nowPoint + getSelectButtonSize() - 5, 0 + hsp, 17, 7, 3, 256, 256);

        int bsi = getSelectButtonSize() - 5;
        int bsik = bsi / 15;

        for (int i = 0; i < bsik; i++) {
            IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 1, y + 2 + nowPoint + 15 * i, 0 + hsp, 2, 7, 15, 256, 256);
        }
        int bsia = bsi - bsik * 15;
        IKSGRenderUtil.guiBindAndBlit(getTexture(), matrix, x + 1, y + 2 + nowPoint + 15 * bsik, 0 + hsp, 2, 7, bsia, 256, 256);

    }

    @Override
    protected void onDragByIKSG(double mouseX, double mouseY, double p_230983_5_, double p_230983_7_) {
        int crickdSize = (int) (mouseY - y);
        if (crickdSize >= getSelectButtonSize() / 2 && crickdSize <= size - getSelectButtonSize() / 2) {

            int cacs = crickdSize - getSelectButtonSize() / 2;

            float wari = cacs / (float) (size - getSelectButtonSize());
            setValue(MValue * wari);
        } else if (crickdSize <= getSelectButtonSize() / 2) {
            setValue(0);
        } else if (crickdSize >= size - getSelectButtonSize() / 2) {
            setValue(MValue);
        }
        super.onDragByIKSG(mouseX, mouseY, p_230983_5_, p_230983_7_);
    }

    @Override
    public void onClickByIKSG(double mouseX, double mouseY) {
        int crickdSize = (int) (mouseY - y);
        if (crickdSize >= getSelectButtonSize() / 2 && crickdSize <= size - getSelectButtonSize() / 2) {

            int cacs = crickdSize - getSelectButtonSize() / 2;

            float wari = cacs / (float) (size - getSelectButtonSize());
            setValue(MValue * wari);
        } else if (crickdSize <= getSelectButtonSize() / 2) {
            setValue(0);
        } else if (crickdSize >= size - getSelectButtonSize() / 2) {
            setValue(MValue);
        }
        super.onClickByIKSG(mouseX, mouseY);
    }

    protected ResourceLocation getTexture() {
        return SCROLL;
    }

    private boolean isHoveredRange(int mouseX, int mouseY) {
        boolean flag1 = false;
        boolean flag2 = false;
        if (selectedRange < 0) {
            flag1 = mouseX < x && mouseY >= y;
            flag2 = mouseX >= x + selectedRange && mouseY <= y + size;
        } else {
            flag1 = mouseX >= x && mouseY >= y;
            flag2 = mouseX <= x + 9 + selectedRange && mouseY <= y + size;
        }
        return this.isHoveredByIKSG() || (flag1 && flag2);
    }

    private boolean isHoveredButton(int mouseX, int mouseY) {
        boolean flag1 = mouseX >= x && mouseY >= y + ((float) size * (float) ((float) NValue / (float) MValue));
        boolean flag2 = mouseX <= x + 9 && mouseY <= y + getSelectButtonSize() + ((float) size * (float) ((float) NValue / (float) MValue));
        return this.isHoveredByIKSG() && flag1 && flag2;
    }

    public int getSelectButtonSize() {
        return 32;
    }

    protected float getValueProportion() {
        return NValue / MValue;
    }

    public float getValue() {
        return this.NValue;
    }

    public void setValue(float value) {
        this.NValue = value;
    }

    public float getMaxValue() {
        return this.MValue;
    }

    public void setMaxValue(float value) {
        this.MValue = value;
    }

    public int getSelectedRange() {
        return this.selectedRange;
    }

    public void setSelectedRange(int range) {
        this.selectedRange = range;
    }

    public boolean isShowRange() {
        return this.showRange;
    }

    public void setShowRange(boolean show) {
        this.showRange = show;
    }
}
