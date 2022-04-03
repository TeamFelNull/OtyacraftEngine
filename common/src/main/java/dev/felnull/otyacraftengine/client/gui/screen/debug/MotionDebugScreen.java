package dev.felnull.otyacraftengine.client.gui.screen.debug;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.felnull.otyacraftengine.client.debug.MotionDebug;
import dev.felnull.otyacraftengine.client.debug.socket.SocketDebugService;
import dev.felnull.otyacraftengine.client.gui.components.BetterEditBox;
import dev.felnull.otyacraftengine.client.gui.components.FixedListWidget;
import dev.felnull.otyacraftengine.client.gui.components.SwitchButton;
import dev.felnull.otyacraftengine.client.gui.screen.OEBaseScreen;
import dev.felnull.otyacraftengine.client.motion.Motion;
import dev.felnull.otyacraftengine.client.motion.MotionPoint;
import dev.felnull.otyacraftengine.client.util.OEClientUtil;
import dev.felnull.otyacraftengine.client.util.OEScreenUtil;
import dev.felnull.otyacraftengine.mixin.client.ScreenAccessor;
import dev.felnull.otyacraftengine.util.OEPaths;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class MotionDebugScreen extends OEBaseScreen {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final SimpleDateFormat saveDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static final Vector3f lastSocketRotation = new Vector3f();
    private static boolean enableVisible = true;
    public static boolean pause;
    private static boolean socketRotationFixX = true;
    private static boolean socketRotationFixY = true;
    private static boolean socketRotationFixZ = true;
    private static boolean enableEdit = true;
    private Button editTypeButton;
    private SwitchButton enableEditSwitch;
    private BetterEditBox xEditBox;
    private BetterEditBox yEditBox;
    private BetterEditBox zEditBox;
    private MotionListWidget motionListWidget;
    private Button startButton;
    private Button stopButton;
    private BetterEditBox motionSpeedEditBox;
    private BetterEditBox ratioEditBox;

    public MotionDebugScreen(@Nullable Screen parent) {
        super(new TextComponent("Motion Debug"), parent);
    }

    @Override
    protected void init() {
        super.init();

        var vib = addRenderableWidget(new Button(width - 13, 3, 10, 10, new TextComponent("I"), this::toggleEnableVisible));


        int st = 3 + mc.font.lineHeight * 6 + 2;
        var etsw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Enable temporary Motion"), n -> getMotionDebug().setEnableTemporary(n.isEnable()), true));
        etsw.setEnable(getMotionDebug().isEnableTemporary());

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

        addRenderableWidget(new Button(123, st + 15 + 23, 50, 20, new TextComponent("All Copy"), n -> {
            var p = getMotionDebug().createPoint();
            var pos = p.getPosition();
            var rot = p.getRotation();
            var ang = rot.angle();
            var ori = rot.origin();
            var res = rot.reset();
            mc.keyboardHandler.setClipboard(String.format("%sf, %sf, %sf, %sf, %sf, %sf, %sf, %sf, %sf, %s, %s, %s", pos.x(), pos.y(), pos.z(), ang.x(), ang.y(), ang.z(), ori.x(), ori.y(), ori.z(), res.getLeft(), res.getMiddle(), res.getLeft()));
        }));

        addRenderableWidget(new Button(123 + 53, st + 15 + 23, 50, 20, new TextComponent("All Paste"), n -> {
            parseAll(mc.keyboardHandler.getClipboard());
        }));

        motionListWidget = addRenderableWidget(new MotionListWidget(width - 3 - 120, st - 20, 120, 50, 5, getMotionDebug().getPoints(), (widget, item) -> {
            var e = motionListWidget.getSelectedEntry();
            if (e != null)
                getMotionDebug().setPoint(e);
        }, motionListWidget));

        addRenderableWidget(new Button(width - 3 - 120, st + 50 + 3 - 20, 27, 20, new TextComponent("Add"), n -> addPoint()));

        addRenderableWidget(new Button(width - 3 - 120 + 30, st + 50 + 3 - 20, 27, 20, new TextComponent("Del"), n -> {
            var e = motionListWidget.getSelectedEntry();
            if (e != null) {
                int num = motionListWidget.getSelectedEntryIndex();
                getMotionDebug().getPoints().remove(num);
                motionListWidget.setSelectedEntry(num - 1);
            }
        }));

        addRenderableWidget(new Button(width - 3 - 120 + 60, st + 50 + 3 - 20, 27, 20, new TextComponent("Set"), n -> {
            int num = motionListWidget.getSelectedEntryIndex();
            getMotionDebug().getPoints().set(num, getMotionDebug().createPoint());
            motionListWidget.setSelectedEntry(num);
        }));

        addRenderableWidget(new Button(width - 3 - 120 + 90, st + 50 + 3 - 20, 27, 20, new TextComponent("Inj"), n -> {
            var e = motionListWidget.getSelectedEntry();
            if (e != null) {
                var ne = getMotionDebug().createPoint();
                int ei = motionListWidget.getSelectedEntryIndex();
                if (ei - 1 >= getMotionDebug().getPoints().size()) {
                    getMotionDebug().getPoints().add(ne);
                    motionListWidget.setSelectedEntry(getMotionDebug().getPoints().size() - 1);
                } else {
                    getMotionDebug().getPoints().add(ei + 1, ne);
                    motionListWidget.setSelectedEntry(ei + 1);
                }
                getMotionDebug().setRotationReset(false, false, false);
            }
        }));

        addRenderableWidget(new Button(width - 3 - 120, st + 50 + 3 - 20 + 23, 27, 20, new TextComponent("Output"), n -> {
            if (getMotionDebug().getPoints().isEmpty()) return;
            var pt = OEPaths.getClientOEFolderPath().resolve("debug").resolve("motion");
            pt.toFile().mkdirs();
            var motion = getMotionDebug().createMotion();
            var jo = motion.toJson();
            try (Writer writer = new FileWriter(pt.resolve(saveDateFormat.format(new Date()) + ".json").toFile())) {
                GSON.toJson(jo, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        addRenderableWidget(new Button(width - 3 - 120 + 30, st + 50 + 3 - 20 + 23, 27, 20, new TextComponent("Input"), n -> {
            var files = OEClientUtil.openFileChooser(null, OEPaths.getClientOEFolderPath(), null, false);
            loadJson(files);
        }));

        startButton = addRenderableWidget(new Button(width - 3 - 120 + 60, st + 50 + 3 - 20 + 23, 27, 20, new TextComponent("Start"), n -> {
            n.active = false;
            stopButton.active = true;
            try {
                var str = motionSpeedEditBox.getValue();
                long val = str.isEmpty() ? 3000 : Math.max(Long.parseLong(str), 1);
                getMotionDebug().startMotion(val);
            } catch (NumberFormatException ignored) {
            }
        }));
        startButton.active = !getMotionDebug().isMotionPlaying();


        stopButton = addRenderableWidget(new Button(width - 3 - 120 + 90, st + 50 + 3 - 20 + 23, 27, 20, new TextComponent("Stop"), n -> {
            n.active = false;
            startButton.active = true;
            getMotionDebug().stopMotion();
        }));
        stopButton.active = getMotionDebug().isMotionPlaying();


        motionSpeedEditBox = addRenderableWidget(new BetterEditBox(width - 3 - 120, st + 50 + 3 - 20 + 23 + 23, 120, 12, motionSpeedEditBox, new TextComponent("Motion Speed")));
        motionSpeedEditBox.setFilter(fpr);

        ratioEditBox = addRenderableWidget(new BetterEditBox(width - 3 - 120, st + 50 + 3 - 20 + 23 + 23 + 20, 50, 12, ratioEditBox, new TextComponent("Ratio")));
        ratioEditBox.setFilter(fpr);
        ratioEditBox.setValue(String.valueOf(getMotionDebug().getRatio()));
        ratioEditBox.setResponder(n -> {
            try {
                float r = ratioEditBox.getValue().isEmpty() ? 1f : Float.parseFloat(ratioEditBox.getValue());
                getMotionDebug().setRatio(r);
            } catch (NumberFormatException ignored) {
            }
        });

        st += 17;

        var tsw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Edit temporary Motion"), n -> getMotionDebug().setEditTemporary(n.isEnable()), true));
        tsw.setEnable(getMotionDebug().isEditTemporary());
        st += 15;

        editTypeButton = addRenderableWidget(new Button(3, st, 120, 20, new TextComponent(getMotionDebug().getEditType().name()), n -> setNextEditType()));
        st += 24;

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
        st += 15;

        addRenderableWidget(new Button(3, st, 40, 20, new TextComponent("Reset X"), n -> {
            var o = getMotionDebug().getRotation().reset();
            getMotionDebug().setRotationReset(true, o.getMiddle(), o.getRight());
            var oa = getMotionDebug().getRotation().angle();
            getMotionDebug().setRotationAngle(new Vector3f(oa.x() % 360, oa.y(), oa.z()));
        }));

        addRenderableWidget(new Button(3 + 40 + 3, st, 40, 20, new TextComponent("Reset Y"), n -> {
            var o = getMotionDebug().getRotation().reset();
            getMotionDebug().setRotationReset(o.getLeft(), true, o.getRight());
            var oa = getMotionDebug().getRotation().angle();
            getMotionDebug().setRotationAngle(new Vector3f(oa.x(), oa.y() % 360, oa.z()));
        }));

        addRenderableWidget(new Button(3 + 40 + 3 + 3 + 40, st, 40, 20, new TextComponent("Reset Z"), n -> {
            var o = getMotionDebug().getRotation().reset();
            getMotionDebug().setRotationReset(o.getLeft(), o.getMiddle(), true);
            var oa = getMotionDebug().getRotation().angle();
            getMotionDebug().setRotationAngle(new Vector3f(oa.x(), oa.y(), oa.z() % 360));
        }));

        st += 24;

        var fosw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Fix Origin"), n -> getMotionDebug().setFixOrigin(n.isEnable()), true));
        fosw.setEnable(getMotionDebug().isFixOrigin());
        st += 17;

        var sosw = addRenderableWidget(new SwitchButton(3, st, new TextComponent("Show Origin"), n -> getMotionDebug().setShowOrigin(n.isEnable()), true));
        sosw.setEnable(getMotionDebug().isShowOrigin());

        var sca = (ScreenAccessor) this;
        sca.getRenderables().forEach(n -> {
            if (n == vib) return;
            if (n instanceof AbstractWidget w)
                w.visible = enableVisible;
        });
    }

    private void parseAll(String text) {
        if (text == null || text.isEmpty()) return;
        text = text.replace(" ", "");
        var str = text.split(",");
        try {
            float[] f = new float[9];
            for (int i = 0; i < f.length; i++) {
                f[i] = parseFloat(str[i]);
            }
            boolean[] b = new boolean[3];
            for (int i = 0; i < b.length; i++) {
                b[i] = Boolean.parseBoolean(str[f.length + i]);
            }
            getMotionDebug().setPosition(new Vector3f(f[0], f[1], f[2]));
            getMotionDebug().setRotationAngle(new Vector3f(f[3], f[4], f[5]));
            getMotionDebug().setRotationOrigin(new Vector3f(f[6], f[7], f[8]), true);
            getMotionDebug().setRotationReset(b[0], b[1], b[2]);
        } catch (Exception ignored) {
        }
//        mc.keyboardHandler.setClipboard(String.format("%sf, %sf, %sf, %sf, %sf, %sf, %sf, %sf, %sf, %s, %s, %s", pos.x(), pos.y(), pos.z(), ang.x(), ang.y(), ang.z(), ori.x(), ori.y(), ori.z(), res.getLeft(), res.getMiddle(), res.getLeft()));
    }

    private float parseFloat(String text) {
        try {
            text = text.replace("f", "");
            return Float.parseFloat(text);
        } catch (NumberFormatException ignored) {
        }
        return 0f;
    }

    protected void loadJson(File[] files) {
        if (files == null || files.length != 1) return;
        var fil = files[0];
        JsonObject jo = null;

        try (Reader reader = new FileReader(fil)) {
            jo = GSON.fromJson(reader, JsonObject.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (jo == null) return;
        getMotionDebug().setMotion(Motion.of(jo));
        motionListWidget.setSelectedEntry(0);
    }

    public void setNextEditType() {
        var et = getMotionDebug().getEditType();
        getMotionDebug().setEditType(MotionDebug.EditType.values()[(et.ordinal() + 1) % MotionDebug.EditType.values().length]);
        editTypeButton.setMessage(new TextComponent(getMotionDebug().getEditType().name()));
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (isHoveredWidget((float) OEClientUtil.getMouseX(), (float) OEClientUtil.getMouseY()))
            return super.keyPressed(i, j, k);

        if (i == GLFW.GLFW_KEY_R) {
            setNextEditType();
            return true;
        } else if (i == GLFW.GLFW_KEY_W) {
            enableEdit = !enableEdit;
            enableEditSwitch.setEnable(enableEdit);
            return true;
        } else if (i == GLFW.GLFW_KEY_Y) {
            toggleEnableVisible(null);
            return true;
        } else if (i == GLFW.GLFW_KEY_A) {
            addPoint();
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
        if (enableVisible)
            this.renderBackground(poseStack);
        super.render(poseStack, x, y, f);

        drawTextBase(poseStack, mc.fpsString, 3, 3, 0xFFFFFF);
        drawTextBase(poseStack, "Motion Point - " + createMotionText(getMotionDebug().getPosition(), getMotionDebug().getRotation().angle(), getMotionDebug().getRotation().origin()), 3, 3 + mc.font.lineHeight, 0xFFFFFF);
        var reset = getMotionDebug().getRotation().reset();
        drawTextBase(poseStack, String.format("Reset: [X: %s, Y: %s, Z: %s]", reset.getLeft() ? "T" : "F", reset.getMiddle() ? "T" : "F", reset.getRight() ? "T" : "F"), 3, 3 + mc.font.lineHeight * 2, 0xFFFFFF);
        drawTextBase(poseStack, "Temporary - " + createMotionText(getMotionDebug().getTemporaryPosition(), getMotionDebug().getTemporaryRotation().angle(), getMotionDebug().getTemporaryRotation().origin()), 3, 3 + mc.font.lineHeight * 3, 0xFFFFFF);
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

        getMotionDebug().addRotationAngle(vx, vy, vz);
        lastSocketRotation.set(sr.x(), sr.y(), sr.z());
    }


    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        if (!isHoveredWidget((float) d, (float) e) && enableEdit) {
            boolean l = i == 0;
            if (l) {
                addMotion((float) g, (float) f, 0);
            } else {
                addMotion(0, (float) f, (float) g);
            }
        }
        return super.mouseDragged(d, e, i, f, g);
    }

    private void toggleEnableVisible(Button button) {
        ScreenAccessor accessor = (ScreenAccessor) this;
        enableVisible = !enableVisible;
        accessor.getRenderables().forEach(m -> {
            if (m == button) return;
            if (m instanceof AbstractWidget w)
                w.visible = enableVisible;
        });
    }

    private void addPoint() {
        var ne = getMotionDebug().createPoint();
        getMotionDebug().getPoints().add(ne);
        motionListWidget.setSelectedEntry(getMotionDebug().getPoints().size() - 1);
        getMotionDebug().setRotationReset(false, false, false);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double f) {
        if (!isHoveredWidget((float) x, (float) y)) {
            float v = getMotionDebug().getSensitivity();
            if (OEClientUtil.isKeyInput(mc.options.keyShift))
                f *= 0.1f;
            v += (f * 0.1f);
            getMotionDebug().setSensitivity(Math.max(v, 0));
        }
        return super.mouseScrolled(x, y, f);
    }

    private boolean isHoveredWidget(float x, float y) {
        if (!enableVisible) return false;

        return OEScreenUtil.getRenderableAbstractWidgets(this).stream().filter(n -> x >= n.x && y >= n.y && x < (n.x + n.getWidth()) && y < (double) (n.y + n.getHeight())).anyMatch(n -> true);
    }

    private void setMotion(float x, float y, float z) {
        switch (getMotionDebug().getEditType()) {
            case POSITION -> getMotionDebug().setPosition(new Vector3f(x, y, z));
            case ROTATION -> getMotionDebug().setRotationAngle(new Vector3f(x, y, z));
            case ROTATION_ORIGIN -> getMotionDebug().setRotationOrigin(new Vector3f(x, y, z));
        }
    }

    private Vector3f getMotion() {
        return switch (getMotionDebug().getEditType()) {
            case POSITION -> getMotionDebug().getPosition();
            case ROTATION -> getMotionDebug().getRotation().angle();
            case ROTATION_ORIGIN -> getMotionDebug().getRotation().origin();
        };
    }

    private void addMotion(float x, float y, float z) {
        float sc = getMotionDebug().getSensitivity();
        x *= sc;
        y *= sc;
        z *= sc;

        if (getMotionDebug().isEditTemporary()) {
            switch (getMotionDebug().getEditType()) {
                case POSITION -> getMotionDebug().addTemporaryPosition(y * 0.05f, -x * 0.05f, z * 0.05f);
                case ROTATION -> getMotionDebug().addTemporaryRotationAngle(-x, -y, -z);
                case ROTATION_ORIGIN -> getMotionDebug().addRotationOrigin(y * 0.05f, -x * 0.05f, z * 0.05f);
            }
        } else {
            switch (getMotionDebug().getEditType()) {
                case POSITION -> getMotionDebug().addPosition(y * 0.05f, -x * 0.05f, z * 0.05f);
                case ROTATION -> getMotionDebug().addRotationAngle(-x, -y, -z);
                case ROTATION_ORIGIN -> getMotionDebug().addRotationOrigin(y * 0.05f, -x * 0.05f, z * 0.05f);
            }
        }
    }

    private MotionDebug getMotionDebug() {
        return MotionDebug.getInstance();
    }

    private static String createMotionText(Vector3f pos, Vector3f angle, Vector3f origin) {
        return String.format("Pos: [X: %s  Y: %s  Z: %s], Angle: [X: %s, Y: %s, Z: %s], Origin: [X: %s, Y: %s, Z: %s]", pos.x(), pos.y(), pos.z(), angle.x(), angle.y(), angle.z(), origin.x(), origin.y(), origin.z());
    }

    @Override
    public boolean isPauseScreen() {
        return pause;
    }

    private static class MotionListWidget extends FixedListWidget<MotionPoint> {
        public MotionListWidget(int x, int y, int width, int height, int entryShowCount, @NotNull List<MotionPoint> entryList, @Nullable PressEntry<MotionPoint> onPressEntry, MotionListWidget old) {
            super(x, y, width, height, new TextComponent("Motion List"), entryShowCount, entryList, MotionPoint::getText, onPressEntry, true, old);
        }
    }
}
