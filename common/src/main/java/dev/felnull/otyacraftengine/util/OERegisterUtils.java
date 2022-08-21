package dev.felnull.otyacraftengine.util;

import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import dev.felnull.otyacraftengine.explatform.OERegisterExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * 登録関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public class OERegisterUtils {
    @NotNull
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(@NotNull BlockEntityCreateSupplier<? extends T> supplier, RegistrySupplier<Block>... blocks) {
        return createBlockEntity(supplier, Arrays.stream(blocks).map(Supplier::get).toList().toArray(new Block[0]));
    }

    @NotNull
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(@NotNull BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        return OERegisterExpectPlatform.createBlockEntity(supplier, blocks);
    }

    public static VillagerTrades.ItemListing createTradeEmeraldForItems(ItemLike item, int cost, int maxUses, int villagerXp) {
        return new VillagerTrades.EmeraldForItems(item, cost, maxUses, villagerXp);
    }

    public static VillagerTrades.ItemListing createTradeItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
        return new VillagerTrades.ItemsForEmeralds(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp);
    }
}
