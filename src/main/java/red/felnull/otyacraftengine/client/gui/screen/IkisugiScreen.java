package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

import java.nio.file.Path;
import java.util.List;

public abstract class IkisugiScreen extends Screen implements IOpendScreen {
    protected IkisugiScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    //init
    @Override
    public void init() {
        this.initByIKSG();
    }

    //render
    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderByIKSG(matrix, mouseX, mouseY, parTick);
    }

    //keyPressed
    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return keyPressedByIKSG(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    //onClose
    @Override
    public void onClose() {
        onCloseByIKSG();
    }

    //isPauseScreen
    @Override
    public boolean isPauseScreen() {
        return isPauseScreenByIKSG();
    }

    //tick
    @Override
    public void tick() {
        this.tickByIKSG();
    }

    //addPacks
    @Override
    public void addPacks(List<Path> dragFiles) {
        this.dropAndDragByIKSG(dragFiles);
    }


    //初期化
    public void initByIKSG() {
        super.init();
    }

    //描画
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        super.render(matrix, mouseX, mouseY, parTick);
    }

    //キー判定
    public boolean keyPressedByIKSG(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    //文字を中央から描画
    public void drawCenterStringByIKSG(MatrixStack matrix, ITextComponent textComp, int x, int y, int color) {
        AbstractGui.drawCenteredString(matrix, this.font, textComp, x, y, color);
    }

    //ボタン等のウィジェット追加
    protected <T extends Widget> T addWidgetByIKSG(T widget) {
        return addButton(widget);
    }

    //幅
    public int getWidthByIKSG() {
        return this.width;
    }

    //高さ
    public int getHeightByIKSG() {
        return this.height;
    }

    //閉じる
    public void onCloseByIKSG() {
        super.onClose();
    }

    //バッググラウ描画
    public void renderBackgroundByIKSG(MatrixStack matrix) {
        this.renderBackground(matrix);
    }

    //開いてる時にワールドを進めるかどうか
    public boolean isPauseScreenByIKSG() {
        return super.isPauseScreen();
    }

    public void renderDirtBackgroundByIKSG(int intege) {
        this.renderDirtBackground(intege);
    }

    //ティック
    public void tickByIKSG() {
        super.tick();
    }

    //タイトル
    public ITextComponent getTitleByIKSG() {
        return this.getTitle();
    }

    //ウィンドウにドロップアンドドラッグされたとき
    public void dropAndDragByIKSG(List<Path> dragFiles) {
        super.addPacks(dragFiles);
    }


}
