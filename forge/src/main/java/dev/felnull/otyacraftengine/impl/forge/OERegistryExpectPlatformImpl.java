package dev.felnull.otyacraftengine.impl.forge;

import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Predicate;

public class OERegistryExpectPlatformImpl {
    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, int maxTickets, int validRange) {
        return new PoiType(name.toString(), matchingStates, maxTickets, validRange);
    }

    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, Predicate<PoiType> predicate, int maxTickets, int validRange) {
        return new PoiType(name.toString(), matchingStates, maxTickets, predicate, validRange);
    }

    public static PoiType registerPoiTypeBlockStates(PoiType poiType) {
        return PoiType.registerBlockStates(poiType);
    }

    public static Set<BlockState> getPoiTypeBlockStates(Block block) {
        return PoiType.getBlockStates(block);
    }

    public static VillagerProfession createVillagerProfession(ResourceLocation name, PoiType jobPoiType, ImmutableSet<Item> requestedItems, ImmutableSet<Block> secondaryPoi, @Nullable SoundEvent workSound) {
        return new VillagerProfession(name.toString(), jobPoiType, requestedItems, secondaryPoi, workSound);
    }
}
