package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.client.debug.OtyacraftEngineClientDebug;
import dev.felnull.otyacraftengine.util.OEEntityUtil;
import dev.felnull.otyacraftengine.util.OEItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;

import java.util.*;


public class ClientDebugHandler {
    public static void init() {
        ClientTooltipEvent.ITEM.register(ClientDebugHandler::onTooltip);
    }

    private static void onTooltip(ItemStack stack, List<Component> lines, TooltipFlag flag) {
        if (stack.isEmpty()) return;
        var oed = OtyacraftEngineClientDebug.getInstance();
        if (oed.isShowTagInTooltip()) {
            var itemTags = new ArrayList<>(stack.getTags().map(TagKey::location).toList());

            if (stack.getItem() instanceof BlockItem blockItem) {
                var blockTags = new ArrayList<>(blockItem.getBlock().defaultBlockState().getTags().map(TagKey::location).toList());
                Set<ResourceLocation> bothTags = new TreeSet<>();
                for (ResourceLocation tag : itemTags) {
                    if (blockTags.contains(tag))
                        bothTags.add(tag);
                }
                itemTags.removeAll(bothTags);
                blockTags.removeAll(bothTags);
                addTagList(lines, "Item tags", itemTags);
                addTagList(lines, "Block tags", blockTags);
                addTagList(lines, "Both tags", bothTags);
            } else {
                addTagList(lines, "Item tags", itemTags);
            }

            var entityTypes = getEntityTypesByItem(stack);
            var entityTypeTags = entityTypes.stream().flatMap(OEEntityUtil::getTags).map(TagKey::location).toList();
            addTagList(lines, "Entity tags", entityTypeTags);
        }

        if (oed.isShowModNameInTooltip()) {
            var modid = OEItemUtil.getCreatorModId(stack);
            lines.add(Component.literal(Platform.getMod(modid).getName()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    private static void addTagList(List<Component> lines, String text, Collection<ResourceLocation> tags) {
        if (tags.isEmpty()) return;
        lines.add(Component.literal(text).withStyle(ChatFormatting.GRAY));
        for (ResourceLocation tag : tags) {
            lines.add(Component.literal("- " + tag));
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
