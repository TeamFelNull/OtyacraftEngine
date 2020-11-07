package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

import java.util.List;

public class ScrollListButton extends IkisugiWidget {
    protected float nowSclooled;
    protected List<ResourceLocation> locations;
    protected int onesize;
    protected int locationCont;
    protected ScrollBarSlider scrollBar;
    private ScrollListButton.IPressable pressed;

    public ScrollListButton(int x, int y, int sizeX, int sizeY, int sizeOne, int count, ScrollBarSlider scrollBar, List<ResourceLocation> locations, ScrollListButton.IPressable pressed) {
        this(x, y, sizeX, sizeY, sizeOne, count, 0f, locations, pressed);
        this.scrollBar = scrollBar;
    }

    public ScrollListButton(int x, int y, int sizeX, int sizeY, int sizeOne, int count, float defaltscolled, List<ResourceLocation> locations, ScrollListButton.IPressable pressed) {
        super(x, y, sizeX, sizeY, new TranslationTextComponent("gui.narrate.scrolllistbutton"));
        this.pressed = pressed;
        this.locations = locations;
        this.onesize = sizeOne;
        this.locationCont = count;
        this.nowSclooled = defaltscolled;
    }

    public float getNowSclooled() {

        if (scrollBar != null)
            return scrollBar.getValue() / scrollBar.getMaxValue();

        return nowSclooled;
    }

    public void setSclooled(float nowsclooled) {
        this.nowSclooled = nowsclooled;
    }

    @Override
    public void renderBgByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        int allsize = onesize * getCont();
        int zure = 0;
        if (allsize > this.getYSize()) {
            int zuresize = allsize - getYSize();
            zure = (int) (zuresize * getNowSclooled());
        }
        for (int i = 0; i < getCont(); i++) {
            int y = this.getY() + i * onesize - zure;

            if (y < this.getY() - onesize || y > this.getY() + this.getYSize())
                continue;

            renderOneList(matrix, this.getX(), y, i, y < this.getY() ? getY() - y : 0, y > this.getY() + this.getYSize() - onesize ? -(getY() + getYSize() - (y + onesize)) : 0);
        }
    }

    public void renderOneList(MatrixStack matrix, int x, int y, int num, int upOver, int downOver) {
        IKSGRenderUtil.guiBindAndBlit(locations.get(num), matrix, x, y + upOver, 0, upOver, this.getXSize(), onesize - downOver, this.getXSize(), onesize);
    }

    @Override
    public void onClickByIKSG(double mouseX, double mouseY) {
        super.onClickByIKSG(mouseX, mouseY);
        int allsize = onesize * getCont();
        int zure = 0;
        if (allsize > this.getYSize()) {
            int zuresize = allsize - getYSize();
            zure = (int) (zuresize * getNowSclooled());
        }
        int y = (int) mouseY - this.getY() + zure;
        int sy = y / onesize;

        if (sy >= getCont())
            return;

        this.pressed.onPress(this, sy);
    }

    protected int getCont() {
        return locationCont;
    }

    @OnlyIn(Dist.CLIENT)
    public interface IPressable {
        void onPress(ScrollListButton button, int number);
    }
}
