package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.container.IkisugiContainer;

import java.nio.file.Path;
import java.util.List;

public abstract class IkisugiContainerScreen<T extends Container> extends ContainerScreen<T> implements IInstructionContainerScreen, IOpendScreen {
    public IkisugiContainerScreen(T screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
    }

    public int getTexturStartX() {
        return (this.getWidthByIKSG() - this.xSize) / 2;
    }

    public int getTexturStartY() {
        return (this.getHeightByIKSG() - this.ySize) / 2;
    }

    @Override
    public BlockPos getInstrunctionPos() {
        return getTileEntity().getPos();
    }

    //drawGuiContainerBackgroundLayer
    @Override
    protected void func_230450_a_(MatrixStack matx, float partTick, int mouseX, int mouseY) {
        drawGuiContainerBackgroundLayerByIKSG(matx, partTick, mouseX, mouseY);
    }

    //renderHoveredToolTip
    @Override
    protected void func_230459_a_(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderHoveredToolTipByIKSG(matrix, mouseX, mouseY);
    }

    //バッググラウンド描画
    protected abstract void drawGuiContainerBackgroundLayerByIKSG(MatrixStack matx, float partTick, int mouseX, int mouseY);

    //ツールチップ？
    protected void renderHoveredToolTipByIKSG(MatrixStack matrix, int mouseX, int mouseY) {
        super.func_230459_a_(matrix, mouseX, mouseY);
    }

    //タイルエンティティ取得
    public TileEntity getTileEntity() {

        if (getContainer() instanceof IkisugiContainer)
            return OtyacraftEngine.proxy.getMinecraft().world.getTileEntity(((IkisugiContainer) this.getContainer()).getPos());

        return null;
    }

    //以下IkisugiScreen
    //init
    @Override
    public void func_231160_c_() {
        this.initByIKSG();
    }

    //render
    @Override
    public void func_230430_a_(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        this.renderByIKSG(matrix, mouseX, mouseY, parTick);
    }

    //keyPressed
    @Override
    public boolean func_231046_a_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return keyPressedByIKSG(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    //onClose
    @Override
    public void func_231175_as__() {
        onCloseByIKSG();
    }

    //isPauseScreen
    @Override
    public boolean func_231177_au__() {
        return isPauseScreenByIKSG();
    }

    //tick
    @Override
    public void func_231023_e_() {
        this.tickByIKSG();
    }

    //dropAndDrag
    @Override
    public void func_230476_a_(List<Path> dragFiles) {
        this.dropAndDragByIKSG(dragFiles);
    }


    //初期化
    public void initByIKSG() {
        super.func_231160_c_();
    }

    //描画
    public void renderByIKSG(MatrixStack matrix, int mouseX, int mouseY, float parTick) {
        super.func_230430_a_(matrix, mouseX, mouseY, parTick);
    }

    //キー判定
    public boolean keyPressedByIKSG(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return super.func_231046_a_(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    //文字を中央から描画
    public void drawCenterStringByIKSG(MatrixStack matrix, ITextComponent textComp, int x, int y, int color) {
        this.func_238472_a_(matrix, this.field_230712_o_, textComp, x, y, color);
    }

    //ボタン等のウィジェット追加
    protected <T extends Widget> T addWidgetByIKSG(T widget) {
        return func_230480_a_(widget);
    }

    //幅
    public int getWidthByIKSG() {
        return field_230708_k_;
    }

    //高さ
    public int getHeightByIKSG() {
        return field_230709_l_;
    }

    //閉じる
    public void onCloseByIKSG() {
        super.func_231175_as__();
    }

    //バッググラウ描画
    public void renderBackgroundByIKSG(MatrixStack matrix) {
        this.func_230446_a_(matrix);
    }

    //開いてる時にワールドを進めるかどうか
    public boolean isPauseScreenByIKSG() {
        return super.func_231177_au__();
    }

    public void renderDirtBackgroundByIKSG(int intege) {
        this.func_231165_f_(intege);
    }

    //ティック
    public void tickByIKSG() {
        super.func_231023_e_();
    }

    //タイトル
    public ITextComponent getTitleByIKSG() {
        return this.field_230704_d_;
    }

    //ウィンドウにドロップアンドドラッグされたとき
    public void dropAndDragByIKSG(List<Path> dragFiles) {
        super.func_230476_a_(dragFiles);
    }

    //文字を描画
    public void drawStringByIKSG(MatrixStack matx, FontRenderer font, String text, int x, int y, int color) {
        super.func_238476_c_(matx, font, text, x, y, color);
    }
}
