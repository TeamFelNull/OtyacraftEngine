package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.loader.PlayerInfoManager;
import dev.felnull.otyacraftengine.client.loader.URLTextureManager;
import dev.felnull.otyacraftengine.client.util.ClientUtilInit;
import dev.felnull.otyacraftengine.util.OEItemUtil;
import dev.felnull.otyacraftengine.util.OETagUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
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
        var item = itemStack.getItem();

        if (OtyacraftEngine.CONFIG.showTagTooltip) {
            var itemTags = OETagUtil.getTags(item);
            if (item instanceof BlockItem blockItem) {
                var blockTags = OETagUtil.getTags(blockItem.getBlock());
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
            entityTypes.forEach(n -> entityTypeTags.addAll(OETagUtil.getTags(n)));
            if (!entityTypeTags.isEmpty()) {
                list.add(new TextComponent("Entity tags").withStyle(ChatFormatting.AQUA));
                for (ResourceLocation tag : entityTypeTags) {
                    list.add(new TextComponent(" - " + tag));
                }
            }
        }

        if (OtyacraftEngine.CONFIG.showModNameTooltip) {
            var modid = Registry.ITEM.getKey(item);
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
