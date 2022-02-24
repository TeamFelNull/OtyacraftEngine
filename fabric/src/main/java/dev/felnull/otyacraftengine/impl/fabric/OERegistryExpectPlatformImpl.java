package dev.felnull.otyacraftengine.impl.fabric;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.mixin.object.builder.PointOfInterestTypeAccessor;
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

public class OERegistryExpectPlatformImpl {
    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, int maxTickets, int validRange) {
        return PointOfInterestTypeAccessor.callCreate(name.toString(), matchingStates, maxTickets, validRange);
    }

    public static PoiType createPoiType(ResourceLocation name, Set<BlockState> matchingStates, Predicate<PoiType> predicate, int maxTickets, int validRange) {
        return PointOfInterestTypeAccessor.callCreate(name.toString(), matchingStates, maxTickets, predicate, validRange);
    }

    public static PoiType registerPoiTypeBlockStates(PoiType poiType) {
        return PointOfInterestTypeAccessor.callSetup(poiType);
    }

    public static Set<BlockState> getPoiTypeBlockStates(Block block) {
        return PoiType.getBlockStates(block);
    }

    public static VillagerProfession createVillagerProfession(ResourceLocation name, PoiType jobPoiType, ImmutableSet<Item> requestedItems, ImmutableSet<Block> secondaryPoi, @Nullable SoundEvent workSound) {
        return VillagerProfessionBuilder.create().id(name).workstation(jobPoiType).harvestableItems(requestedItems).secondaryJobSites(secondaryPoi).workSound(workSound).build();
    }

    public static VillagerTrades.ItemListing createTradeEmeraldForItems(ItemLike item, int cost, int maxUses, int villagerXp) {
        return new VillagerTrades.EmeraldForItems(item, cost, maxUses, villagerXp);
    }

    public static VillagerTrades.ItemListing createTradeItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
        return new VillagerTrades.ItemsForEmeralds(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp);
    }
}
