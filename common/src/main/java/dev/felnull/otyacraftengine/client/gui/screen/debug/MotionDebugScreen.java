package dev.felnull.otyacraftengine.client.gui.screen.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.debug.MotionDebug;
import dev.felnull.otyacraftengine.client.debug.socket.SocketDebugService;
import dev.felnull.otyacraftengine.client.gui.components.SwitchButton;
import dev.felnull.otyacraftengine.client.gui.screen.OEBaseScreen;
import dev.felnull.otyacraftengine.client.util.OEClientUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MotionDebugScreen extends OEBaseScreen {
    private final static Vector3f lastSocketRotation = new Vector3f();
    private static boolean socketRotationFixX = true;
    private static boolean socketRotationFixY = true;
    private static boolean socketRotationFixZ = true;

    public MotionDebugScreen(@Nullable Screen parent) {
        super(new TextComponent("Motion Debug"), parent);
    }

    @Override
    protected void init() {
        super.init();
        int st = 3 + mc.font.lineHeight * 6 + 2;
        var etsw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Enable temporary Motion"), n -> getMotionDebug().setTemporary(n.isEnable()), true));
        etsw.setEnable(getMotionDebug().isTemporary());
        st += 17;

        var tsw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Edit temporary Motion"), n -> getMotionDebug().setEditTemporary(n.isEnable()), true));
        tsw.setEnable(getMotionDebug().isEditTemporary());
        st += 17;

        var trsw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Edit Position - Rotation"), n -> getMotionDebug().setEditRotation(n.isEnable()), true));
        trsw.setEnable(getMotionDebug().isEditRotation());
        st += 15;

        addRenderableWidget(new Button(3, st, 100, 20, new TextComponent("Reset"), n -> getMotionDebug().reset()));
        st += 24;

        var srswfx = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Socket Rotation Fix X"), n -> socketRotationFixX = n.isEnable(), true));
        srswfx.setEnable(socketRotationFixX);
        st += 17;

        var srswfy = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Socket Rotation Fix Y"), n -> socketRotationFixY = n.isEnable(), true));
        srswfy.setEnable(socketRotationFixY);
        st += 17;

        var srswfz = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Socket Rotation Fix Z"), n -> socketRotationFixZ = n.isEnable(), true));
        srswfz.setEnable(socketRotationFixZ);
        st += 17;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int x, int y, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);

        drawTextBase(poseStack, mc.fpsString, 3, 3, 0xFFFFFF);
        drawTextBase(poseStack, "Debug - " + createMotionText(getMotionDebug().getPosition(), getMotionDebug().getRotation(), getMotionDebug().getScale()), 3, 3 + mc.font.lineHeight, 0xFFFFFF);
        drawTextBase(poseStack, "Current - " + createMotionText(getMotionDebug().getCurrentPosition(), getMotionDebug().getCurrentRotation(), getMotionDebug().getCurrentScale()), 3, 3 + mc.font.lineHeight * 2, 0xFFFFFF);
        drawTextBase(poseStack, "Temporary - " + createMotionText(getMotionDebug().getTemporaryPosition(), getMotionDebug().getTemporaryRotation(), getMotionDebug().getTemporaryScale()), 3, 3 + mc.font.lineHeight * 3, 0xFFFFFF);
        drawTextBase(poseStack, "Sensitivity: " + getMotionDebug().getSensitivity(), 3, 3 + mc.font.lineHeight * 4, 0xFFFFFF);
        var spos = SocketDebugService.getPosition(f);
        var srot = SocketDebugService.getAngele(f);
        drawTextBase(poseStack, !SocketDebugService.isConnected() ? "Socket - Not connected" : String.format("Socket - Pos: [X: %s  Y: %s  Z: %s], Rot: [X: %s, Y: %s, Z: %s]", (int) spos.x(), (int) spos.y(), (int) spos.z(), (int) srot.x(), (int) srot.y(), (int) srot.z()), 3, 3 + mc.font.lineHeight * 5, 0xFFFFFF);
    }

    @Override
    public void tick() {
        super.tick();
        var sr = SocketDebugService.getAngele(0);
        
        var sa = new Vector3f(lastSocketRotation.x() - sr.x(), lastSocketRotation.y() - sr.y(), lastSocketRotation.z() - sr.z());
        float vx = 0;
        float vy = 0;
        float vz = 0;

        if (!socketRotationFixX)
            vx = sa.y();
        if (!socketRotationFixY)
            vy = -sa.x();
        if (!socketRotationFixZ)
            vz = sa.z();

        getMotionDebug().addRotation(vx, vy, vz);
        lastSocketRotation.set(sr.x(), sr.y(), sr.z());
    }


    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        boolean l = i == 0;
        if (l) {
            addMotion((float) g, (float) f, 0);
        } else {
            addMotion(0, 0, (float) g);
        }
        return super.mouseDragged(d, e, i, f, g);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        float v = getMotionDebug().getSensitivity();
        if (OEClientUtil.isKeyInput(mc.options.keyShift))
            f *= 0.1f;
        v += (f * 0.1f);
        getMotionDebug().setSensitivity(Math.max(v, 0));
        return super.mouseScrolled(d, e, f);
    }

    private void addMotion(float x, float y, float z) {
        float sc = getMotionDebug().getSensitivity();
        x *= sc;
        y *= sc;
        z *= sc;
        if (getMotionDebug().isEditRotation())
            getMotionDebug().addRotation(-x, -y, -z);
        else
            getMotionDebug().addPosition(y * 0.05f, -x * 0.05f, z * 0.05f);
    }

    private MotionDebug getMotionDebug() {
        return MotionDebug.getInstance();
    }

    private static String createMotionText(Vector3f pos, Vector3f rot, Vector3f scale) {
        return String.format("Pos: [X: %s  Y: %s  Z: %s], Rot: [X: %s, Y: %s, Z: %s], Scale: [X: %s, Y: %s, Z: %s]", pos.x(), pos.y(), pos.z(), rot.x(), rot.y(), rot.z(), scale.x(), scale.y(), scale.z());
    }
}
