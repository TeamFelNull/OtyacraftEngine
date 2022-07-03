package net.examplemod.blockentity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import dev.felnull.otyacraftengine.util.OERegisterUtils;
import net.examplemod.block.TestBlocks;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Arrays;
import java.util.function.Supplier;

public class TestBlockEntitys {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static final RegistrySupplier<BlockEntityType<TestContainerBlockEntity>> TEST_CONTAINER_BLOCKENTITY = register("test_container_blockentity", TestContainerBlockEntity::new, TestBlocks.TEST_CONTAINER_BLOCK);

    private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, BlockEntityCreateSupplier<T> supplier, RegistrySupplier<Block>... blocks) {
        return BLOCK_ENTITY_TYPES_REGISTER.register(name, () -> OERegisterUtils.createBlockEntity(supplier, Arrays.stream(blocks).map(Supplier::get).toList().toArray(new Block[0])));
    }

    public static void init() {
        BLOCK_ENTITY_TYPES_REGISTER.register();
    }
}
