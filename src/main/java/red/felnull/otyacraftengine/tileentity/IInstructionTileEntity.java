package red.felnull.otyacraftengine.tileentity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface IInstructionTileEntity {
    //クライアントからの指示
    default public CompoundNBT instructionFromClient(ServerPlayerEntity player, String name, CompoundNBT data) {

        return null;
    }

    //クライアントからの指示を受けるかどうか
    default public boolean canInteractWith(ServerPlayerEntity player, String name, CompoundNBT data) {
        return true;
    }
}
