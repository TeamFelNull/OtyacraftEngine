package dev.felnull.otyacraftengine.client.gui.screen;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.menu.MenuRegistry;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.gui.components.RadioButton;
import dev.felnull.otyacraftengine.inventory.TestMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TestBEScreen extends OEBEContainerBaseScreen<TestMenu> {
    private static final ResourceLocation TEST_BG_TEXTURE = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/test_container.png");
    // private ContainerTab testContainerTab;

    public static void fInit() {
        MenuRegistry.registerScreenFactory(TestMenu.TEST_MENU.get(), TestBEScreen::new);
    }

    public TestBEScreen(TestMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        this.imageWidth = 214;
        this.imageHeight = 175;
        this.inventoryLabelY = this.imageHeight - 94;

    }

    @Override
    protected void init() {
        super.init();
        //  this.addRenderableWidget(new ContainerTab(leftPos + imageWidth, topPos + 30, 30, 30, new TextComponent("TEST")));

        this.addRenderableWidget(new RadioButton(leftPos, topPos, new TextComponent("TEST V2"), null, ImmutableSet::of, true));
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        super.render(poseStack, i, j, f);
    }

    @Override
    public void mouseMoved(double d, double e) {
        super.mouseMoved(d, e);
        // this.testContainerTab.x = (int) d;
        //  this.testContainerTab.y = (int) e;
    }

    @Override
    protected ResourceLocation getBackGrandTexture() {
        return TEST_BG_TEXTURE;
    }
}
