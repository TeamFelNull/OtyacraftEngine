package dev.felnull.otyacraftengine.networking;

import dev.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record BlockEntityExistence(ResourceLocation dimension, BlockPos blockPos, ResourceLocation blockEntityName) {

    public static BlockEntityExistence readFBB(FriendlyByteBuf buf) {
        return new BlockEntityExistence(buf.readResourceLocation(), buf.readBlockPos(), buf.readResourceLocation());
    }

    public static BlockEntityExistence getByBlockEntity(BlockEntity blockEntity) {
        var bereg = Registry.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType());
        return new BlockEntityExistence(blockEntity.getLevel().dimension().location(), blockEntity.getBlockPos(), bereg);
    }

    public boolean check(Level level) {
        if (level == null)
            return false;
        if (!level.dimension().location().equals(dimension))
            return false;
        var be = level.getBlockEntity(blockPos);
        return be instanceof IInstructionBlockEntity && blockEntityName.equals(Registry.BLOCK_ENTITY_TYPE.getKey(be.getType()));
    }

    public FriendlyByteBuf writeFBB(FriendlyByteBuf buf) {
        buf.writeResourceLocation(dimension);
        buf.writeBlockPos(blockPos);
        buf.writeResourceLocation(blockEntityName);
        return buf;
    }
}
