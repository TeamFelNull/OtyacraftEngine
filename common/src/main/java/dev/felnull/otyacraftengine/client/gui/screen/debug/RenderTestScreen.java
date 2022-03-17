package dev.felnull.otyacraftengine.client.gui.screen.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.gui.screen.OEBaseScreen;
import dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest.BakedModelRenderTest;
import dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest.IRenderTest;
import dev.felnull.otyacraftengine.client.gui.screen.debug.rendertest.ItemRenderTest;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RenderTestScreen extends OEBaseScreen {
    private static final List<IRenderTest> renderTests = new ArrayList<>();
    private int currentTest;
    private long eqAll;
    private long lastTime = -1;
    private long rtAll;
    private int sampleCount;
    private int testCount = 1;
    private Motion motion = Motion.FIX;

    private EditBox countBox;

    public RenderTestScreen(@Nullable Screen parent) {
        super(new TextComponent("Render Test"), parent);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button(3, height - 23, 100, 20, CommonComponents.GUI_BACK, n -> mc.setScreen(getParentScreen())));
        this.addRenderableWidget(new Button(106, height - 23, 100, 20, new TextComponent("Reset"), n -> reset()));

        this.countBox = this.addRenderableWidget(new EditBox(mc.font, 209, height - 23, 70, 20, new TextComponent("Count")));
        this.countBox.setValue(String.valueOf(testCount));
        this.countBox.setResponder(n -> {
            if (!n.isEmpty()) {
                try {
                    int val = Integer.parseInt(n);
                    if (val < 0) {
                        this.countBox.setValue("0");
                    } else {
                        setCount(val);
                    }
                } catch (NumberFormatException e) {
                    this.countBox.setValue("0");
                }
            } else {
                this.countBox.setValue("0");
            }
        });

        this.addRenderableWidget(new Button(282, height - 23, 20, 20, new TextComponent("-1"), n -> this.countBox.setValue(String.valueOf(testCount - 1))));
        this.addRenderableWidget(new Button(305, height - 23, 20, 20, new TextComponent("+1"), n -> this.countBox.setValue(String.valueOf(testCount + 1))));

        this.addRenderableWidget(new Button(328, height - 23, 100, 20, new TextComponent("Motion: " + motion.name()), n -> {
            motion = Motion.values()[(motion.ordinal() + 1) % Motion.values().length];
            n.setMessage(new TextComponent("Motion: " + motion.name()));
        }));

        if (!renderTests.isEmpty()) {
            this.addRenderableWidget(new Button(width - 23, 3, 20, 20, new TextComponent("->"), n -> {
                currentTest = (currentTest + 1) % renderTests.size();
                reset();
            }));
            this.addRenderableWidget(new Button(width - 46, 3, 20, 20, new TextComponent("<-"), n -> {
                currentTest = (currentTest - 1) % renderTests.size();
                if (currentTest < 0)
                    currentTest = renderTests.size() - 1;
                reset();
            }));
        }

        reset();
    }

    private void setCount(int count) {
        testCount = count;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mx, int my, float f) {
        long rt = System.nanoTime() - lastTime;
        if (lastTime >= 0)
            rtAll += rt;

        this.renderDirtBackground(0);
        float sy = height - height / 4f;
        drawRelativeFill(poseStack, 0, 0, width, sy, 0xFFFFFFFF);
        drawRelativeFill(poseStack, 0, 0, width, sy - 1f, 0xFF000000);
        super.render(poseStack, mx, my, f);

        if (renderTests.isEmpty()) return;

        MultiBufferSource.BufferSource buf = mc.renderBuffers().bufferSource();

        long st = System.nanoTime();
        renderTest(poseStack, buf, f, testCount);
        long eq = System.nanoTime() - st;

        buf.endBatch();

        eqAll += eq;
        sampleCount++;
        float eqAvg = (float) eqAll / (float) sampleCount;
        float rtAvg = (float) rtAll / (float) (sampleCount - 1);

        drawTextBase(poseStack, mc.fpsString, 3f, sy, 0xFFFFFF);

        drawTextBase(poseStack, String.format("Rendering Time: %.3fms %06dns", eq / 1000000f, eq), 3f, sy + mc.font.lineHeight + 1, 0xFFFFFF);
        drawTextBase(poseStack, String.format("Rendering Average Time: %.3fms %06dns", eqAvg / 1000000f, (int) eqAvg), 3f, sy + (mc.font.lineHeight + 1) * 2, 0xFFFFFF);

        drawTextBase(poseStack, String.format("Return Time: %.3fms %06dns", rt / 1000000f, rt), 3f + width / 2f, sy + mc.font.lineHeight + 1, 0xFFFFFF);
        drawTextBase(poseStack, String.format("Return Average Time: %.3fms %06dns", rtAvg / 1000000f, (int) rtAvg), 3f + width / 2f, sy + (mc.font.lineHeight + 1) * 2, 0xFFFFFF);

        lastTime = System.nanoTime();
    }

    private void reset() {
        eqAll = 0;
        sampleCount = 0;
        lastTime = -1;
        rtAll = 0;
    }

    private void renderTest(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, float f, int count) {
        float sy = height - height / 4f;

        poseStack.pushPose();


        if (motion != Motion.TRANSLATED && motion != Motion.ROTED && motion != Motion.BOTH) {
            double x = width / 2f;
            double y = sy / 2f;
            poseStack.translate(x, y, 1050.0D);
            poseStack.scale(1, 1, -1);
            poseStack.translate(0.0D, 0.0D, 1000);
            poseStack.scale((float) 30, (float) -30, (float) 30);
        }

        for (int i = 0; i < count; i++) {
            float sp = OERenderUtil.getParSecond(5000) + (1f / count) * i;
            if (sp > 1)
                sp -= 1;
            poseStack.pushPose();

            double x = width / 2f;
            double y = sy / 2f;

            if (motion == Motion.TRANSLATED || motion == Motion.BOTH) {
                float par = (float) (sp * Math.toRadians(360));
                x += Math.sin(par) * ((width / 2f) - 30);
                y += Math.sin(2 * par) * ((sy / 2) - 30);
            }

            if (motion == Motion.ROTED || motion == Motion.BOTH) {
                poseStack.translate(x, y, 30);
                OERenderUtil.poseRotateX(poseStack, sp * 360);
                OERenderUtil.poseRotateY(poseStack, sp * 360);
                OERenderUtil.poseRotateZ(poseStack, sp * 360);
                poseStack.translate(-x, -y, -30);
            }

            if (motion == Motion.TRANSLATED || motion == Motion.ROTED || motion == Motion.BOTH) {
                poseStack.translate(x, y, 1050.0D);
                poseStack.scale(1, 1, -1);
                poseStack.translate(0.0D, 0.0D, 1000);
                poseStack.scale((float) 30, (float) -30, (float) 30);
            }

            renderTest(poseStack, multiBufferSource, f);

            poseStack.popPose();
        }

        poseStack.popPose();
    }

    private void renderTest(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, float f) {
        var rt = renderTests.get(currentTest);
        rt.renderTest(poseStack, multiBufferSource, f);
    }

    public static void addRenderTest(IRenderTest renderTest) {
        renderTests.add(renderTest);
    }

    public static void first() {
        // for (Item item : Registry.ITEM) {
        //      addRenderTest(new ItemRenderTest(item));
        //  }
        //10000
        addRenderTest(new BakedModelRenderTest(new ResourceLocation(OtyacraftEngine.MODID, "block/test_model")));//23.5 26.7
        addRenderTest(new BakedModelRenderTest(new ResourceLocation(OtyacraftEngine.MODID, "item/test_item_kame")));//144.5*10 32.6*10
        addRenderTest(new ItemRenderTest(Items.APPLE));//44.2 56.5
        addRenderTest(new ItemRenderTest(Items.IRON_BLOCK));//13.0 17.7
    }

    private static enum Motion {
        FIX, TRANSLATED, ROTED, BOTH;
    }
}
