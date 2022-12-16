package dev.felnull.otyacraftenginetest.blockentity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import dev.felnull.otyacraftengine.util.OERegisterUtils;
import dev.felnull.otyacraftenginetest.block.TestBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TestBlockEntitys {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registries.BLOCK_ENTITY_TYPE);
    public static final RegistrySupplier<BlockEntityType<TestContainerBlockEntity>> TEST_CONTAINER_BLOCKENTITY = register("test_container_blockentity", TestContainerBlockEntity::new, TestBlocks.TEST_CONTAINER_BLOCK);
    public static final RegistrySupplier<BlockEntityType<TestRotedBlockEntity>> TEST_ROTED_BLOCKENTITY = register("test_roted_blockentity", TestRotedBlockEntity::new, TestBlocks.TEST_ROTED_BLOCK);

    private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, BlockEntityCreateSupplier<T> supplier, RegistrySupplier<Block>... blocks) {
        return BLOCK_ENTITY_TYPES_REGISTER.register(name, () -> OERegisterUtils.createBlockEntity(supplier, blocks));
    }

    public static void init() {
        BLOCK_ENTITY_TYPES_REGISTER.register();
    }
}
