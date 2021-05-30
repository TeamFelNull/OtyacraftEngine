package red.felnull.otyacraftengine.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;

import java.util.ArrayList;
import java.util.List;

public class IKSGTagUtil {
    public static List<ResourceLocation> getItemTags(Item item) {
        return new ArrayList<>(ItemTags.getAllTags().getMatchingTags(item));
    }

    public static List<ResourceLocation> getBlockTags(Block block) {
        return new ArrayList<>(BlockTags.getAllTags().getMatchingTags(block));
    }

    public static List<ResourceLocation> getEntityTypeTags(EntityType<?> entity) {
        return new ArrayList<>(EntityTypeTags.getAllTags().getMatchingTags(entity));
    }

    public static List<ResourceLocation> getFluidTags(Fluid fluid) {
        return new ArrayList<>(OEExpectPlatform.getFluidAllTags().getMatchingTags(fluid));
    }

    public static List<ResourceLocation> getGameEventTags(GameEvent gameevent) {
        return new ArrayList<>(OEExpectPlatform.getGameEventAllTags().getMatchingTags(gameevent));
    }

    public static Tag.Named<Item> bindItemTag(ResourceLocation location) {
        return OEExpectPlatform.bindItemTag(location.toString());
    }

    public static Tag.Named<Block> bindBlockTag(ResourceLocation location) {
        return OEExpectPlatform.bindBlockTag(location.toString());
    }

    public static Tag.Named<EntityType<?>> bindEntityTypeTag(ResourceLocation location) {
        return OEExpectPlatform.bindEntityTypeTag(location.toString());
    }

    public static Tag.Named<Fluid> bindFluidTag(ResourceLocation location) {
        return OEExpectPlatform.bindFluidTag(location.toString());
    }

    public static Tag.Named<GameEvent> bindGameEventTag(ResourceLocation location) {
        return OEExpectPlatform.bindGameEventTag(location.toString());
    }
}
