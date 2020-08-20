package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.client.data.InstructionFromClient;
import red.felnull.otyacraftengine.tileentity.IInstructionTileEntity;

public interface IInstructionContainerScreen {
    default void instruction(String name, CompoundNBT data) {
        if (OtyacraftEngine.proxy.getMinecraft().world.getTileEntity(getInstrunctionPos()) instanceof IInstructionTileEntity) {
            InstructionFromClient.instruction(getInstrunctionPos(), name, data);
        }
    }

    BlockPos getInstrunctionPos();

    default void instructionReturn(String name, CompoundNBT data) {

    }
}
