package dev.felnull.otyacraftenginetest.client.gui.screen;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureRegion;
import dev.felnull.otyacraftengine.client.gui.components.IconButton;
import dev.felnull.otyacraftengine.client.gui.components.RadioButton;
import dev.felnull.otyacraftengine.client.gui.components.SwitchButton;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.client.gui.components.TestFixedListWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ComponentsTestScreen extends Screen {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation(OtyacraftEngineTest.MODID, "textures/gui/test.png");
    private static final TextureRegion ICON = new TextureRegion(TEST_TEXTURE, 14, 14, 0, 0, 14, 14);
    private RadioButton radio1;
    private RadioButton radio2;

    public ComponentsTestScreen() {
        super(Component.literal("Component Test"));
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(new SwitchButton(3, 3, Component.literal("Test Switch Button 1"), false));
        this.addRenderableWidget(new SwitchButton(3, 16, Component.literal("Test Switch Button 2"), true));

        radio1 = addRenderableWidget(new RadioButton(3, 45, Component.literal("Test radio1"), r -> {
        }, () -> ImmutableSet.of(radio1, radio2), true));

        radio2 = addRenderableWidget(new RadioButton(3, 65, Component.literal("Test radio2"), r -> {
        }, () -> ImmutableSet.of(radio1, radio2), true));

        addRenderableWidget(new IconButton(this.width - 27, this.height - 27, 20, 20, Component.literal("Test icon button"), it -> System.out.println("Icon button pressed!"), ICON));

        List<TestFixedListWidget.TestListEntry> entryList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            entryList.add(new TestFixedListWidget.TestListEntry("Entry-" + i));
        }

        addRenderableWidget(new TestFixedListWidget(3, 108, 175, 100, Component.literal("Test list"), 10, entryList, (r) -> Component.literal(r.text()), null, true, null));

    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);
    }
}
