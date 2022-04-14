package dev.felnull.otyacraftengine.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.event.FabricOBJLoaderEvent;
import dev.felnull.otyacraftengine.client.loader.PlayerInfoManager;
import dev.felnull.otyacraftengine.client.loader.URLTextureManager;
import dev.felnull.otyacraftengine.client.renderer.GuiDebugRenderer;
import dev.felnull.otyacraftengine.client.util.ClientUtilInit;
import dev.felnull.otyacraftengine.util.OEItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientHandler {

    public static void init() {
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(ClientHandler::onQuit);
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(ClientHandler::onJoin);
        ClientTooltipEvent.ITEM.register(ClientHandler::onTooltip);
        ClientGuiEvent.RENDER_POST.register(ClientHandler::onRenderGui);
        FabricOBJLoaderEvent.LOAD.register(ClientHandler::onFabricOBJLoad);
    }

    private static EventResult onFabricOBJLoad(ResourceLocation location) {
        if (location.getNamespace().equals(OtyacraftEngine.MODID))
            return EventResult.interruptTrue();
        return EventResult.pass();
    }

    private static void onRenderGui(Screen screen, PoseStack posestack, int mouseX, int mouseY, float delta) {
        if (!OtyacraftEngine.CONFIG.showWidgetData) return;
        GuiDebugRenderer.onScreenRender(screen, posestack, mouseX, mouseY, delta);
    }

    private static void onJoin(LocalPlayer localPlayer) {
        onReset();
    }

    private static void onQuit(LocalPlayer localPlayer) {
        onReset();
    }

    private static void onReset() {
        ClientUtilInit.clear();
        PlayerInfoManager.getInstance().reload();
        URLTextureManager.getInstance().reload();
    }

    private static void onTooltip(ItemStack itemStack, List<Component> list, TooltipFlag tooltipFlag) {
        if (itemStack.isEmpty()) return;

        if (OtyacraftEngine.CONFIG.showTagTooltip) {
            var itemTags = itemStack.getTags().map(TagKey::location).toList();
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                var blockTags = blockItem.getBlock().builtInRegistryHolder().tags().map(TagKey::location).toList();
                Set<ResourceLocation> bothTags = new HashSet<>();
                boolean firstItem = false;
                boolean firstBlock = false;

                for (ResourceLocation tag : itemTags) {
                    if (!blockTags.contains(tag)) {
                        if (!firstItem) {
                            list.add(new TextComponent("Item tags").withStyle(ChatFormatting.AQUA));
                            firstItem = true;
                        }
                        list.add(new TextComponent(" - " + tag));
                    } else {
                        bothTags.add(tag);
                    }
                }

                for (ResourceLocation blockTag : blockTags) {
                    if (!itemTags.contains(blockTag)) {
                        if (!firstBlock) {
                            list.add(new TextComponent("Block tags").withStyle(ChatFormatting.AQUA));
                            firstBlock = true;
                        }
                        list.add(new TextComponent(" - " + blockTag));
                    } else {
                        bothTags.add(blockTag);
                    }
                }

                if (!bothTags.isEmpty()) {
                    list.add(new TextComponent("Both tags").withStyle(ChatFormatting.AQUA));
                    for (ResourceLocation tag : bothTags) {
                        list.add(new TextComponent(" - " + tag));
                    }
                }
            } else {
                if (!itemTags.isEmpty()) {
                    list.add(new TextComponent("Item tags").withStyle(ChatFormatting.AQUA));
                    for (ResourceLocation tag : itemTags) {
                        list.add(new TextComponent(" - " + tag));
                    }
                }
            }
            var entityTypes = getEntityTypesByItem(itemStack);
            Set<ResourceLocation> entityTypeTags = new HashSet<>();
            entityTypes.forEach(n -> entityTypeTags.addAll(n.builtInRegistryHolder().tags().map(TagKey::location).toList()));
            if (!entityTypeTags.isEmpty()) {
                list.add(new TextComponent("Entity tags").withStyle(ChatFormatting.AQUA));
                for (ResourceLocation tag : entityTypeTags) {
                    list.add(new TextComponent(" - " + tag));
                }
            }
        }

        if (OtyacraftEngine.CONFIG.showModNameTooltip) {
            var modid = Registry.ITEM.getKey(itemStack.getItem());
            list.add(new TextComponent(Platform.getMod(modid.getNamespace()).getName()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    private static Set<EntityType<?>> getEntityTypesByItem(ItemStack stack) {
        var item = stack.getItem();
        if (item instanceof SpawnEggItem spawnEggItem) {
            return Set.of(spawnEggItem.getType(stack.getTag()));
        } else if (item instanceof MobBucketItem mobBucketItem) {
            return Set.of(OEItemUtil.getMobBucketEntity(mobBucketItem));
        }
        return Set.of();
    }

}
