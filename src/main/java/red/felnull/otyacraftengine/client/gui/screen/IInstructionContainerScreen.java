package red.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public interface IInstructionContainerScreen {
    void instruction(String name, CompoundNBT data);

    BlockPos getInstrunctionPos();

    void instructionReturn(String name, CompoundNBT data);
}
