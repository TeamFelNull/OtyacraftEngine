package net.examplemod.blockentity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import dev.felnull.otyacraftengine.util.OERegisterUtils;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TestBlockEntitys {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, BlockEntityCreateSupplier<T> supplier, RegistrySupplier<Block> blocks) {
        return BLOCK_ENTITY_TYPES_REGISTER.register(name, () -> OERegisterUtils.createBlockEntity(supplier, blocks.get()));
    }

    public static void init() {
        BLOCK_ENTITY_TYPES_REGISTER.register();
    }
}
