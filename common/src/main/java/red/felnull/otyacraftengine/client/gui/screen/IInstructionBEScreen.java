package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import red.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import red.felnull.otyacraftengine.packet.BlockEntityInstructionMessage;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public interface IInstructionBEScreen {
    default void instruction(String name, CompoundTag data) {
        if (getInstructionBlockEntity() instanceof IInstructionBlockEntity) {
            Minecraft mc = Minecraft.getInstance();
            ResourceLocation dimension = mc.level.dimension().location();
            ResourceLocation bereg = Registry.BLOCK_ENTITY_TYPE.getKey(getInstructionBlockEntity().getType());
            IKSGPacketUtil.sendToServerPacket(new BlockEntityInstructionMessage(dimension, getInstructionBlockEntity().getBlockPos(), bereg, name, data));
        }
    }

    default void instructionReturn(String name, CompoundTag data) {
    }

    BlockEntity getInstructionBlockEntity();
}
