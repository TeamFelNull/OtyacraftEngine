package dev.felnull.otyacraftenginetest.client.gui.screen;


import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureSpecify;
import dev.felnull.otyacraftengine.client.gui.components.IconButton;
import dev.felnull.otyacraftengine.client.gui.components.RadioButton;
import dev.felnull.otyacraftengine.client.gui.components.SwitchButton;
import dev.felnull.otyacraftenginetest.OtyacraftEngineTest;
import dev.felnull.otyacraftenginetest.client.gui.components.TestFixedListWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestCode3Screen extends Screen {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation(OtyacraftEngineTest.MODID, "textures/gui/test.png");
    private static final TextureSpecify SPECIFY = TextureSpecify.create(TEST_TEXTURE, 0, 0, 50, 50, 100, 100);
    private static final TextureSpecify ICON_SPECIFY = TextureSpecify.create(TEST_TEXTURE, 0, 0, 14, 14, 14, 14);
    private RadioButton radio1;
    private RadioButton radio2;

    public TestCode3Screen() {
        super(Component.literal("Test code3"));
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new IconButton(3, 25, 150, 20, Component.literal("Test icon button"), ICON_SPECIFY, b -> System.out.println("Icon button pressed!")));

        radio1 = addRenderableWidget(new RadioButton(3, 45, Component.literal("Test radio1"), r -> {
        }, () -> ImmutableSet.of(radio1, radio2), true));

        radio2 = addRenderableWidget(new RadioButton(3, 65, Component.literal("Test radio2"), r -> {
        }, () -> ImmutableSet.of(radio1, radio2), true));

        addRenderableWidget(new SwitchButton(3, 85, Component.literal("Test switch"), sw -> {
        }, true));

        List<TestFixedListWidget.TestListEntry> entryList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            entryList.add(new TestFixedListWidget.TestListEntry("Entry-" + i));
        }

        addRenderableWidget(new TestFixedListWidget(3, 108, 175, 100, Component.literal("Test list"), 10, entryList, (r) -> Component.literal(r.text()), null, true, null));

    }

    @Override
    public void render(@NotNull PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);

        SPECIFY.draw(poseStack, 0, 0, 300, 20);

        super.render(poseStack, i, j, f);
    }
}

