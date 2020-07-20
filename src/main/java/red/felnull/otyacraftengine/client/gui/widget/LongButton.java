package red.felnull.otyacraftengine.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class LongButton extends Button {
    public LongButton(int x, int y, int sizeX, int sizeY, ITextComponent text, IPressable pressable) {
        super(x, y, sizeX, sizeY, text, pressable);
    }

    public LongButton(int x, int y, int sizeX, int sizeY, ITextComponent text, IPressable pressable, Button.ITooltip tip) {
        super(x, y, sizeX, sizeY, text, pressable, tip);
    }

    @Override
    public void func_230431_b_(MatrixStack matrix, int mousX, int mousY, float parTick) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(field_230687_i_);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
        int i = this.func_230989_a_(this.func_230449_g_());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();


        this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_, 0, 46 + i * 20, 2, 2);
        this.func_238474_b_(matrix, this.field_230690_l_ + field_230688_j_ - 2, this.field_230691_m_, 198, 46 + i * 20, 2, 2);
        this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_ + field_230689_k_ - 3, 0, 46 + i * 20 + 17, 2, 3);
        this.func_238474_b_(matrix, this.field_230690_l_ + field_230688_j_ - 2, this.field_230691_m_ + field_230689_k_ - 3, 198, 46 + i * 20 + 17, 2, 3);

        int xtsize = 200 - 4;
        int xw = (field_230688_j_ - 4) / xtsize;
        for (int c = 0; c < xw; c++) {
            this.func_238474_b_(matrix, this.field_230690_l_ + c * xtsize + 2, this.field_230691_m_, 2, 46 + i * 20, xtsize, 2);
            this.func_238474_b_(matrix, this.field_230690_l_ + c * xtsize + 2, this.field_230691_m_ + field_230689_k_ - 3, 2, 46 + i * 20 + 17, xtsize, 3);

            int nytsize = 20 - 5;
            int nyw = (field_230689_k_ - 5) / nytsize;
            for (int c2 = 0; c2 < nyw; c2++) {
                this.func_238474_b_(matrix, this.field_230690_l_ + c * xtsize + 2, this.field_230691_m_ + 2 + c2 * nytsize, 2, 46 + i * 20 + 2, xtsize, nytsize);
            }
            int nywr = (field_230689_k_ - 5) - nyw * nytsize;
            this.func_238474_b_(matrix, this.field_230690_l_ + c * xtsize + 2, this.field_230691_m_ + 2 + nyw * nytsize, 2, 46 + i * 20 + 2, xtsize, nywr);
        }
        int xwr = (field_230688_j_ - 4) - xw * xtsize;
        this.func_238474_b_(matrix, this.field_230690_l_ + xw * xtsize + 2, this.field_230691_m_, 2, 46 + i * 20, xwr, 2);
        this.func_238474_b_(matrix, this.field_230690_l_ + xw * xtsize + 2, this.field_230691_m_ + field_230689_k_ - 3, 2, 46 + i * 20 + 17, xwr, 3);

        int nytsize = 20 - 5;
        int nyw = (field_230689_k_ - 5) / nytsize;
        for (int c2 = 0; c2 < nyw; c2++) {
            this.func_238474_b_(matrix, this.field_230690_l_ + xw * xtsize + 2, this.field_230691_m_ + 2 + c2 * nytsize, 2, 46 + i * 20 + 2, xwr, nytsize);
        }
        int nywr = (field_230689_k_ - 5) - nyw * nytsize;
        this.func_238474_b_(matrix, this.field_230690_l_ + xw * xtsize + 2, this.field_230691_m_ + 2 + nyw * nytsize, 2, 46 + i * 20 + 2, xwr, nywr);


        int ytsize = 20 - 5;
        int yw = (field_230689_k_ - 5) / ytsize;
        for (int c = 0; c < yw; c++) {
            this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_ + c * ytsize + 2, 0, 46 + i * 20 + 2, 2, ytsize);
            this.func_238474_b_(matrix, this.field_230690_l_ + field_230688_j_ - 2, this.field_230691_m_ + c * ytsize + 2, 198, 46 + i * 20 + 2, 2, ytsize);
        }
        int ywr = (field_230689_k_ - 5) - yw * ytsize;
        this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_ + yw * ytsize + 2, 0, 46 + i * 20 + 2, 2, ywr);
        this.func_238474_b_(matrix, this.field_230690_l_ + field_230688_j_ - 2, this.field_230691_m_ + yw * ytsize + 2, 198, 46 + i * 20 + 2, 2, ywr);


        this.func_230441_a_(matrix, minecraft, mousX, mousY);
        int j = getFGColor();
        this.func_238472_a_(matrix, fontrenderer, this.func_230458_i_(), this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_ + (this.field_230689_k_ - 8) / 2, j | MathHelper.ceil(this.field_230695_q_ * 255.0F) << 24);
    }
}
