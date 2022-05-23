package dev.felnull.otyacraftengine.util;

import com.google.common.collect.ImmutableSet;
import dev.felnull.otyacraftengine.impl.OERegistryExpectPlatform;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Predicate;

public class OERegistryUtil {
    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, int maxTickets, int validRange) {
        return OERegistryExpectPlatform.createPoiType(name, matchingStates, maxTickets, validRange);
    }

    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, Predicate<PoiType> predicate, int maxTickets, int validRange) {
        return OERegistryExpectPlatform.createPoiType(name, matchingStates, predicate, maxTickets, validRange);
    }

    public static PoiType registerPoiTypeBlockStates(PoiType poiType) {
        return OERegistryExpectPlatform.registerPoiTypeBlockStates(poiType);
    }

    public static Set<BlockState> getPoiTypeBlockStates(Block block) {
        return OERegistryExpectPlatform.getPoiTypeBlockStates(block);
    }

    public static VillagerProfession createVillagerProfession(ResourceLocation name, PoiType jobPoiType, ImmutableSet<Item> requestedItems, ImmutableSet<Block> secondaryPoi, @Nullable SoundEvent workSound) {
        return OERegistryExpectPlatform.createVillagerProfession(name, jobPoiType, requestedItems, secondaryPoi, workSound);
    }

    public static VillagerTrades.ItemListing createTradeEmeraldForItems(ItemLike item, int cost, int maxUses, int villagerXp) {
        return new VillagerTrades.EmeraldForItems(item, cost, maxUses, villagerXp);
    }

    public static VillagerTrades.ItemListing createTradeItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
        return new VillagerTrades.ItemsForEmeralds(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp);
    }

    public static SimpleParticleType createSimpleParticleType(boolean overrideLimiter) {
        return OERegistryExpectPlatform.createSimpleParticleType(overrideLimiter);
    }
}
