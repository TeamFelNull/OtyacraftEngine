package dev.felnull.otyacraftengine.impl;

import com.google.common.collect.ImmutableSet;
import dev.architectury.injectables.annotations.ExpectPlatform;
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

public class OERegistryExpectPlatform {
    @ExpectPlatform
    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, int maxTickets, int validRange) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, Predicate<PoiType> predicate, int maxTickets, int validRange) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static PoiType registerPoiTypeBlockStates(PoiType poiType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Set<BlockState> getPoiTypeBlockStates(Block block) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static VillagerProfession createVillagerProfession(ResourceLocation name, PoiType jobPoiType, ImmutableSet<Item> requestedItems, ImmutableSet<Block> secondaryPoi, @Nullable SoundEvent workSound) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static VillagerTrades.ItemListing createTradeEmeraldForItems(ItemLike item, int cost, int maxUses, int villagerXp) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static VillagerTrades.ItemListing createTradeItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
        throw new AssertionError();
    }
}
