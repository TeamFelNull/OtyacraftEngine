package red.felnull.otyacraftengine.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
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
        return getTileEntity() != null ? getTileEntity().getPos() : null;
    }

    //drawGuiContainerBackgroundLayer
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matx, float partTick, int mouseX, int mouseY) {
        drawGuiContainerBackgroundLayerByIKSG(matx, partTick, mouseX, mouseY);
    }

    //renderHoveredToolTip
    @Override
    protected void renderHoveredTooltip(MatrixStack matrix, int mouseX, int mouseY) {
        this.renderHoveredToolTipByIKSG(matrix, mouseX, mouseY);
    }

    //バッググラウンド描画
    protected abstract void drawGuiContainerBackgroundLayerByIKSG(MatrixStack matx, float partTick, int mouseX, int mouseY);

    //ツールチップ？
    protected void renderHoveredToolTipByIKSG(MatrixStack matrix, int mouseX, int mouseY) {
        super.renderHoveredTooltip(matrix, mouseX, mouseY);
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

    //dropAndDrag
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
        return width;
    }

    //高さ
    public int getHeightByIKSG() {
        return height;
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
