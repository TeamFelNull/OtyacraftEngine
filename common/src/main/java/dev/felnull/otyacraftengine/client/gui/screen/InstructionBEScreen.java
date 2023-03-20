package dev.felnull.otyacraftengine.client.gui.screen;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import dev.felnull.otyacraftengine.networking.OEPackets;
import dev.felnull.otyacraftengine.networking.existence.BlockEntityExistence;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface InstructionBEScreen extends InstructionScreen {
    static void instructionBlockEntity(InstructionBEScreen screen, BlockEntity blockEntity, String name, CompoundTag data) {
        if (Minecraft.getInstance().screen == screen && blockEntity instanceof IInstructionBlockEntity) {
            NetworkManager.sendToServer(OEPackets.BLOCK_ENTITY_INSTRUCTION, new OEPackets.BlockEntityInstructionMessage(screen.getInstructionID(), BlockEntityExistence.getByBlockEntity(blockEntity), name, data).toFBB());
        }
    }
}
