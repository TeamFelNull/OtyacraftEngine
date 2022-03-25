package dev.felnull.otyacraftengine.client.gui.screen.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.debug.MotionDebug;
import dev.felnull.otyacraftengine.client.debug.socket.SocketDebugService;
import dev.felnull.otyacraftengine.client.gui.components.BetterEditBox;
import dev.felnull.otyacraftengine.client.gui.components.SwitchButton;
import dev.felnull.otyacraftengine.client.gui.screen.OEBaseScreen;
import dev.felnull.otyacraftengine.client.util.OEClientUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.function.Predicate;

public class MotionDebugScreen extends OEBaseScreen {
    private final static Vector3f lastSocketRotation = new Vector3f();
    public static boolean pause;
    private static boolean socketRotationFixX = true;
    private static boolean socketRotationFixY = true;
    private static boolean socketRotationFixZ = true;
    private static boolean enableEdit;
    private SwitchButton editTypeSwitch;
    private SwitchButton enableEditSwitch;
    private BetterEditBox xEditBox;
    private BetterEditBox yEditBox;
    private BetterEditBox zEditBox;

    public MotionDebugScreen(@Nullable Screen parent) {
        super(new TextComponent("Motion Debug"), parent);
    }

    @Override
    protected void init() {
        super.init();
        int st = 3 + mc.font.lineHeight * 6 + 2;
        var etsw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Enable temporary Motion"), n -> getMotionDebug().setTemporary(n.isEnable()), true));
        etsw.setEnable(getMotionDebug().isTemporary());

        xEditBox = addRenderableWidget(new BetterEditBox(123, st, 50, 12, xEditBox, new TextComponent("x")));
        yEditBox = addRenderableWidget(new BetterEditBox(176, st, 50, 12, yEditBox, new TextComponent("y")));
        zEditBox = addRenderableWidget(new BetterEditBox(229, st, 50, 12, zEditBox, new TextComponent("z")));

        Predicate<String> fpr = n -> {
            try {
                Float.parseFloat(n);
                return true;
            } catch (Exception ignored) {
            }
            return n.isEmpty();
        };

        xEditBox.setFilter(fpr);
        yEditBox.setFilter(fpr);
        zEditBox.setFilter(fpr);

        addRenderableWidget(new Button(123, st + 15, 50, 20, new TextComponent("Input"), n -> {
            var cm = getMotion();
            xEditBox.setValue(String.valueOf(cm.x()));
            yEditBox.setValue(String.valueOf(cm.y()));
            zEditBox.setValue(String.valueOf(cm.z()));
        }));

        addRenderableWidget(new Button(176, st + 15, 50, 20, new TextComponent("Output"), n -> {
            if (!xEditBox.getValue().isEmpty() && !yEditBox.getValue().isEmpty() && !zEditBox.getValue().isEmpty()) {
                try {
                    float x = Float.parseFloat(xEditBox.getValue());
                    float y = Float.parseFloat(yEditBox.getValue());
                    float z = Float.parseFloat(zEditBox.getValue());
                    setMotion(x, y, z);
                } catch (Exception ignored) {
                }
            }
        }));

        addRenderableWidget(new Button(229, st + 15, 50, 20, new TextComponent("Copy"), n -> {
            if (!xEditBox.getValue().isEmpty() && !yEditBox.getValue().isEmpty() && !zEditBox.getValue().isEmpty())
                mc.keyboardHandler.setClipboard(String.format("%sf, %sf, %sf", xEditBox.getValue(), yEditBox.getValue(), zEditBox.getValue()));
        }));

        st += 17;

        var tsw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Edit temporary Motion"), n -> getMotionDebug().setEditTemporary(n.isEnable()), true));
        tsw.setEnable(getMotionDebug().isEditTemporary());
        st += 17;

        editTypeSwitch = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Edit Position - Rotation"), n -> getMotionDebug().setEditRotation(n.isEnable()), true));
        editTypeSwitch.setEnable(getMotionDebug().isEditRotation());
        st += 15;

        addRenderableWidget(new Button(3, st, 120, 20, new TextComponent("Reset"), n -> getMotionDebug().reset()));
        st += 24;

        enableEditSwitch = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Enable Edit"), n -> enableEdit = n.isEnable(), true));
        enableEditSwitch.setEnable(enableEdit);
        st += 17;

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
    public boolean keyPressed(int i, int j, int k) {
        if (i == GLFW.GLFW_KEY_R) {
            getMotionDebug().setEditRotation(!getMotionDebug().isEditRotation());
            editTypeSwitch.setEnable(getMotionDebug().isEditRotation());
            return true;
        } else if (i == GLFW.GLFW_KEY_W) {
            enableEdit = !enableEdit;
            enableEditSwitch.setEnable(enableEdit);
            return true;
        }
        if (enableEdit) {
            boolean sflg = OEClientUtil.isKeyInput(mc.options.keyShift);

            if (i == GLFW.GLFW_KEY_LEFT) {
                addMotion(0, -1, 0);
                return true;
            } else if (i == GLFW.GLFW_KEY_RIGHT) {
                addMotion(0, 1, 0);
                return true;
            } else if (i == GLFW.GLFW_KEY_UP) {
                if (sflg)
                    addMotion(0, 0, -1);
                else
                    addMotion(-1, 0, 0);
                return true;
            } else if (i == GLFW.GLFW_KEY_DOWN) {
                if (sflg)
                    addMotion(0, 0, 1);
                else
                    addMotion(1, 0, 0);
                return true;
            }
        }
        return super.keyPressed(i, j, k);
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
        if (!enableEdit) return;
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
        if (enableEdit) {
            boolean l = i == 0;
            if (l) {
                addMotion((float) g, (float) f, 0);
            } else {
                addMotion(0, 0, (float) g);
            }
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

    private void setMotion(float x, float y, float z) {
        if (getMotionDebug().isEditRotation())
            getMotionDebug().setRotation(x, y, z);
        else
            getMotionDebug().setPosition(x, y, z);
    }

    private Vector3f getMotion() {
        if (getMotionDebug().isEditRotation())
            return getMotionDebug().getRotation();
        else
            return getMotionDebug().getPosition();
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

    @Override
    public boolean isPauseScreen() {
        return pause;
    }
}
