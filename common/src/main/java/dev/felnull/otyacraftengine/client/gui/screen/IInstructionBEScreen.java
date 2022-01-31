package dev.felnull.otyacraftengine.client.gui.screen;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import dev.felnull.otyacraftengine.networking.BlockEntityExistence;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

public interface IInstructionBEScreen extends IInstructionScreen {
    public static void instructionBlockEntity(IInstructionBEScreen screen, BlockEntity blockEntity, String name, int num, CompoundTag data) {
        if (Minecraft.getInstance().screen == screen && blockEntity instanceof IInstructionBlockEntity) {
            var mc = Minecraft.getInstance();
            NetworkManager.sendToServer(OEPackets.BLOCK_ENTITY_INSTRUCTION, new OEPackets.BlockEntityInstructionMessage(screen.getInstructionID(), BlockEntityExistence.getByBlockEntity(blockEntity), name, num, data).toFBB());
        }
    }
}
