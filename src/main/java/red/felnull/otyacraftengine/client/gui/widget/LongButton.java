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
    public void renderButton(MatrixStack matrix, int mousX, int mousY, float parTick) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();


        this.blit(matrix, this.x, this.y, 0, 46 + i * 20, 2, 2);
        this.blit(matrix, this.x + width - 2, this.y, 198, 46 + i * 20, 2, 2);
        this.blit(matrix, this.x, this.y + height - 3, 0, 46 + i * 20 + 17, 2, 3);
        this.blit(matrix, this.x + width - 2, this.y + height - 3, 198, 46 + i * 20 + 17, 2, 3);

        int xtsize = 200 - 4;
        int xw = (width - 4) / xtsize;
        for (int c = 0; c < xw; c++) {
            this.blit(matrix, this.x + c * xtsize + 2, this.y, 2, 46 + i * 20, xtsize, 2);
            this.blit(matrix, this.x + c * xtsize + 2, this.y + height - 3, 2, 46 + i * 20 + 17, xtsize, 3);

            int nytsize = 20 - 5;
            int nyw = (height - 5) / nytsize;
            for (int c2 = 0; c2 < nyw; c2++) {
                this.blit(matrix, this.x + c * xtsize + 2, this.y + 2 + c2 * nytsize, 2, 46 + i * 20 + 2, xtsize, nytsize);
            }
            int nywr = (height - 5) - nyw * nytsize;
            this.blit(matrix, this.x + c * xtsize + 2, this.y + 2 + nyw * nytsize, 2, 46 + i * 20 + 2, xtsize, nywr);
        }
        int xwr = (width - 4) - xw * xtsize;
        this.blit(matrix, this.x + xw * xtsize + 2, this.y, 2, 46 + i * 20, xwr, 2);
        this.blit(matrix, this.x + xw * xtsize + 2, this.y + height - 3, 2, 46 + i * 20 + 17, xwr, 3);

        int nytsize = 20 - 5;
        int nyw = (height - 5) / nytsize;
        for (int c2 = 0; c2 < nyw; c2++) {
            this.blit(matrix, this.x + xw * xtsize + 2, this.y + 2 + c2 * nytsize, 2, 46 + i * 20 + 2, xwr, nytsize);
        }
        int nywr = (height - 5) - nyw * nytsize;
        this.blit(matrix, this.x + xw * xtsize + 2, this.y + 2 + nyw * nytsize, 2, 46 + i * 20 + 2, xwr, nywr);


        int ytsize = 20 - 5;
        int yw = (height - 5) / ytsize;
        for (int c = 0; c < yw; c++) {
            this.blit(matrix, this.x, this.y + c * ytsize + 2, 0, 46 + i * 20 + 2, 2, ytsize);
            this.blit(matrix, this.x + width - 2, this.y + c * ytsize + 2, 198, 46 + i * 20 + 2, 2, ytsize);
        }
        int ywr = (height - 5) - yw * ytsize;
        this.blit(matrix, this.x, this.y + yw * ytsize + 2, 0, 46 + i * 20 + 2, 2, ywr);
        this.blit(matrix, this.x + width - 2, this.y + yw * ytsize + 2, 198, 46 + i * 20 + 2, 2, ywr);


        this.renderBg(matrix, minecraft, mousX, mousY);
        int j = getFGColor();
        this.drawCenteredString(matrix, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }
}
